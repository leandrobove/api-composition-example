package com.github.leandrobove.apicomposition.infrastructure.employee.client;

import com.github.leandrobove.apicomposition.infrastructure.config.annotations.EmployeeAddressClientQualifier;
import com.github.leandrobove.apicomposition.infrastructure.employee.models.GetEmployeeAddressResponse;
import com.github.leandrobove.apicomposition.infrastructure.exceptions.EmployeeDetailsNotFoundException;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Objects;

import static com.github.leandrobove.apicomposition.infrastructure.config.CacheConfig.EMPLOYEE_ADDRESS_CACHE;

@Component
public class EmployeeAddressRestClient implements EmployeeAddressClient {

    public static final String NAMESPACE = "employee-address";

    private final RestClient restClient;

    public EmployeeAddressRestClient(@EmployeeAddressClientQualifier final RestClient restClient) {
        this.restClient = Objects.requireNonNull(restClient);
    }

    @Override
    @CircuitBreaker(name = NAMESPACE, fallbackMethod = "fallback")
    @Retry(name = NAMESPACE, fallbackMethod = "fallback")
    @Bulkhead(name = NAMESPACE, fallbackMethod = "fallback")
    @Cacheable(cacheNames = EMPLOYEE_ADDRESS_CACHE, key = "#employeeId")
    public GetEmployeeAddressResponse get(String employeeId) {
        return this.restClient
                .get()
                .uri("/employees/{employeeId}/address", employeeId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(GetEmployeeAddressResponse.class)
                .getBody();
    }

    private GetEmployeeAddressResponse fallback(String employeeId, Throwable e) {
        throw new EmployeeDetailsNotFoundException(String.format("Unable to get address of employee id %s", employeeId));
    }
}
