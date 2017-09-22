package com.yimei.hs.boot.web;

import com.yimei.hs.boot.annotation.CurrentUser;
import com.yimei.hs.boot.exception.NoJwtTokenException;
import com.yimei.hs.entity.User;
import com.yimei.hs.util.JsonMapper;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xiangyang on 15-6-4.
 */
@Service
public class CurrentUserMethodArgumentHandler implements HandlerMethodArgumentResolver {

    private static final Logger logger = LoggerFactory.getLogger(CurrentUserMethodArgumentHandler.class);
    Pattern p = Pattern.compile("Bearer (\\S+)");
    @Value("${jwt.secureKey}")
    private String secretKey;

    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.hasParameterAnnotation(CurrentUser.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest httpServletRequest = (HttpServletRequest) webRequest.getNativeRequest();
        String jwtToken = httpServletRequest.getHeader("Authorization");
        try {
            Matcher m = p.matcher(jwtToken);
            if (m.matches()) {
                String token = m.group(1);
                String userJson = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
                return JsonMapper.nonDefaultMapper().fromJson(userJson, User.class);
            } else {
                throw new NoJwtTokenException("缺少jwtToken异常");
            }
        } catch (SignatureException e) {
            throw new SignatureException("jwt token 签名错误", e);
        } catch (MalformedJwtException e) {
            throw new MalformedJwtException("jwt token 签名错误", e);
        } catch (ExpiredJwtException e) {
            throw new MalformedJwtException("jwt token 签名过期", e);
        } catch (UnsupportedJwtException e) {
            throw new UnsupportedJwtException("jwt token 签名错误", e);
        } catch (IllegalArgumentException e) {
            throw new UnsupportedJwtException("jwt token 签名错误", e);
        }

    }
}
