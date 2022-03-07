package org.example.common.result;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Rao
 * @Date 2021/4/13
 **/
@Data
@Accessors(chain = true)
public class Result<T> {

    public static final Integer SUCCESS = 200;
    public static final Integer FAIL = 500;

    /**
     * 响应码，200为成功
     */
    private Integer code = 200;

    /**
     * 失败时的具体失败信息
     */
    private String message;

    /**
     * 失败信息是否为用户提示，如果是的话框架不会主动拦截该错误
     */
    private boolean userPrompt;

    /**
     * 响应的具体对象
     */
    private T data;
}
