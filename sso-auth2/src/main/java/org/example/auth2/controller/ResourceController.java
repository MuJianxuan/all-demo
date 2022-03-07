package org.example.auth2.controller;

import org.example.auth2.data.DataConfig;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 *
 * 资源接口
 * @author Rao
 * @Date 2021/4/22
 **/
@RequestMapping("/resource")
@RestController
public class ResourceController {

    @Resource
    private Map<String, DataConfig.InMemoryUser> dpUserData;

    /**
     * 没有配置 @EnableResourceServer 不会生效 @PreAuthorize("#oauth2.hasScope('all')")
     * @param id
     * @return
     */
//    @PreAuthorize("#                                                                                                                  ggggggggggggggggggggggggggggggggggggggggggggggggggvffggggggggg  `````````('user')")
    @GetMapping("/user/{id}")
    public DataConfig.InMemoryUser hello(@PathVariable("id")String id) {
        return dpUserData.get(id);
    }

}
