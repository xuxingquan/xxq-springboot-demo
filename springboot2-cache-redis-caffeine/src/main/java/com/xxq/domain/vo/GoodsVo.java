package com.xxq.domain.vo;

import com.google.common.base.MoreObjects;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Getter
@Setter
public class GoodsVo implements Serializable{

    private static final long serialVersionUID = -3985077237964932312L;

    private Long id;
    private String name;
    private BigDecimal price;
    private Long customerId;
    private Date updateTime;
    private Date createTime;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).omitNullValues()
                .add("id", id)
                .add("name", name)
                .add("price", price)
                .add("customerId", customerId)
                .add("updateTime", updateTime)
                .add("createTime", createTime)
                .toString();
    }
}
