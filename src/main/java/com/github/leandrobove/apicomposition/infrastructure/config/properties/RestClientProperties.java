package com.github.leandrobove.apicomposition.infrastructure.config.properties;

import java.time.Duration;

public class RestClientProperties {

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
}
