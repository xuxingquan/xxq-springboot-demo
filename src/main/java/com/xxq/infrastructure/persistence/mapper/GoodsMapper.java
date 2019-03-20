package com.xxq.infrastructure.persistence.mapper;

import com.xxq.infrastructure.persistence.entity.Goods;
import com.xxq.infrastructure.persistence.entity.GoodsExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface GoodsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Goods record);

    int insertSelective(Goods record);

    List<Goods> selectByExample(GoodsExample example);

    Goods selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Goods record, @Param("example") GoodsExample example);

    int updateByExample(@Param("record") Goods record, @Param("example") GoodsExample example);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKey(Goods record);
}