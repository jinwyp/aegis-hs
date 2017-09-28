package com.yimei.hs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yimei.hs.test.CangBaseTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by hary on 2017/9/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = HsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional(readOnly = true)
public class CangIntegrationA extends CangBaseTest{

    public static final Logger logger = LoggerFactory.getLogger(CangIntegrationA.class);

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Autowired TestRestTemplate client;

    @Autowired ObjectMapper objectMapper;

    @Override
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    @Override
    public TestRestTemplate getTestRestTemplate() {
        return client;
    }

    @Test
    public void cangIntegration() {
        System.out.println("开始苍押集成测试");
    }
}
