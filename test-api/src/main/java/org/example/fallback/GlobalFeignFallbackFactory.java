package org.example.fallback;

import cn.hutool.aop.ProxyUtil;
import cn.hutool.core.util.StrUtil;
import feign.Request;
import feign.Target;
import lombok.extern.slf4j.Slf4j;
import org.example.common.result.Result;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 不可用
 * @author Rao
 * @Date 2021/4/15
 **/
@Slf4j
@Component
public class GlobalFeignFallbackFactory<T> extends ExtFallbackFactory<T> {

    @Override
    public T create(Method method, Throwable cause) {
        return createFallbackService(cause, method.getDeclaringClass());
    }

    private T createFallbackService(Throwable ex, Class<?> targetClass) {


//        Throwable[] causeChain = THROWABLE_ANALYZER.determineCauseChain(ex);
//        RetryableException ase1 = (RetryableException) THROWABLE_ANALYZER.getFirstThrowableOfType(RetryableException.class, causeChain);
//        log.error("服务出错了", ex);
//        if (ase1 != null) {
//            return toResponse("服务[{}]接口调用超时！", ase1.request());
//        }
//        FeignException ase2 = (FeignException) THROWABLE_ANALYZER.getFirstThrowableOfType(FeignException.class, causeChain);
//        if (ase2 != null) {
//            return toResponse("服务[{}]接口调用出错了！", ase2.request());
//        }

        log.info("爷进来了！");

        // 创建一个JDK代理类
        return ProxyUtil.newProxyInstance((proxy, method, args) -> new Result<Object>().setCode(500).setMessage("爷统一拒绝"), targetClass);
    }

}

