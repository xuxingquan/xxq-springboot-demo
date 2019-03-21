package com.xxq.controller.request;

import com.google.common.base.MoreObjects;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class GoodsInsertReq {
    @NotBlank
    private String name;
    @NotBlank
    private String price;
    @NotBlank
    @Length(max = 10)
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
