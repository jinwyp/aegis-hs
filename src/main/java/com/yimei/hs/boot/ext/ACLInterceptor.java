package com.yimei.hs.boot.ext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yimei.hs.boot.api.Result;
import com.yimei.hs.boot.ext.annotation.Logined;
import com.yimei.hs.enums.BusinessType;
import com.yimei.hs.same.service.OrderService;
import com.yimei.hs.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
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

    public static final Pattern p = Pattern.compile("^/api/business/(\\w+)/(\\d+).*$");

    @Autowired
    JwtSupport jwtSupport;

    @Autowired
    ObjectMapper om;

    @Autowired
    private OrderService orderService;

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
                    response.setStatus(401);
                    response.setContentType("application/json;charset=UTF-8");
                    om.writeValue(response.getOutputStream(), new Result(4001, "管理员才能访问"));
                    return false;
                }
            } else {
                Matcher m = p.matcher(request.getRequestURI());
                if (m.matches()) {
                    String business = m.group(1);

                    if ( business.equals("yings")) {
                        business = "ying";
                    }
                    if ( business.equals("cangs")) {
                        business = "cang";
                    }

                    BusinessType type = BusinessType.valueOf(business);

                    long orderId = Long.parseLong(m.group(2));
                    if (!orderService.hasOrder(user.getId(), type, orderId)) {
                        response.setStatus(401);
                        response.setContentType("application/json;charset=UTF-8");
                        om.writeValue(response.getOutputStream(), new Result(4001, "你不是这条业务线的主人"));
                        return false;
                    }

                } else {
                   return true;
                }
            }
        }
        return true;
    }
}
