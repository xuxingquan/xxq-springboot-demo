package com.xxq.controller;

import com.xxq.common.BaseResult;
import com.xxq.configure.ConfigDomain;
import com.xxq.domain.vo.GoodsVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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

    @RequestMapping("/listCustomers")
    @ResponseBody
    @Cacheable( value = "listCustomers" , key = "#id",condition = "#p0 !=5",unless = "#result.id == 3")
    public GoodsVo listCustomers(@RequestParam int id){
        int i = id;
        log.info("i==>"+i);
        GoodsVo customer = new GoodsVo();
        customer.setId(Long.valueOf(i));
        customer.setPrice(new BigDecimal(i));
        customer.setName("customer"+i);
        return customer;
    }

}
