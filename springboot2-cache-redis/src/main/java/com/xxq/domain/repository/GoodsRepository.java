package com.xxq.domain.repository;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xxq.common.util.WrappedBeanCopier;
import com.xxq.domain.vo.GoodsVo;
import com.xxq.infrastructure.persistence.entity.Goods;
import com.xxq.infrastructure.persistence.entity.GoodsExample;
import com.xxq.infrastructure.persistence.mapper.GoodsMapper;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Cacheable ---> condition/keyGenerator/cacheManager
 * @CacheEvict ---> allentries/beforeInvocation
 */
@Slf4j
@Repository
public class GoodsRepository {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private GoodsMapper goodsMapper;

    @Cacheable(value = "goods", key = "#id", unless = "#result == null")//可能会有缓存击穿问题
    public GoodsVo queryByid(@NonNull Long id) {
        Goods goods = goodsMapper.selectByPrimaryKey(id);
        log.info("queryGoodsVoByid,id={}", Objects.nonNull(goods) ? goods.getId() : StringUtils.EMPTY);
        return WrappedBeanCopier.copyProperties(goods, GoodsVo.class);
    }

    @CacheEvict(value = "goods", key = "#id")
    public Boolean deleteByid(@NonNull Long id) {
        int i = goodsMapper.deleteByPrimaryKey(id);
        return i > 0;
    }

    @CacheEvict(value = "goods", key = "#vo.id")
    public Boolean update(@NonNull GoodsVo vo) {
        if (Objects.isNull(vo.getId())) return false;
        Goods goods = WrappedBeanCopier.copyProperties(vo, Goods.class);
        goods.setUpdateTime(new Date());
        int i = goodsMapper.updateByPrimaryKeySelective(goods);
        return i > 0;
    }

    @CachePut(value = "goods", key = "#result.id", unless = "#result == null")
    public GoodsVo insert(@NonNull GoodsVo vo) {
        Goods goods = WrappedBeanCopier.copyProperties(vo, Goods.class);
        Date now = new Date();
        goods.setCreateTime(now);
        goods.setUpdateTime(now);
        goodsMapper.insert(goods);
        return WrappedBeanCopier.copyProperties(goods, GoodsVo.class);
    }

    /**
     * 分页查询
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    public List<GoodsVo> findByPage(int currentPage, int pageSize) {
        //设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
        PageHelper.startPage(currentPage, pageSize);
        List<Goods> goods = goodsMapper.selectByExample(new GoodsExample());
        Page<Object> localPage = PageHelper.getLocalPage();
        System.out.println("localPage = " + localPage);
        return WrappedBeanCopier.copyPropertiesOfList(goods, GoodsVo.class);
    }

}
