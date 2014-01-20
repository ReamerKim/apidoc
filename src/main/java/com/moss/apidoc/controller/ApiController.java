package com.moss.apidoc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.moss.apidoc.model.Api;
import com.moss.apidoc.service.ApiService;

@RestController
@RequestMapping("api")
public class ApiController {
    
    @Inject
    ApiService apiService;
    
    @Inject
    RequestMappingHandlerMapping handler;

    @RequestMapping("requestMappings")
    public List<String> requestMappings() {
        List<String> mappings = new ArrayList<String>();
        for (RequestMappingInfo mapping: handler.getHandlerMethods().keySet()) {
            for (String pattern : mapping.getPatternsCondition().getPatterns()) {
                if (mappings.contains(pattern))
                    continue;
                mappings.add(pattern);
            }
        }
        return mappings;
    }
    
    @RequestMapping("test")
    public String test() {
        return "Test가나다";
    }
    
    @RequestMapping("addApi")
    @ResponseBody
    public int addApi(
            @RequestParam int id,
            @RequestParam String url) {
        Api api = new Api();
        api.setId(id);
        api.setUrl(url);
        return apiService.addApi(api);
    }
}
