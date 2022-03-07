package org.example.open.controller;

import org.example.open.config.AuthConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  Full authentication is required to access this resource 需要完成身份验证才能访问此资源
 *
 * http://127.0.0.1:9091/login/oauth2/dp
 *
 * @author Rao
 * @Date 2021/4/29
 **/
@RequestMapping("/login")
@RestController
public class LoginController {

    @Resource
    private AuthConfig authConfig;

    // 认证授权地址
    private String url = "http://127.0.0.1:9090/oauth/authorize";

    private String callbackUrl = "http://127.0.0.1:9091/login/callback/dp";

    @GetMapping("/oauth2/dp")
    public void dpLogin(HttpServletResponse response) throws IOException {

        //重定向到dp登录页面
        String auth2Url = url + "?" +
                "client_id=" + authConfig.getClientId() +
                "&response_type=code" +
                // ? 这个到底是啥
                "&scope=user_basic_info" +
                // 回调地址
                "&redirect_uri=" + callbackUrl;
        response.sendRedirect(auth2Url);
    }

}
