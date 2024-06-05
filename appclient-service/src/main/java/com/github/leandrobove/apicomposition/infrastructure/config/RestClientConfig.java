package com.github.leandrobove.apicomposition.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.leandrobove.apicomposition.infrastructure.config.annotations.EmployeeAddressClientQualifier;
import com.github.leandrobove.apicomposition.infrastructure.config.annotations.EmployeeNameClientQualifier;
import com.github.leandrobove.apicomposition.infrastructure.config.annotations.EmployeePhoneClientQualifier;
import com.github.leandrobove.apicomposition.infrastructure.config.properties.RestClientProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClient;

import java.util.List;

@Configuration
public class RestClientConfig {

    @EmployeeNameClientQualifier
    @Bean
    @ConfigurationProperties(prefix = "app.http-client.employee-name")
    public RestClientProperties employeeNameRestClientProperties() {
        return new RestClientProperties();
    }

    @EmployeeAddressClientQualifier
    @Bean
    @ConfigurationProperties(prefix = "app.http-client.employee-address")
    public RestClientProperties employeeAddressRestClientProperties() {
        return new RestClientProperties();
    }

    @EmployeePhoneClientQualifier
    @Bean
    @ConfigurationProperties(prefix = "app.http-client.employee-phone")
    public RestClientProperties employeePhoneRestClientProperties() {
        return new RestClientProperties();
    }

    @Bean
    @EmployeeNameClientQualifier
    public RestClient employeeNameRestClientConfig(
            @EmployeeNameClientQualifier final RestClientProperties properties,
            final ObjectMapper objectMapper
    ) {
        return restClient(properties, objectMapper);
    }

    @Bean
    @EmployeeAddressClientQualifier
    public RestClient employeeAddressRestClientConfig(
            @EmployeeAddressClientQualifier final RestClientProperties properties,
            final ObjectMapper objectMapper
    ) {
        return restClient(properties, objectMapper);
    }

    @Bean
    @EmployeePhoneClientQualifier
    public RestClient employeePhoneRestClientConfig(
            @EmployeePhoneClientQualifier final RestClientProperties properties,
            final ObjectMapper objectMapper
    ) {
        return restClient(properties, objectMapper);
    }

    private static RestClient restClient(final RestClientProperties properties, final ObjectMapper objectMapper) {
        final var factory = new JdkClientHttpRequestFactory();
        factory.setReadTimeout(properties.getReadTimeout());

        return RestClient.builder()
                .baseUrl(properties.getBaseUrl())
                .requestFactory(factory)
                .messageConverters(converters -> {
                    converters.removeIf(it -> it instanceof MappingJackson2HttpMessageConverter);
                    converters.add(jsonConverter(objectMapper));
                    converters.add(new FormHttpMessageConverter());
                })
                .build();
    }

    private static MappingJackson2HttpMessageConverter jsonConverter(ObjectMapper objectMapper) {
        final var jsonConverter = new MappingJackson2HttpMessageConverter(objectMapper);
        jsonConverter.setSupportedMediaTypes(List.of(MediaType.APPLICATION_JSON));

        return jsonConverter;
    }
}