package com.xxq.controller;


import com.xxq.infrastructure.persistence.entity.Seckill;
import com.xxq.infrastructure.persistence.mapper.SeckillMapper;
import com.xxq.infrastructure.persistence.mapper.SeckillOrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 秒杀商品的控制层
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SeckillMapper seckillMapper;
    @Autowired
    private SeckillOrderMapper seckillOrderMapper;
    @RequestMapping("/list")
    public List<Seckill> findSeckillList(Model model) {
        List<Seckill> list = seckillMapper.findAll();
        model.addAttribute("list", list);
        return list;
    }
    @ResponseBody
    @RequestMapping("/findById")
    public Seckill findById(@RequestParam("id") Long id) {
        return seckillMapper.findById(id);
    }

}
