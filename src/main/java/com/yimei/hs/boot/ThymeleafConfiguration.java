package com.yimei.hs.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.AbstractConfigurableTemplateResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by xiangyang on 16/7/4.
 */
@Configuration
@AutoConfigureAfter({WebMvcAutoConfiguration.class})
public class ThymeleafConfiguration implements ApplicationContextAware {

    public static final String SPRING_PROFILE_DEVELOPMENT = "dev";
    public static final String SPRING_PROFILE_PRODUCTION = "prod";
    private static final Logger logger = LoggerFactory.getLogger(ThymeleafConfiguration.class);
    private ApplicationContext applicationContext;


    @Autowired
    private Environment env;

    @Autowired
    private ThymeleafProperties properties;


    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }


    @Bean
    public ViewResolver viewResolver() {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setOrder(2147483642);
        resolver.setTemplateEngine(templateEngine());
        resolver.setCharacterEncoding("UTF-8");
        return resolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(loadTemplateResolver());
        engine.addDialect(new Java8TimeDialect());
        return engine;
    }


    private ITemplateResolver loadTemplateResolver() {
        AbstractConfigurableTemplateResolver resolver = null;
        if (env.getActiveProfiles().length == 0) {
            throw new RuntimeException("No Spring profile configured, running with default configuration");
        } else {
            logger.info("Running with Spring profile(s) : {}", Arrays.toString(env.getActiveProfiles()));
            Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
            if (activeProfiles.contains(SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(SPRING_PROFILE_PRODUCTION)) {
                throw new RuntimeException("You have misconfigured your application! " +
                        "It should not run with both the 'dev' and 'prod' profiles at the same time.");
            } else if (activeProfiles.contains(SPRING_PROFILE_DEVELOPMENT)) {
                resolver = new FileTemplateResolver();
            } else if (activeProfiles.contains(SPRING_PROFILE_PRODUCTION)) {
                resolver = new ClassLoaderTemplateResolver();
            }
        }
        resolver.setPrefix(this.properties.getPrefix());
        resolver.setSuffix(this.properties.getSuffix());
        resolver.setTemplateMode(this.properties.getMode());
        resolver.setCacheable(this.properties.isCache());
        return resolver;
    }


}
