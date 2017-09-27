package com.yimei.hs.boot.ext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yimei.hs.boot.api.Result;
import com.yimei.hs.boot.exception.UnAuthorizedException;
import com.yimei.hs.boot.ext.annotation.Logined;
import com.yimei.hs.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Autowired
    ObjectMapper om;

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
            } else {
              // todo    /api/ying/{orderId}/.*
                // todo  /api/cang/{orderId}/.*
              // 在数据表hs_ying_order/hs_cang_order里找  ownerId = user.getId() && id = {orderId}
                // 如果找到， return true;
                // 如果找不到 throw new UnAuthorizedException("你不是这条业务线的主人");
                response.setStatus(400);
                om.writeValue(response.getOutputStream(), Result.error(4001, "你不是这条业务线的主人", HttpStatus.UNAUTHORIZED));
                return false;
            }
        }
        return true;
    }
}
