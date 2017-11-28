package com.example.demo.interceptor;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class RequestLoggingFilterConfig {

    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter () {

        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();

//        filter.setIncludeClientInfo(true);

        filter.setIncludeQueryString(true);

        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(10000);

        filter.setIncludeHeaders(false);
        filter.setAfterMessagePrefix("\r\n ===== Request : [");

        return filter;
    }


}
