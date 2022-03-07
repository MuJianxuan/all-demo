package org.example.auth2.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Rao
 * @Date 2021/4/28
 **/
@Configuration
public class GlobalSpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    @ConditionalOnMissingBean(PasswordEncoder.class)
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 用户都会在 服务端集成
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // 用户登录
//        auth.userDetailsService()
        auth
                .inMemoryAuthentication()
                .withUser("sang")
                .password(passwordEncoder().encode("123"))
                .roles("admin");
    }

    /**
     * 资源安全
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                // 放行 静态资源
                .ignoring()
                .mvcMatchers("/*.html","*.js","*.json")
                .and()
                .ignoring().antMatchers("/config/**", "/css/**", "/img/**", "/js/**","/webjars/**","/v2/api-docs/**","/swagger-resources/**")
                ;

    }

    /**
     * 请求安全
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {



        http.authorizeRequests()
//                .antMatchers("/admin/**")
//                .hasRole("ADMIN")
//                .antMatchers("/user/**")
//                .access("hasAnyRole('ADMIN','USER')")
//                .antMatchers("/db/**")
//                .access("hasAnyRole('ADMIN') and  hasRole('DBA')")
                .antMatchers("/oauth/*").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .permitAll()
                .and()
                .csrf()
                .disable();
        // 上述配置 不能拦截其他请求，其他请求依旧可以访问！
//                .successHandler()

//                .and()
//                .formLogin()
//                .and()
//                .oauth2Login()
//                .and()
//                .openidLogin()


                ;


//        http.authorizeRequests()
//                .antMatchers("/wuzz/test4","/code/*").permitAll() //不需要保护的资源，可以多个
//                .antMatchers("/wuzz/**").authenticated()// 需要认证得资源，可以多个
//                .and()
//                .formLogin().loginPage("http://localhost:8080/#/login")//自定义登陆地址
//                .loginProcessingUrl("/authentication/form") //登录处理地址
//                .successHandler(myAuthenticationSuccessHandler) // 登陆成功处理器
//                .failureHandler(myAuthenctiationFailureHandler) // 登陆失败处理器
//                .permitAll()
//                .and()
//                .userDetailsService(myUserDetailService)//设置userDetailsService，处理用户信息


    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
