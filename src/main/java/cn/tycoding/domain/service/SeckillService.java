package cn.tycoding.domain.service;

import cn.tycoding.controller.response.ExposerResp;
import cn.tycoding.controller.response.SeckillExecutionResp;
import cn.tycoding.infrastructure.persistence.entity.Seckill;
import cn.tycoding.common.exception.RepeatKillException;
import cn.tycoding.common.exception.SeckillCloseException;
import cn.tycoding.common.exception.SeckillException;

import java.math.BigDecimal;
import java.util.List;

/**
 * 业务接口：应该站在"使用者"的角度设计，比如：
 * 1.定义方法的颗粒度要细
 * 2.方法的参数要明确且简练，不建议用类似Map这种让使用者封装一堆参数传递过来
 * 3.方法的return类型，除了要明确返回值类型，还应该指定该方法可能抛出的异常
 *
 * @auther TyCoding
 * @date 2018/10/6
 */
public interface SeckillService {

    /**
     * 获取所有的秒杀商品列表
     *
     * @return
     */
    List<Seckill> findAll();

    /**
     * 获取某一条商品秒杀信息
     *
     * @param seckillId
     * @return
     */
    Seckill findById(long seckillId);

    /**
     * 秒杀开始时输出暴露秒杀的地址
     * 否者输出系统时间和秒杀时间
     *
     * @param seckillId
     */
    ExposerResp exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀的操作
     *
     * @param seckillId
     * @param userPhone
     * @param money
     * @param md5
     */
    SeckillExecutionResp executeSeckill(long seckillId, BigDecimal money, long userPhone, String md5)
            throws SeckillException, RepeatKillException, SeckillCloseException;


}