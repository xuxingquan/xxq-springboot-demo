package com.xxq.configure;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableApolloConfig
public class AppConfig {
  @Bean
  public ApolloConfigBean javaConfigBean() {
    return new ApolloConfigBean();
  }
}