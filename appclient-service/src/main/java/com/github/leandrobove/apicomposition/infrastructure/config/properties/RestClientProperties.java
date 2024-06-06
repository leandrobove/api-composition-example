package com.github.leandrobove.apicomposition.infrastructure.config.properties;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

import java.time.Duration;

@Slf4j
public class RestClientProperties implements InitializingBean {

    private String baseUrl;
    private Duration readTimeout;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Duration getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(Duration readTimeout) {
        this.readTimeout = readTimeout;
    }

    @Override
    public String toString() {
        return "RestClientProperties{" +
                "baseUrl='" + baseUrl + '\'' +
                ", readTimeout=" + readTimeout +
                '}';
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info(toString());
    }
}
