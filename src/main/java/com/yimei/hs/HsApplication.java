package com.yimei.hs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication(scanBasePackages = {"com.yimei.hs"})
public class HsApplication {

    public static void main(String[] args) {
        SpringApplication.run(HsApplication.class, args);
    }


}
