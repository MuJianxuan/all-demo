package org.example.auth2.data;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Rao
 * @Date 2021/4/22
 **/
@Configuration
public class DataConfig {

    @Bean
    public Map<String,InMemoryUser> dpUserData(){
        HashMap<String, InMemoryUser> map = new HashMap<>();
        map.put("1",new InMemoryUser().setId("1").setName("i love you!"));
        map.put("2",new InMemoryUser().setId("2").setName("i not love you!"));
        return map;
    }

    @Data
    @Accessors(chain = true)
    public static class InMemoryUser{
        private String id;
        private String name;
    }

}
