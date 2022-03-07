package org.example.api;

import org.example.common.config.CustomizedFeignConfiguration;
import org.example.common.result.A;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Rao
 * @Date 2021/4/13
 **/
@FeignClient(value = "test-micro-service",configuration = CustomizedFeignConfiguration.class)
public interface Test1MicroFeignClient {

    /**
     * 对象会转成String
     * @return
     */
    @GetMapping("/test")
    A test();

    @GetMapping("/test1")
    void test1();

    @GetMapping("/test2")
    void test2();

}
