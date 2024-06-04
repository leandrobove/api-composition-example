package com.github.leandrobove.apicomposition.infrastructure.employee.client;

import com.github.leandrobove.apicomposition.infrastructure.config.annotations.EmployeePhoneClientQualifier;
import com.github.leandrobove.apicomposition.infrastructure.employee.models.GetEmployeePhoneResponse;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Objects;

import static com.github.leandrobove.apicomposition.infrastructure.config.CacheConfig.EMPLOYEE_PHONE_CACHE;

@Component
public class EmployeePhoneRestClient implements EmployeePhoneClient {

    public static final String NAMESPACE = "employee-phone";

    private final RestClient restClient;

    public EmployeePhoneRestClient(@EmployeePhoneClientQualifier final RestClient restClient) {
        this.restClient = Objects.requireNonNull(restClient);
    }

    @Override
    @CircuitBreaker(name = NAMESPACE, fallbackMethod = "fallback")
    @Retry(name = NAMESPACE, fallbackMethod = "fallback")
    @Bulkhead(name = NAMESPACE, fallbackMethod = "fallback")
    @Cacheable(cacheNames = EMPLOYEE_PHONE_CACHE, key = "#employeeId")
    public GetEmployeePhoneResponse get(final String employeeId) {
        return this.restClient
                .get()
                .uri("/employees/{employeeId}/phone", employeeId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(GetEmployeePhoneResponse.class)
                .getBody();
    }

    private GetEmployeePhoneResponse fallback(String employeeId, Throwable e) {
        //return partial response
        return new GetEmployeePhoneResponse("");
    }
}
