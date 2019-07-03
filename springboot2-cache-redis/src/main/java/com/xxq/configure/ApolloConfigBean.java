package com.xxq.configure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
@Setter
@Getter
public class ApolloConfigBean {
    @Value("${supported:false}")
    private boolean supported;
}
