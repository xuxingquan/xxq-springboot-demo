package com.xxq.controller.request;

import com.google.common.base.MoreObjects;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class GoodsInsertReq {

    private String name;
    private String price;
    private String customerId;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).omitNullValues()
                .add("name", name)
                .add("price", price)
                .add("customerId", customerId)
                .toString();
    }
}
