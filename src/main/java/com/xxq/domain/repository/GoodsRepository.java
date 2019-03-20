package com.xxq.domain.repository;

import com.xxq.common.util.WrappedBeanCopier;
import com.xxq.domain.vo.GoodsVo;
import com.xxq.infrastructure.persistence.entity.Goods;
import com.xxq.infrastructure.persistence.mapper.GoodsMapper;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Objects;

@Repository
public class GoodsRepository {
    @Autowired
    private GoodsMapper goodsMapper;
    
    public GoodsVo queryByid(@NonNull Long id){
        Goods goods = goodsMapper.selectByPrimaryKey(id);
        return WrappedBeanCopier.copyProperties(goods,GoodsVo.class);
    }

    public Boolean deleteByid(@NonNull Long id){
        int i = goodsMapper.deleteByPrimaryKey(id);
        return i > 0;
    }

    public Boolean update(@NonNull GoodsVo vo){
        if (Objects.isNull(vo.getId())) return false;
        Goods goods = WrappedBeanCopier.copyProperties(vo, Goods.class);
        goods.setUpdateTime(new Date());
        int i = goodsMapper.updateByPrimaryKeySelective(goods);
        return i > 0;
    }

    public GoodsVo insert(@NonNull GoodsVo vo){
        Goods goods = WrappedBeanCopier.copyProperties(vo, Goods.class);
        Date now = new Date();
        goods.setCreateTime(now);
        goods.setUpdateTime(now);
        goodsMapper.insert(goods);
        return WrappedBeanCopier.copyProperties(goods,GoodsVo.class);
    }

}
