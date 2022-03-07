package org.example.auth2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 授权认证服务器
 *
 * 1、我们的用户是否也在这个平台认证？
 * 2、我们的用户在别人平台上如何使用这边的账号信息？
 * 3、登录校验要走这个服务还是redis呢？
 * 4、OAuth2 是用来做什么的？服务配置呢？
 * @author Rao
 * @Date 2021/4/19
 *
 *  存在问题: @EnableResourceServer 添加后无法访问接口
 *
 **/
@Slf4j
@SpringBootApplication
public class SsoAuth2Application {

    public static void main(String[] args) {
        SpringApplication.run(SsoAuth2Application.class,args);
    }

}
