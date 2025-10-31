package com.todolist.todoservice.client;

import com.todolist.todoservice.dto.UserInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * UserService 客户端 - 用于与用户服务通信
 */
@Component
public class UserServiceClient {

    @Value("${user-service.url}")
    private String userServiceUrl;

    private final RestTemplate restTemplate;

    public UserServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * 验证 Token 并获取用户信息
     */
    public UserInfo verifyToken(String token) {
        String url = userServiceUrl + "/api/v1/auth/verify";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("token", token);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);

        try {
            UserInfo response = restTemplate.postForObject(url, request, UserInfo.class);
            return response;
        } catch (Exception e) {
            // Token 验证失败
            UserInfo userInfo = new UserInfo();
            userInfo.setValid(false);
            return userInfo;
        }
    }
}
