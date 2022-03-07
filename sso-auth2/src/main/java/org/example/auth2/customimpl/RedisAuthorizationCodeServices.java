package org.example.auth2.customimpl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author Rao
 * @Date 2021/4/28
 **/
@Slf4j
public class RedisAuthorizationCodeServices extends RandomValueAuthorizationCodeServices {

    private RedisTemplate<Object,Object> redisTemplate;

    public RedisAuthorizationCodeServices(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void store(String code, OAuth2Authentication authentication) {
        // 10分钟
        this.redisTemplate.opsForValue().set(code,authentication,60 * 10 , TimeUnit.MINUTES);
    }

    @Override
    protected OAuth2Authentication remove(String code) {
        OAuth2Authentication value = (OAuth2Authentication)this.redisTemplate.opsForValue().get(code);
        try {
            return value;
        }finally {
            if (value != null && !  Optional.ofNullable( this.redisTemplate.delete(code)).orElse(false)) {
                log.error("第一次删除code失败！尝试再次删除：" + this.redisTemplate.delete(code));
            }
        }

    }
}
