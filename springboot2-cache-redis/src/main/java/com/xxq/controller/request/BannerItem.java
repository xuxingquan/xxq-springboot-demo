package com.xxq.controller.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class BannerItem {
    private String id;
    private String imageUrl;
    private String jumpUrl;
}
