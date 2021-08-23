package com.wedul.websocket.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("spring.redis")
public class RedisProperties {

    private String host;
    private int port;
}
