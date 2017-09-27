package com.yimei.hs.boot.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yimei.hs.boot.api.Result;
import com.yimei.hs.util.WebUtils;
import io.jsonwebtoken.MalformedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by hary on 2017/9/21.
 */
@ControllerAdvice
public class GlobalHandlerExceptionResolver {

    private static final Logger logger = LoggerFactory.getLogger(GlobalHandlerExceptionResolver.class);


    @Autowired
    private ObjectMapper om;

    private static final String userUnAuth_flag = "userUnauthorized";

    private static final String adminUnAuth_flag = "adminUnauthorized";

    @ExceptionHandler({BindException.class, TypeMismatchException.class, MissingServletRequestParameterException.class, MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void process400Error(HttpServletRequest request, HttpServletResponse response, Exception ex) throws IOException {
        logger.error("400Exception:", ex);
        if (WebUtils.isAjaxRequest(request)) {
            response.setStatus(400);
            response.sendRedirect("/400");
        }
        response.setStatus(400);
        om.writeValue(response.getOutputStream(), new Result(4001, "客户端错误"));
    }

    @ExceptionHandler({NoJwtTokenException.class, UnAuthorizedException.class, MalformedJwtException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public void process401Error(HttpServletRequest request, HttpServletResponse response, Exception ex) throws IOException {
        response.setStatus(401);
        if (!WebUtils.isAjaxRequest(request)) {
            if (ex instanceof NoJwtTokenException) {
                response.sendRedirect("/login");
            }
            return;
        }
        response.setStatus(401);
        response.setContentType("application/json; charset=UTF-8");
        om.writeValue(response.getOutputStream(), new Result(4001, "无权访问"));
    }


    //业务异常
    @ExceptionHandler({BusinessException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public void process409Error(HttpServletRequest request, HttpServletResponse response, Exception ex) throws IOException, ServletException {
        response.setStatus(409);
        response.setContentType("application/json; charset=UTF-8");

        String errMsg = ex.getMessage();
        if (errMsg == null) {
            errMsg = "系统错误";
        }
        response.setStatus(400);
        om.writeValue(response.getOutputStream(), new Result(5001, errMsg));
    }


    @Async
    private void handler500(HttpServletRequest request, Exception ex) {
        logger.warn("开始发送邮件");
        try {
            if ("application/json".equals(request.getContentType())) {
                // reporter.handle(ex, request.getRequestURL().toString(), om.writeValueAsString(extractPostRequestBody(request)), loadRequestHeader(request));
            } else {
                // reporter.handle(ex, request.getRequestURL().toString(), om.writeValueAsString(request.getParameterMap()), loadRequestHeader(request));
            }
        } catch (Exception e) {
            logger.warn("邮件发送失败", e);
        }
        logger.warn("邮件发送结束");
    }


    public String extractPostRequestBody(HttpServletRequest request) {
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            Scanner s = null;
            try {
                s = new Scanner(request.getInputStream(), "UTF-8").useDelimiter("\\A");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return s.hasNext() ? s.next() : "";
        } else {
            return "{}";
        }
    }

    public String loadRequestHeader(HttpServletRequest request) throws JsonProcessingException {
        Map<String, String> map = new HashMap<String, String>();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        map.put("Request Method", request.getMethod());
        map.put("requestURL", request.getRequestURL().toString());
        return om.writeValueAsString(map);
    }


}
