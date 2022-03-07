package org.example.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.annotation.Resource;

/**
 * @author Rao
 * @Date 2021/4/30
 **/
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Resource
    private RedisConnectionFactory redisConnectionFactory;

    /**
     * 资源安全
     * @param resources
     * @throws Exception
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        /**
         * stateless >> fasle 为关闭状态，则 access token 使用时的 session id 会被记录，后续请求不携带 access token 也可以正常响应
         * >> true 为打开状态，则每次请求都必须携带 access token 请求才行，否则将无法访问
         */
        resources.resourceId("r1").stateless(true)
        .tokenStore( new RedisTokenStore(redisConnectionFactory));
    }

    /**
     * 请求安全
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 资源请求所携带的 access token 会有一个对应的 scope（该 scope 与 access token 对应的 clientId/clientSecret 在数据库中的 scope 一致），
        // 该 scope 如果与被请求资源的 scope 一致，则表示有访问权限；如果不一致，则表示无访问权限。
        http.authorizeRequests().antMatchers("/**").authenticated()
        .and().authorizeRequests().antMatchers("/resource/**")
                .access("#oauth2.hasScope('user_basic_info')");
    }
}
