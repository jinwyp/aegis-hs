package com.yimei.hs.boot.ext;

import com.yimei.hs.boot.exception.UnAuthorizedException;
import com.yimei.hs.boot.ext.annotation.Logined;
import com.yimei.hs.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by hary on 2017/9/21.
 */

@Service
public class ACLInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    JwtSupport jwtSupport;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod)handler;
            Logined logined = method.getMethodAnnotation(Logined.class);
            if (logined == null ) {
                logined = method.getBeanType().getDeclaredAnnotation(Logined.class);
            }
            if (logined == null) {
                return true;
            }
            boolean isAdmin = logined.isAdmin();

            User user = jwtSupport.doJwt(request);

            if (isAdmin) {
                if ( user.getIsAdmin() == null || user.getIsAdmin() == false) {
                    return false;
                }
            }
        }
        return true;
    }
}
