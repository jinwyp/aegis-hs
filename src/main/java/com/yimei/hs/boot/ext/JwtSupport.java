package com.yimei.hs.boot.ext;

import com.yimei.hs.boot.exception.NoJwtTokenException;
import com.yimei.hs.user.entity.User;
import com.yimei.hs.util.JsonMapper;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hary on 2017/9/26.
 */

@Service
public class JwtSupport {

    Pattern p = Pattern.compile("Bearer (\\S+)");

    @Value("${jwt.secureKey}")
    private String secretKey;

    public User doJwt(HttpServletRequest request) {
        Pattern p = Pattern.compile("Bearer (\\S+)");

        String jwtToken = request.getHeader("Authorization");
        try {
            if (jwtToken == null || jwtToken.isEmpty()) {
                throw new NoJwtTokenException("Header Authorization 缺少jwtToken");
            }

            Matcher m = p.matcher(jwtToken);
            if (m.matches()) {
                String token = m.group(1);
                String userJson = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
                return JsonMapper.nonDefaultMapper().fromJson(userJson, User.class);
            } else {
                throw new NoJwtTokenException("缺少jwtToken异常");
            }
        }
        catch (SignatureException e) {
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
