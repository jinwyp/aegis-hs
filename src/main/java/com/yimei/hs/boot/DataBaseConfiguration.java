package com.yimei.hs.boot;

import com.yimei.hs.boot.persistence.PageInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Created by xiangyang on 16/8/14.
 */
@Configuration
@MapperScan(basePackages = {"com.yimei.hs.**.mapper"})
public class DataBaseConfiguration {


    @Bean
    public PageInterceptor pageInterceptor() {
        PageInterceptor pageInterceptor = new PageInterceptor();
        return pageInterceptor;
    }


}
