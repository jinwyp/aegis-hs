package com.yimei.hs.boot;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.thymeleaf.expression.Lists;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by hary on 2017/9/21.
 */

@Service
public class ACLInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return super.preHandle(request, response, handler);
    }

    //    @Autowired
//    private UserSession userSession;
//    @Autowired
//    private AdminSession adminSession;
//    @Autowired
//    private CompanyService companyService;


//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//
//        if (handler instanceof HandlerMethod) {
//            HandlerMethod method = (HandlerMethod) handler;
//            List<UserRole> requiresRoles = Arrays.asList(method.getMethodAnnotation(RequireJWT.class),
//                    method.getBeanType().getDeclaredAnnotation(RequiresRoles.class))
//                    .stream().filter(a -> a != null)
//                    .flatMap(a -> Lists.newArrayList(a.value()).stream())
//                    .collect(Collectors.toList());
//            if (requiresRoles.size() > 0) {
//
//                //公司前台用户公司信息
//                if (userSession.getUser() != null && !WebUtils.isFromAdminRequest(request) && method.getBeanType().getDeclaredAnnotation(CheckCompany.class)!=null) {
//                    if (StringUtils.isEmpty(userSession.getUser().getCompanyId())) {
//                        throw new CompanyStatusException(Company.Status.waitImprove);
//                    } else {
//                        Company company = companyService.loadLastCompanyByUUID(userSession.getUser().getCompanyId());
//                        if (company == null) {
//                            throw new CompanyStatusException(Company.Status.waitImprove);
//                        } else if (company.getStatus() == Company.Status.unApproved.value) {
//                            throw new CompanyStatusException(Company.Status.unApproved);
//                        } else if (company.getStatus() == Company.Status.checkPending.value) {
//                            throw new CompanyStatusException(Company.Status.checkPending);
//                        }
//                    }
//                }
//
////                //验证角色权限
////                if (userSession.getUser() != null && !WebUtils.isFromAdminRequest(request) && !requiresRoles.contains(userSession.getUser().getRole())) {
////                    throw new ForbiddenException();
////                }
////                if (adminSession.getAdmin() != null && WebUtils.isFromAdminRequest(request) && !requiresRoles.contains(adminSession.getAdmin().getRole())) {
////                    throw new ForbiddenException();
////                }
//
//            }
//
//        }
//        return super.preHandle(request, response, handler);
//    }

}
