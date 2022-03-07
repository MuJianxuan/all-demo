package org.example.auth2.config;

import lombok.extern.slf4j.Slf4j;
import org.example.auth2.constant.OAuth2Constants;
import org.example.auth2.customimpl.CustomTokenServices;
import org.example.auth2.customimpl.RedisAuthorizationCodeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import javax.annotation.Resource;

/**
 * @author Rao
 * @Date 2021/4/20
 **/
@Slf4j
@EnableAuthorizationServer
@Configuration
public class GlobalAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Bean
    @ConditionalOnMissingBean(PasswordEncoder.class)
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置 Token 持久化
     * @return
     */
    @Bean
    public TokenStore tokenStore(RedisConnectionFactory redisConnectionFactory){
        return new RedisTokenStore( redisConnectionFactory);
    }

    @Bean
    public AuthorizationCodeServices authorizationCodeServices(RedisTemplate<Object,Object> redisTemplate){
        return new RedisAuthorizationCodeServices( redisTemplate);
    }


    /**
     * token -<>  令牌
     * 授权服务器令牌服务
     * @return
     */
    @Bean
    public AuthorizationServerTokenServices authorizationServerTokenServices(TokenStore tokenStore){

        CustomTokenServices tokenServices = new CustomTokenServices();
        //
        tokenServices.setTokenStore( tokenStore );
        // 刷新token
        tokenServices.setReuseRefreshToken( true);

        //提供有关OAuth2客户端详细信息的服务。
//        tokenServices.setClientDetailsService( );

        return tokenServices;
    }


    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //配置客户端访问

        security
                .allowFormAuthenticationForClients()
                // 令牌密钥访问
                .tokenKeyAccess(OAuth2Constants.TOKEN_KEY_ACCESS )

                // 这个是啥啊
                .checkTokenAccess( OAuth2Constants.CHECK_TOKEN_ACCESS);

    }


    @Resource
    private PasswordEncoder passwordEncoder;

    /**
     * 设置添加用户信息,正常应该从数据库中读取
     */
//    @Bean
//    public UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
//        userDetailsService.createUser(User.withUsername("user_1").password(passwordEncoder().encode("123456"))
//                .authorities("ROLE_USER").build());
//        userDetailsService.createUser(User.withUsername("user_2").password(passwordEncoder().encode("1234567"))
//                .authorities("ROLE_USER").build());
//        return userDetailsService;
//    }

    /**
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 自定义实现 客户端id获取
        clients
                .inMemory()
                .withClient("c1")
                .secret(passwordEncoder.encode("123"))
                .resourceIds("r1","r2")
                // 授权码模式
                .authorizedGrantTypes("authorization_code","refresh_token")
                // 与上面一样的
                .authorities("user")
                // 这个呢？ 这个是配置权限的 客户端权限
                //我有个想法， 开放授权，只需要校验服务端必须存在 某个 scope的权限，而 自己的用户则需要校验
                .scopes("user_basic_info")
                // 重定向地址 (回调地址？)  必须注册？？？
                // 回调校验 传参对不上校验失败！
                .redirectUris("http://127.0.0.1:9091/login/callback/dp");

    }

    @Resource
    private AuthorizationServerTokenServices authorizationServerTokenServices;
    @Resource
    private AuthorizationCodeServices authorizationCodeServices;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints

                // 端点设置 token生成
                .tokenServices( authorizationServerTokenServices)
                // 授权码配置 默认是内存的
                .authorizationCodeServices( authorizationCodeServices)
                // 这里配置得是什么用户 ??
//                .userDetailsService( username -> {
//                   log.info(".........read user ............");
//                    return new User("rao",passwordEncoder.encode("123"), Collections.singletonList(new SimpleGrantedAuthority("admin")));
//                })

                // 定义请求方法
                .allowedTokenEndpointRequestMethods(HttpMethod.GET,HttpMethod.POST);
                // 串改路径映射
//                .pathMapping()
                // 用于保存，检索和撤销用户批准的界面（每个客户，每个范围）。
//                .approvalStore()

                // 密码授权 需要配置 密码授予 AuthenticationManager
//                .authenticationManager(authenticationManager);

    }
}
