package com.xxq.controller;

import com.xxq.controller.response.ExposerResp;
import com.xxq.controller.response.SeckillExecutionResp;
import com.xxq.common.SeckillResult;
import com.xxq.infrastructure.persistence.entity.Seckill;
import com.xxq.common.enums.SeckillStatEnum;
import com.xxq.common.exception.RepeatKillException;
import com.xxq.common.exception.SeckillCloseException;
import com.xxq.common.exception.SeckillException;
import com.xxq.domain.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 秒杀商品的控制层
 *
 * @auther TyCoding
 * @date 2018/10/6
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController {

    @Autowired
    private SeckillService seckillService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/list")
    public String findSeckillList(Model model) {
        List<Seckill> list = seckillService.findAll();
        model.addAttribute("list", list);
        return "page/seckill";
    }

    @ResponseBody
    @RequestMapping("/findById")
    public Seckill findById(@RequestParam("id") Long id) {
        return seckillService.findById(id);
    }

    @RequestMapping("/{seckillId}/detail")
    public String detail(@PathVariable("seckillId") Long seckillId, Model model) {
        if (seckillId == null) {
            return "page/seckill";
        }
        Seckill seckill = seckillService.findById(seckillId);
        model.addAttribute("seckill", seckill);
        if (seckill == null) {
            return "page/seckill";
        }
        return "page/seckill_detail";
    }

    @ResponseBody
    @RequestMapping(value = "/{seckillId}/exposer",
            method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public SeckillResult<ExposerResp> exposer(@PathVariable("seckillId") Long seckillId) {
        SeckillResult<ExposerResp> result;
        try {
            ExposerResp exposerResp = seckillService.exportSeckillUrl(seckillId);
            result = new SeckillResult<>(exposerResp);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result = SeckillResult.create("1000", e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/{seckillId}/{md5}/execution",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<SeckillExecutionResp> execute(@PathVariable("seckillId") Long seckillId,
                                                       @PathVariable("md5") String md5,
                                                       @RequestParam("money") BigDecimal money,
                                                       @CookieValue(value = "killPhone", required = false) Long userPhone) {
        if (userPhone == null) {
            return SeckillResult.create("1000", "未注册");
        }
        try {
            SeckillExecutionResp execution = seckillService.executeSeckill(seckillId, money, userPhone, md5);
            return new SeckillResult(execution);
        } catch (RepeatKillException e) {
            SeckillExecutionResp seckillExecutionResp = new SeckillExecutionResp(seckillId, SeckillStatEnum.REPEAT_KILL);
            return new SeckillResult<>(seckillExecutionResp);
        } catch (SeckillCloseException e) {
            SeckillExecutionResp seckillExecutionResp = new SeckillExecutionResp(seckillId, SeckillStatEnum.END);
            return new SeckillResult<>(seckillExecutionResp);
        } catch (SeckillException e) {
            SeckillExecutionResp seckillExecutionResp = new SeckillExecutionResp(seckillId, SeckillStatEnum.INNER_ERROR);
            return new SeckillResult(seckillExecutionResp);
        }
    }

    @ResponseBody
    @GetMapping(value = "/time/now")
    public SeckillResult<Long> time() {
        Date now = new Date();
        return new SeckillResult(now.getTime());
    }
}
