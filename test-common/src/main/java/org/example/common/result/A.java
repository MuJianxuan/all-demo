package org.example.common.result;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Rao
 * @Date 2021/4/13
 **/
@Data
public class A implements Serializable {

    private String name = "asd";

    private Map<String,Object> xxx;

    private List<B> lb;

    private B b;

}
