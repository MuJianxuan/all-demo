package org.example.micro;

import lombok.extern.slf4j.Slf4j;
import org.example.common.result.A;
import org.example.common.result.B;
import org.example.common.result.Result;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Rao
 * @Date 2021/4/12
 **/
@Slf4j
@EnableFeignClients
@RestController
@SpringBootApplication
@EnableDiscoveryClient
@RestControllerAdvice
public class TestMicroApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestMicroApplication.class);
    }

    @RequestMapping("/test")
    public Result<A> test(){

        log.info("请求进来了！");
//        throw new RuntimeException("爷错了");
        Map<String, Object> map = new HashMap<>();
        map.put("我爱你","asd");
        A a = new A();
        a.setXxx(map);

        a.setLb(Arrays.asList(new B(),new B()));
        return new Result<A>().setData(a);

    }

    @GetMapping("/test1")
    public Result<String> test1(){
        return new Result<String>().setData("我爱你");
    }

    @ExceptionHandler(value = Exception.class)
    Result<Object> exceptionHandle(Exception ex){
        ex.printStackTrace();
        return new Result<Object>().setCode(500).setMessage(ex.getMessage());
    }

}
