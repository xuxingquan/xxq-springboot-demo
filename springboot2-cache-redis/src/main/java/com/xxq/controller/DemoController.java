package com.xxq.controller;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.xxq.common.BaseResult;
import com.xxq.configure.ApolloConfigBean;
import com.xxq.configure.ConfigDomain;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@Slf4j
@Controller
@RequestMapping("/demo")
public class DemoController {
    @Autowired
    private ConfigDomain domain;
    @Autowired
    private ApolloConfigBean apolloConfigBean;
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

    @ResponseBody
    @RequestMapping(value = "/getconf")
    public BaseResult<Boolean> getconf(@RequestParam(defaultValue = "0") Long userId,
                                       @RequestParam(defaultValue = "0") Integer appId,
                                       @RequestParam(defaultValue = "0") Long userid) {
        System.out.println("userId="+userId+",appId="+appId+",userid="+userid);
        boolean supported = apolloConfigBean.isSupported();
        Config config = ConfigService.getAppConfig();
        String key = "testIntValue";
        String defaultValue = "100";
        System.out.println(config.getProperty(key,defaultValue));
        return BaseResult.ok(supported);
    }
}
