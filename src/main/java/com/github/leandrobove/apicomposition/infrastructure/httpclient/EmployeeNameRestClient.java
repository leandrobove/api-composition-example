package com.github.leandrobove.apicomposition.infrastructure.httpclient;

import com.github.leandrobove.apicomposition.application.client.EmployeeNameClient;
import com.github.leandrobove.apicomposition.infrastructure.config.annotations.EmployeeNameClientQualifier;
import com.github.leandrobove.apicomposition.infrastructure.exceptions.EmployeeDetailsNotFoundException;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Objects;

import static com.github.leandrobove.apicomposition.infrastructure.config.CacheConfig.EMPLOYEE_NAME_CACHE;

@Component
public class EmployeeNameRestClient implements EmployeeNameClient {

    public static final String NAMESPACE = "employee-name";

    private final RestClient restClient;

    public EmployeeNameRestClient(@EmployeeNameClientQualifier final RestClient restClient) {
        this.restClient = Objects.requireNonNull(restClient);
    }

    @Override
    @CircuitBreaker(name = NAMESPACE, fallbackMethod = "fallback")
    @Retry(name = NAMESPACE, fallbackMethod = "fallback")
    @Bulkhead(name = NAMESPACE, fallbackMethod = "fallback")
    @Cacheable(cacheNames = EMPLOYEE_NAME_CACHE, key = "#employeeId")
    public GetEmployeeNameResponse get(final String employeeId) {
        return this.restClient
                .get()
                .uri("/employees/{employeeId}/name", employeeId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(GetEmployeeNameResponse.class)
                .getBody();
    }

    private GetEmployeeNameResponse fallback(String employeeId, Throwable e) {
        throw new EmployeeDetailsNotFoundException(String.format("Unable to get name of employee id %s", employeeId));
    }
}
