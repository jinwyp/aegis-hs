package com.yimei.hs.boot.ext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yimei.hs.boot.api.Result;
import com.yimei.hs.boot.ext.annotation.Logined;
import com.yimei.hs.user.entity.User;
import com.yimei.hs.ying.service.YingOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hary on 2017/9/21.
 */

@Service
public class ACLInterceptor extends HandlerInterceptorAdapter {

    public static final Pattern p = Pattern.compile("^/api/(ying|cang)/(\\d+).*$");

    @Autowired
    JwtSupport jwtSupport;

    @Autowired
    ObjectMapper om;

    @Autowired
    private YingOrderService yingOrderService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;
            Logined logined = method.getMethodAnnotation(Logined.class);
            if (logined == null) {
                logined = method.getBeanType().getDeclaredAnnotation(Logined.class);
            }
            if (logined == null) {
                return true;
            }
            boolean isAdmin = logined.isAdmin();

            User user = jwtSupport.doJwt(request);

            if (isAdmin) {
                if (user.getIsAdmin() == null || user.getIsAdmin() == false) {
                    return false;
                }
            } else {
                // validate /api/ying/{orderId}/.*
                // todo  /api/cang/{orderId}/.*
                // validate /api/ying/{orderId}/.*
                Matcher m = p.matcher(request.getRequestURI());
                if (m.matches()) {
                    String business = m.group(1);
                    long orderId = Long.parseLong(m.group(2));

                    if ( business.equals("ying")) {
                        if (!yingOrderService.orderIsExists(user.getId(), orderId)) {
                            response.setStatus(401);
                            response.setContentType("application/json;charset=UTF-8");
                            om.writeValue(response.getOutputStream(), new Result(4001, "你不是这条业务线的主人"));
                            return false;
                        }

                    } else if (business.equals("cang")) {
                        if (!yingOrderService.orderIsExists(user.getId(), orderId)) {
                            response.setStatus(401);
                            response.setContentType("application/json;charset=UTF-8");
                            om.writeValue(response.getOutputStream(), new Result(4001, "你不是这条业务线的主人"));
                            return false;
                        }
                    } else {
                        response.setStatus(404);
                        response.setContentType("application/json;charset=UTF-8");
                        om.writeValue(response.getOutputStream(), new Result(4001, "页面不存在"));
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
