package com.xxq.controller;

import com.xxq.common.BaseResult;
import com.xxq.common.util.DateUtil;
import com.xxq.controller.request.GoodsInsertReq;
import com.xxq.controller.request.GoodsUpdateReq;
import com.xxq.controller.response.GoodsResp;
import com.xxq.domain.service.GoodsService;
import com.xxq.domain.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.Objects;

@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private MessageSource messageSource;
    @ResponseBody
    @PostMapping(value = "/add")
    public BaseResult<Boolean> add(GoodsInsertReq request) {
        GoodsVo vo = new GoodsVo();
        vo.setName(request.getName());
        vo.setPrice(new BigDecimal(request.getPrice()));
        vo.setCustomerId(Long.valueOf(request.getCustomerId()));
        GoodsVo insert = goodsService.insert(vo);
        return BaseResult.ok(Objects.nonNull(insert));
    }

    @ResponseBody
    @GetMapping(value = "/delete")
    public BaseResult<Boolean> delete(String id) {
        Boolean isSuccess = goodsService.deleteByid(Long.valueOf(id));
        return BaseResult.ok(isSuccess);
    }

    @ResponseBody
    @PostMapping(value = "/update")
    public BaseResult<Boolean> update(GoodsUpdateReq request) {
        GoodsVo vo = new GoodsVo();
        vo.setId(Long.valueOf(request.getId()));
        vo.setName(request.getName());
        vo.setPrice(new BigDecimal(request.getPrice()));
        vo.setCustomerId(Long.valueOf(request.getCustomerId()));
        Boolean updated = goodsService.update(vo);
        return BaseResult.ok(updated);
    }

    @ResponseBody
    @GetMapping(value = "/query-by-id")
    public BaseResult<GoodsResp> queryById(String id) {
        GoodsVo vo = goodsService.queryByid(Long.valueOf(id));
        if (vo == null) return BaseResult.create("3000",messageSource.getMessage("3000",new Object[]{id},null));
        GoodsResp response = new GoodsResp();
        response.setId(id);
        response.setName(vo.getName());
        response.setPrice(vo.getPrice().toString());
        response.setCustomerId(vo.getCustomerId().toString());
        response.setUpdateTime(DateUtil.format(vo.getCreateTime()));
        response.setCreateTime(DateUtil.format(vo.getUpdateTime()));
        return BaseResult.ok(response);
    }

}
