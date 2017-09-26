package com.yimei.hs.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yimei.hs.boot.api.PageResult;
import com.yimei.hs.boot.api.Result;
import com.yimei.hs.user.entity.Dept;
import com.yimei.hs.user.entity.Party;
import com.yimei.hs.user.entity.Team;
import com.yimei.hs.user.entity.User;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.util.Collections;

/**
 * Created by hary on 2017/9/26.
 */
abstract public class HsTestBase {

    public abstract ObjectMapper getObjectMapper();
    public abstract TestRestTemplate getTestRestTemplate();

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
    protected ParameterizedTypeReference<PageResult<Boolean>> typeReferenceBooleanPage  = new ParameterizedTypeReference<PageResult<Boolean>>() {};

    protected ParameterizedTypeReference<Result<Integer>> typeReferenceInteger  = new ParameterizedTypeReference<Result<Integer>>() {};
    protected ParameterizedTypeReference<PageResult<Integer>> typeReferenceIntegerPage  = new ParameterizedTypeReference<PageResult<Integer>>() {};

    protected ParameterizedTypeReference<Result<String>> typeReferenceString  = new ParameterizedTypeReference<Result<String>>() {};
    protected ParameterizedTypeReference<PageResult<String>> typeReferenceStringPage  = new ParameterizedTypeReference<PageResult<String>>() {};

    protected ParameterizedTypeReference<Result<User>> typeReferenceUser  = new ParameterizedTypeReference<Result<User>>() {};
    protected ParameterizedTypeReference<Result<Team>> typeReferenceTeam  = new ParameterizedTypeReference<Result<Team>>() {};
    protected ParameterizedTypeReference<Result<Dept>> typeReferenceDept  = new ParameterizedTypeReference<Result<Dept>>() {};
    protected ParameterizedTypeReference<Result<Party>> typeReferenceParty  = new ParameterizedTypeReference<Result<Party>>() {};

    protected ParameterizedTypeReference<PageResult<User>> typeReferenceUserPage  = new ParameterizedTypeReference<PageResult<User>>() {};
    protected ParameterizedTypeReference<PageResult<Team>> typeReferenceTeamPage  = new ParameterizedTypeReference<PageResult<Team>>() {};
    protected ParameterizedTypeReference<PageResult<Dept>> typeReferenceDeptPage  = new ParameterizedTypeReference<PageResult<Dept>>() {};
    protected ParameterizedTypeReference<PageResult<Party>> typeReferencePartyPage  = new ParameterizedTypeReference<PageResult<Party>>() {};

}
