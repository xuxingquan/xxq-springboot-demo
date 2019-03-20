package com.xxq.domain.service;

import com.xxq.domain.vo.GoodsVo;
import lombok.NonNull;

public interface GoodsService {
    GoodsVo insert(@NonNull GoodsVo vo);
    GoodsVo queryByid(@NonNull Long id);
    Boolean deleteByid(@NonNull Long id);
    Boolean update(@NonNull GoodsVo vo);
}
