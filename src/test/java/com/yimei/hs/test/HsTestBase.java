package com.yimei.hs.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yimei.hs.boot.api.Result;
import com.yimei.hs.boot.persistence.Page;
import com.yimei.hs.same.entity.Order;
import com.yimei.hs.same.entity.OrderConfig;
import com.yimei.hs.same.entity.OrderParty;
import com.yimei.hs.user.entity.Dept;
import com.yimei.hs.user.entity.Party;
import com.yimei.hs.user.entity.Team;
import com.yimei.hs.user.entity.User;
import com.yimei.hs.util.Digests;
import com.yimei.hs.util.Encodes;
import com.yimei.hs.ying.entity.*;
import org.slf4j.Logger;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by hary on 2017/9/26.
 */
abstract public class HsTestBase {

    public abstract ObjectMapper getObjectMapper();
    public abstract TestRestTemplate getTestRestTemplate();
    public abstract Logger getLogger();

    public <T> String printJson(T m) throws JsonProcessingException {
        return getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(m);
    }

    public void setClientInterceptor(String bearer) {
        ClientHttpRequestInterceptor interceptor = new ClientHttpRequestInterceptor() {
            @Override
            public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
                request.getHeaders().add("Authorization", bearer);
                return execution.execute(request, body);
            }
        };
        getTestRestTemplate().getRestTemplate().setInterceptors(Collections.singletonList(interceptor));
    }

    protected ParameterizedTypeReference<Result<Boolean>> typeReferenceBoolean  = new ParameterizedTypeReference<Result<Boolean>>() {};
    protected ParameterizedTypeReference<Result<Page<Boolean>>> typeReferenceBooleanPage  = new ParameterizedTypeReference<Result<Page<Boolean>>>() {};

    protected ParameterizedTypeReference<Result<Integer>> typeReferenceInteger  = new ParameterizedTypeReference<Result<Integer>>() {};
    protected ParameterizedTypeReference<Result<Page<Integer>>> typeReferenceIntegerPage  = new ParameterizedTypeReference<Result<Page<Integer>>>() {};

    protected ParameterizedTypeReference<Result<String>> typeReferenceString  = new ParameterizedTypeReference<Result<String>>() {};
    protected ParameterizedTypeReference<Result<Page<String>>> typeReferenceStringPage  = new ParameterizedTypeReference<Result<Page<String>>>() {};

    protected ParameterizedTypeReference<Result<User>> typeReferenceUser  = new ParameterizedTypeReference<Result<User>>() {};
    protected ParameterizedTypeReference<Result<Team>> typeReferenceTeam  = new ParameterizedTypeReference<Result<Team>>() {};
    protected ParameterizedTypeReference<Result<Dept>> typeReferenceDept  = new ParameterizedTypeReference<Result<Dept>>() {};
    protected ParameterizedTypeReference<Result<Party>> typeReferenceParty  = new ParameterizedTypeReference<Result<Party>>() {};

    protected ParameterizedTypeReference<Result<Page<User>>> typeReferenceUserPage  = new ParameterizedTypeReference<Result<Page<User>>>() {};
    protected ParameterizedTypeReference<Result<Page<Team>>> typeReferenceTeamPage  = new ParameterizedTypeReference<Result<Page<Team>>>() {};
    protected ParameterizedTypeReference<Result<Page<Dept>>> typeReferenceDeptPage  = new ParameterizedTypeReference<Result<Page<Dept>>>() {};
    protected ParameterizedTypeReference<Result<Page<Party>>> typeReferencePartyPage  = new ParameterizedTypeReference<Result<Page<Party>>>() {};


    protected ParameterizedTypeReference<Result<List<User>>> typeReferenceUserList  = new ParameterizedTypeReference<Result<List<User>>>() {};

    protected ParameterizedTypeReference<Result<Order>> typeReferenceOrder  = new ParameterizedTypeReference<Result<Order>>() {};
    protected ParameterizedTypeReference<Result<OrderConfig>> typeReferenceOrderConfig  = new ParameterizedTypeReference<Result<OrderConfig>>() {};
    protected ParameterizedTypeReference<Result<OrderParty>> typeReferenceOrderParty  = new ParameterizedTypeReference<Result<OrderParty>>() {};

    protected ParameterizedTypeReference<Result<Page<Order>>> typeReferenceOrderPage  = new ParameterizedTypeReference<Result<Page<Order>>>() {};
    protected ParameterizedTypeReference<Result<Page<OrderConfig>>> typeReferenceOrderConfigPage  = new ParameterizedTypeReference<Result<Page<OrderConfig>>>() {};
    protected ParameterizedTypeReference<Result<Page<OrderParty>>> typeReferenceOrderPartyPage  = new ParameterizedTypeReference<Result<Page<OrderParty>>>() {};


    public void createUser(String phone, String password) {

        final int HASH_INTERATIONS = 1024;
        final int SALT_SIZE = 8;

        User user = new User();
        user.setDeptId(1L);
        user.setPhone(phone);
        user.setPassword(password);

        byte[] passwordSalt = Digests.generateSalt(SALT_SIZE);
        byte[] hashPassword = Digests.sha1(user.getPassword().getBytes(), passwordSalt, HASH_INTERATIONS);

        user.setIsActive(true);
        user.setIsAdmin(false);
        user.setPassword(Encodes.encodeHex(hashPassword));
        user.setPasswordSalt(Encodes.encodeHex(passwordSalt));
        user.setCreateDate(LocalDateTime.now());
        user.setCreateBy("sys");

        // System.out.println("create user: " + user);
    }


    protected List<Long> createParties(List<Party> parties) throws JsonProcessingException {
        List<Long> rtn = new ArrayList<Long>();
        for (Party p : parties) {

            Result<Party> pt = getTestRestTemplate().exchange("/api/parties", HttpMethod.POST, new HttpEntity<Party>(p), typeReferenceParty).getBody();
            if (pt.getSuccess()) {
                getLogger().info("创建party成功\nPOST {}\nrequest = {}\nresponse = {} ", "/api/parties", printJson(p), printJson(pt.getData()));
                rtn.add(pt.getData().getId());
            } else {
                getLogger().info("创建party失败: " + pt.getError());
                System.exit(-1);
            }
        }
        return rtn;
    }

}
