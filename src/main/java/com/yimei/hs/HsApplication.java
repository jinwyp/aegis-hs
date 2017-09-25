package com.yimei.hs;

import com.yimei.hs.ying.mapper.YingInvoiceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {"com.yimei.hs"})
public class HsApplication {

    @Autowired
    private YingInvoiceMapper yingInvoiceMapper;

    public static void main(String[] args) {
        SpringApplication.run(HsApplication.class, args);
    }
}
