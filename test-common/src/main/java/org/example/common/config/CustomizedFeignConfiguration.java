package org.example.common.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import lombok.extern.slf4j.Slf4j;
import org.example.common.result.Result;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @author Rao
 * @Date 2021/4/13
 **/
public class CustomizedFeignConfiguration {

    @Bean
    public Decoder feignDecoder() {
        return new FeignResultDecoder();
    }

    @Slf4j
    static class FeignResultDecoder implements Decoder {

        /**
         * void 方法不会进入此解码中
         * @param response
         * @param type
         * @return
         * @throws IOException
         * @throws DecodeException
         * @throws FeignException
         */
        @Override
        public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
            if (response.body() == null) {
                throw new RuntimeException("请求服务器数据获取失败！");
            }
            String bodyStr = Util.toString(response.body().asReader());

            //对结果进行转换
            Result<JSON> result = JSON.parseObject(bodyStr, new TypeReference<Result<JSON>>(){});
            //如果返回错误，且为内部错误，则直接抛出异常
            if (! Result.SUCCESS.equals(result.getCode() )) {
                throw new RuntimeException("服务器异常！");
            }

            return result.getData().toJavaObject(type);
        }
    }

}
