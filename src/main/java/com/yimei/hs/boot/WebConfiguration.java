package com.yimei.hs.boot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yimei.hs.boot.support.Java8TimeModule;
import com.yimei.hs.boot.web.CurrentUserMethodArgumentHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.PostConstruct;
import javax.servlet.MultipartConfigElement;
import javax.validation.Validator;
import java.util.List;

/**
 * Created by xiangyang on 16/7/4.
 */
@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected CurrentUserMethodArgumentHandler currentUserMethodArgumentHandler;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/bower_components/**").addResourceLocations("file:./src-web/bower_components/");
        registry.addResourceHandler("/styles/**").addResourceLocations("file:./src-web/app/styles/");
        registry.addResourceHandler("/images/**").addResourceLocations("file:./src-web/app/images/");
        registry.addResourceHandler("/scripts/**").addResourceLocations("file:./src-web/app/scripts/");
        registry.addResourceHandler("/files/**").addResourceLocations("file:../files/");
    }


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(currentUserMethodArgumentHandler);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    }

    @PostConstruct
    private void jacksonConfig() {
        objectMapper.registerModule(new Java8TimeModule());
    }

    @Bean(name = "validator")
    public Validator createBeanValidator() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize("10MB");
        factory.setMaxRequestSize("10MB");
        return factory.createMultipartConfig();
    }


}