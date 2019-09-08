package com.xxq.controller.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
@Data
public class IndexRn<T> {
    private Integer type;
    private String title;
    private List<T> items;
}
