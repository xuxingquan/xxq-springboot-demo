package com.xxq.controller.response;

import com.google.common.base.MoreObjects;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class GoodsResp {

    private String id;
    private String name;
    private String price;
    private String customerId;
    private String updateTime;
    private String createTime;

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
