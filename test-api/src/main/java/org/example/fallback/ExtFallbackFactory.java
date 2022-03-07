package org.example.fallback;

import feign.hystrix.FallbackFactory;

import java.lang.reflect.Method;

/**
 * 不可用！
 * @author Rao
 * @Date 2021/4/15
 **/
public abstract class ExtFallbackFactory<T> implements FallbackFactory<T> {
    @Override
    public T create(Throwable cause) {
        return null;
    }

    /**
     * 需要method获取对应的接口class
     * @param method
     * @param cause
     * @return
     */
    public abstract T create(Method method, Throwable cause);
}
