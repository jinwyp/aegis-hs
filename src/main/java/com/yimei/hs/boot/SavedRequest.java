package com.yimei.hs.boot;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by hary on 2017/9/21.
 */

public class SavedRequest {
    private String method;
    private String queryString;
    private String requestURI;

    /**
     * Constructs a new instance from the given HTTP request.
     *
     * @param request the current request to save.
     */
    public SavedRequest(HttpServletRequest request) {
        this.method = request.getMethod();
        this.queryString = request.getQueryString();
        this.requestURI = request.getRequestURI();
    }

    public String getMethod() {
        return method;
    }

    public String getQueryString() {
        return queryString;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public String getRequestUrl() {
        StringBuilder requestUrl = new StringBuilder(getRequestURI());
        if (getQueryString() != null) {
            requestUrl.append("?").append(getQueryString());
        }
        return requestUrl.toString();
    }
}
