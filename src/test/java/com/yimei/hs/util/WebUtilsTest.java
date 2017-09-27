package com.yimei.hs.util;

import com.yimei.hs.boot.persistence.BaseFilter;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by hary on 2017/9/27.
 */
public class WebUtilsTest {

    static class TestClass extends BaseFilter<TestClass> {
        private Long orderId;
        private String name;
    }

    @Test
    public void getUrlTemplate() throws Exception {
        String template = WebUtils.getUrlTemplate(TestClass.class);
        System.out.println(template);
    }

}