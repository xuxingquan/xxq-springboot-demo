package com.xxq.configure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Configuration
public class ConfigDomain {
    @Value("#{${blogTopLinks}}")
    private Map<String, String> topLinks;
    @Value("#{'${blog-list}'.split(',')}")
    private List<Integer> list;
}
