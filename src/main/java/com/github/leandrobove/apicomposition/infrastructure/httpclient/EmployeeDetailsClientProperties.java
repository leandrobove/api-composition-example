package com.github.leandrobove.apicomposition.infrastructure.httpclient;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.services")

@Getter
@Setter
public class EmployeeDetailsClientProperties {

    private String nameBaseUrl;
    private String phoneBaseUrl;
    private String addressBaseUrl;
}
