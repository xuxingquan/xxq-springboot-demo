package com.xxq.controller;

import com.github.pagehelper.PageInfo;
import com.xxq.common.BaseResult;
import com.xxq.common.annotation.DistributLock;
import com.xxq.common.annotation.DistributLockParam;
import com.xxq.common.annotation.Limit;
import com.xxq.common.util.DateUtil;
import com.xxq.controller.request.GoodsInsertReq;
import com.xxq.controller.request.GoodsUpdateReq;
import com.xxq.controller.response.GoodsResp;
import com.xxq.domain.service.GoodsService;
import com.xxq.domain.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private MessageSource messageSource;
    @ResponseBody
    @PostMapping(value = "/add")
    public BaseResult<Boolean> add(@Valid GoodsInsertReq request) {
        GoodsVo vo = new GoodsVo();
        vo.setName(request.getName());
        vo.setPrice(new BigDecimal(request.getPrice()));
        vo.setCustomerId(Long.valueOf(request.getCustomerId()));
        GoodsVo insert = goodsService.insert(vo);
        return BaseResult.ok(Objects.nonNull(insert));
    }

    @ResponseBody
    @GetMapping(value = "/delete")
    @DistributLock(prefix = "goods")//分布式锁
    public BaseResult<Boolean> delete(@DistributLockParam(name = "id") @RequestParam String id) {
        Boolean isSuccess = goodsService.deleteByid(Long.valueOf(id));
        try {
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10));
        } catch (Exception e){

        }
        return BaseResult.ok(isSuccess);
    }

    @ResponseBody
    @PostMapping(value = "/update")
    public BaseResult<Boolean> update(@RequestBody GoodsUpdateReq request) {
        GoodsVo vo = new GoodsVo();
        vo.setId(Long.valueOf(request.getId()));
        vo.setName(request.getName());
        vo.setPrice(new BigDecimal(request.getPrice()));
        vo.setCustomerId(Long.valueOf(request.getCustomerId()));
        Boolean updated = goodsService.update(vo);
        return BaseResult.ok(updated);
    }

    @Limit(key = "goods_queryById", period = 100, count = 2)//分布式限流
    @ResponseBody
    @GetMapping(value = "/query-by-id")
    public BaseResult<GoodsResp> queryById( @RequestParam String id) {
        GoodsVo vo = goodsService.queryByid(Long.valueOf(id));
        if (vo == null) return BaseResult.create("3000",messageSource.getMessage("3000",new Object[]{id},null));
        GoodsResp response = new GoodsResp();
        response.setId(id);
        response.setName(vo.getName());
        response.setPrice(vo.getPrice().toString());
        response.setCustomerId(vo.getCustomerId().toString());
        response.setUpdateTime(DateUtil.defaultFormat(vo.getCreateTime()));
        response.setCreateTime(DateUtil.defaultFormat(vo.getUpdateTime()));
        return BaseResult.ok(response);
    }

    @ResponseBody
    @GetMapping(value = "/query-by-page")
    public BaseResult<PageInfo> queryByPage(@RequestParam int currentPage,
                                            @RequestParam int pageSize) {
        PageInfo pageInfo = goodsService.queryByPage(currentPage, pageSize);
        return BaseResult.ok(pageInfo);
    }

}
