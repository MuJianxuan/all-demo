package org.example.open.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Rao
 * @Date 2021/4/29
 **/
@Slf4j
@RequestMapping("/login/callback")
@RestController
public class LoginCallbackController {

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/dp")
    public String dpOauth2LoginCallback(@RequestParam("code") String code) throws JsonProcessingException {
        log.info("code:{}",code);

        //访问远程获取access_token
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", "c1");
        params.add("client_secret", "123");
        // 重定向的地址？  为什么要重定向地址  ： 设置
        params.add("redirect_uri", "http://127.0.0.1:9091/login/callback/dp");
        params.add("grant_type", "authorization_code");
        Map<String, String> resp = (Map<String,String>)restTemplate.postForObject("http://127.0.0.1:9090/oauth/token", params, Map.class);

        log.info("{}",new ObjectMapper().writeValueAsString(resp));
        //可以获取到token

        //请求数据
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + resp.get("access_token"));
        System.out.println("access_token值:"+ resp.get("access_token"));
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<Object> exchange = restTemplate.exchange("http://127.0.0.1:9092/resource/user/1", HttpMethod.GET, httpEntity, Object.class);
        log.info("结果值：{}" , exchange.toString());

        //请求数据
        ResponseEntity<Object> exchangeResult = restTemplate.exchange("http://127.0.0.1:9092/resource/user/1", HttpMethod.GET, httpEntity, Object.class);
        log.info("结果值：{}" , exchangeResult.toString());

        return "sucess";
    }

}
