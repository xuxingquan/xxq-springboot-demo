package com.xxq.controller;

import com.xxq.common.BaseResult;
import com.xxq.configure.ConfigDomain;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
@Slf4j
@Controller
@RequestMapping("/demo")
public class DemoController {
    @Autowired
    private ConfigDomain domain;
    @ResponseBody
    @GetMapping(value = "/add")
    public BaseResult<Boolean> add() {
        Map<String, String> stringMap = domain.getTopLinks();
        if (MapUtils.isNotEmpty(stringMap)){
            stringMap.entrySet().forEach(entry->{
                log.info("key="+entry.getKey()+", value="+entry.getValue());
            });
        }
        return BaseResult.ok(true);
    }

}
