package com.xxq.domain.service;

import com.github.pagehelper.PageInfo;
import com.xxq.controller.response.GoodsResp;
import com.xxq.domain.vo.GoodsVo;
import lombok.NonNull;

import java.util.List;

public interface GoodsService {
    GoodsVo insert(@NonNull GoodsVo vo);
    GoodsVo queryByid(@NonNull Long id);
    Boolean deleteByid(@NonNull Long id);
    Boolean update(@NonNull GoodsVo vo);
    PageInfo queryByPage(int currentPage, int pageSize);
}
