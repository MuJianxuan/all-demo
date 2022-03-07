package org.example.fallback;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.example.api.TestMicroFeignClient;
import org.example.common.result.A;
import org.springframework.stereotype.Component;

/**
 * @author Rao
 * @Date 2021/4/14
 **/
@Slf4j
@Component
public class TestFallbackFactory implements FallbackFactory<TestMicroFeignClient> {
    @Override
    public TestMicroFeignClient create(Throwable throwable) {
        return new TestMicroFeignClient() {
            @Override
            public A test() {
                log.info("爷在,别怕");
                return new A();
            }

            @Override
            public void test1() {

            }

            @Override
            public void test2() {
                log.info("我哦日");
            }
        };
    }
}
