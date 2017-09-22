package com.yimei.hs;

import com.yimei.hs.entity.dto.ying.PageYingInvoiceDTO;
import com.yimei.hs.mapper.YingInvoiceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;


@SpringBootApplication(scanBasePackages = {"com.yimei.hs"})
public class HsApplication {

    @Autowired
    private YingInvoiceMapper yingInvoiceMapper;

//    @PostConstruct
//    public void a(){
//        System.out.println(yingInvoiceMapper.getPage(new PageYingInvoiceDTO()));
//    }

    public static void main(String[] args) {
        SpringApplication.run(HsApplication.class, args);
    }


}
