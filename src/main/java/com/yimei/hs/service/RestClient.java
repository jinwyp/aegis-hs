package com.yimei.hs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by hary on 2017/9/21.
 */

@Service
public class RestClient {

    private String baseUrl = "asdfasdfadsf";

    @Autowired
    RestTemplate restTemplate;


//    // 如果是kotlin就可以
//    public final <P, R> R post(P param, String url) {
//        R r = restTemplate.postForEntity(baseUrl + url, param, R.class)
//    }

}
