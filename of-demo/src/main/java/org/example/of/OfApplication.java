package org.example.of;

import lombok.extern.slf4j.Slf4j;
import org.example.api.TestMicroFeignClient;
import org.example.common.result.A;
import org.example.common.result.Result;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Resource;

/**
 * @author Rao
 * @Date 2021/4/13
 **/
@Slf4j
@EnableFeignClients(basePackages = {"org.example.api"})
@RestController
@SpringBootApplication(scanBasePackages = {"org.example"})
@EnableDiscoveryClient
@RestControllerAdvice
public class OfApplication {

    public static void main(String[] args) {
        SpringApplication.run(OfApplication.class);

    }

    @Resource
    private TestMicroFeignClient testMicroFeignClient;

    @RequestMapping
    public String test(){

        A result = testMicroFeignClient.test();
        log.info("获取结果 >> {}", result.getName());
        log.info("{}",result.getXxx().get("我爱你"));
        log.info("{}",result.getB());
        log.info("{}",result.getLb());

        return "of成功";
    }

    @RequestMapping("/test1")
    public String test1(){
        testMicroFeignClient.test1();
        return "test1";
    }

    @RequestMapping("/test2")
    public String test2(){
        testMicroFeignClient.test2();
        return "test2";
    }

    @ExceptionHandler(value = Exception.class)
    Result<Object> exceptionHandle(Exception ex){
//        ex.printStackTrace();
        return new Result<Object>().setCode(500).setMessage(ex.getMessage());
    }

}
