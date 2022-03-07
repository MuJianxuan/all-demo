package org.example.open.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Rao
 * @Date 2021/4/28
 **/
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "auth")
public class AuthConfig {

    private String clientId;

    /**
     * 密钥 secret
     */
    private String clientSecret;

}
