package org.example.auth2.config;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.annotation.Resource;

/**
 *  同一个 资源服务器且认证服务器在的话不需要配置那啥
 * @author Rao
 * @Date 2021/4/30
 **/
//@EnableResourceServer
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true) //开启方法上的鉴权
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Resource
    private TokenStore tokenStore;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("r2").stateless(true)
                .tokenStore( tokenStore).stateless(true);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        super.configure(http);
//        http.formLogin().permitAll()
//                .and()
//                .csrf()
//                .disable();;
    }

    //    @Override
//    public void configure(HttpSecurity http) throws Exception {
//
//        super.configure( http);
//
//        http.formLogin().permitAll();

        //使用注解式鉴权

//        http.authorizeRequests()
//                .antMatchers("/admin/**").hasRole("admin")
//                .anyRequest().authenticated();

//       super.configure(http);




//        http.authorizeRequests().antMatchers("/**").fullyAuthenticated().and().formLogin();
//                // 退出 允许
//                .and().logout().permitAll()
//                // 表单提交  允许
//                .and().formLogin().permitAll()
//                //  异常处理  访问拒绝处理程序   O Auth 2访问被拒绝处理程序
//                .and().exceptionHandling()
//                // 资源访问拒绝处理
//                .accessDeniedHandler( new OAuth2AccessDeniedHandler());
//    }
}
