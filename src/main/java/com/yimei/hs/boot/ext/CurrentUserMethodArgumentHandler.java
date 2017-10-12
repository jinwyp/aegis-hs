package com.yimei.hs.boot.ext;

import com.yimei.hs.boot.ext.annotation.CurrentUser;
import com.yimei.hs.boot.exception.NoJwtTokenException;
import com.yimei.hs.user.entity.User;
import com.yimei.hs.util.JsonMapper;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    JwtSupport support;

    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.hasParameterAnnotation(CurrentUser.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        User user =  new User();
        user.setId(1L);
        user.setIsActive(true);
        user.setIsAdmin(true);
        user.setPhone("13022117051");
        user.setDeptId(1L);
        return user;

//        HttpServletRequest httpServletRequest = (HttpServletRequest) webRequest.getNativeRequest();
//        return support.doJwt(httpServletRequest);

    }
}
