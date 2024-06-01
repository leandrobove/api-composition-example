package com.github.leandrobove.apicomposition.infrastructure.httpclient;

import java.util.Objects;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.github.leandrobove.apicomposition.application.client.EmployeeDetailsClientInterface;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@Service
public class HttpEmployeeDetailsClient implements EmployeeDetailsClientInterface {

    private final RestClient restClient;
    private final EmployeeDetailsClientProperties properties;

    public HttpEmployeeDetailsClient(final RestClient restClient, final EmployeeDetailsClientProperties properties) {
        this.restClient = Objects.requireNonNull(restClient);
        this.properties = Objects.requireNonNull(properties);
    }

    @Override
    @CircuitBreaker(name = "getNameCB", fallbackMethod = "getNameFallback")
    @Retry(name = "name")
    public GetEmployeeNameResponse getName(final String employeeId) {
        return this.restClient
                .get()
                .uri(properties.getNameBaseUrl() + "/employees/{employeeId}/name", employeeId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(GetEmployeeNameResponse.class)
                .getBody();
    }

    private GetEmployeeNameResponse getNameFallback(String employeeId, Throwable e) {
        throw new EmployeeDetailsNotFoundException(String.format("Unable to get name of employee id %s", employeeId));
    }

    @Override
    @CircuitBreaker(name = "getAddressCB", fallbackMethod = "getAddressFallback")
    @Retry(name = "address")
    public GetEmployeeAddressResponse getAddress(final String employeeId) {
        return this.restClient
                .get()
                .uri(properties.getAddressBaseUrl() + "/employees/{employeeId}/address", employeeId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(GetEmployeeAddressResponse.class)
                .getBody();
    }

    private GetEmployeeAddressResponse getAddressFallback(String employeeId, Throwable e) {
        throw new EmployeeDetailsNotFoundException(String.format("Unable to get address of employee id %s", employeeId));
    }

    @Override
    @CircuitBreaker(name = "getPhoneCB", fallbackMethod = "getPhoneFallback")
    @Retry(name = "phone")
    public GetEmployeePhoneResponse getPhone(final String employeeId) {
        return this.restClient
                .get()
                .uri(properties.getPhoneBaseUrl() + "/employees/{employeeId}/phone", employeeId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(GetEmployeePhoneResponse.class)
                .getBody();
    }

    private GetEmployeePhoneResponse getPhoneFallback(String employeeId, Throwable e) {
        //return partial response
        return new GetEmployeePhoneResponse("");
    }
}