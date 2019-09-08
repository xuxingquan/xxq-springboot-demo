package com.xxq.controller;

import com.xxq.common.BaseResult;
import com.xxq.configure.ConfigDomain;
import com.xxq.controller.request.BannerIndex;
import com.xxq.controller.request.BannerItem;
import com.xxq.controller.request.IndexRn;
import com.xxq.domain.vo.GoodsVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/json")
public class JsonController {
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

    @RequestMapping("/set-banner")
    @ResponseBody
    public BannerIndex setBannerIndex(@RequestBody BannerIndex index){
        log.info("index==>"+index);
        return index;
    }

    @RequestMapping("/set-index")
    @ResponseBody
    public IndexRn setIndex(@RequestBody IndexRn index){
        log.info("index==>"+index);
        return index;
    }

    @RequestMapping("/set-banner-item")
    @ResponseBody
    public List<BannerItem> setBannerItem(@RequestBody List<BannerItem> items){
        log.info("index==>"+items);
        return items;
    }

}
