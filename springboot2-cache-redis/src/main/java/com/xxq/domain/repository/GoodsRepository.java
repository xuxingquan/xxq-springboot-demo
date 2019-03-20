package com.xxq.domain.repository;

import com.xxq.common.util.WrappedBeanCopier;
import com.xxq.domain.vo.GoodsVo;
import com.xxq.infrastructure.persistence.entity.Goods;
import com.xxq.infrastructure.persistence.mapper.GoodsMapper;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Objects;
@Slf4j
@Repository
public class GoodsRepository {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private GoodsMapper goodsMapper;

    //condition/keyGenerator/cacheManager
    @Cacheable(value = "goods",key = "#id",unless = "#result == null")//可能会有缓存击穿问题
    public GoodsVo queryByid(@NonNull Long id){
        Goods goods = goodsMapper.selectByPrimaryKey(id);
        log.info("queryGoodsVoByid,id={}", Objects.nonNull(goods) ? goods.getId() : "");
        return WrappedBeanCopier.copyProperties(goods,GoodsVo.class);
    }

    @CacheEvict(value = "goods",key = "#id")//allentries/beforeInvocation
    public Boolean deleteByid(@NonNull Long id){
        int i = goodsMapper.deleteByPrimaryKey(id);
        return i > 0;
    }

    @CacheEvict(value = "goods",key = "#vo.id")
    public Boolean update(@NonNull GoodsVo vo){
        if (Objects.isNull(vo.getId())) return false;
        Goods goods = WrappedBeanCopier.copyProperties(vo, Goods.class);
        goods.setUpdateTime(new Date());
        int i = goodsMapper.updateByPrimaryKeySelective(goods);
        return i > 0;
    }

    @CachePut(value = "goods",key = "#result.id",unless = "#result == null")
    public GoodsVo insert(@NonNull GoodsVo vo){
        Goods goods = WrappedBeanCopier.copyProperties(vo, Goods.class);
        Date now = new Date();
        goods.setCreateTime(now);
        goods.setUpdateTime(now);
        goodsMapper.insert(goods);
        return WrappedBeanCopier.copyProperties(goods,GoodsVo.class);
    }

}
