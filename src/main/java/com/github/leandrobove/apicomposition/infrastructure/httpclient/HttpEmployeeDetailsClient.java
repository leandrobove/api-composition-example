package com.github.leandrobove.apicomposition.infrastructure.httpclient;

import com.github.leandrobove.apicomposition.application.client.EmployeeDetailsClientInterface;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HttpEmployeeDetailsClient implements EmployeeDetailsClientInterface {

    private final RestTemplate client;

    private final EmployeeDetailsClientProperties properties;

    public HttpEmployeeDetailsClient(final RestTemplate client, final EmployeeDetailsClientProperties properties) {
        this.client = client;
        this.properties = properties;
    }

    @Override
    @CircuitBreaker(name = "getNameCB", fallbackMethod = "getNameFallback")
    public GetEmployeeNameResponse getName(String employeeId) {
        return client.getForObject(properties.getNameBaseUrl() + "/employees/{employeeId}/name", GetEmployeeNameResponse.class, employeeId);
    }

    private GetEmployeeNameResponse getNameFallback(String employeeId, Throwable e) {
        throw new EmployeeDetailsNotFoundException(String.format("Unable to get name of employee id %s", employeeId));
    }

    @Override
    @CircuitBreaker(name = "getAddressCB", fallbackMethod = "getAddressFallback")
    public GetEmployeeAddressResponse getAddress(String employeeId) {
        return client.getForObject(properties.getAddressBaseUrl() + "/employees/{employeeId}/address", GetEmployeeAddressResponse.class, employeeId);
    }

    private GetEmployeeAddressResponse getAddressFallback(String employeeId, Throwable e) {
        throw new EmployeeDetailsNotFoundException(String.format("Unable to get address of employee id %s", employeeId));
    }

    @Override
    @CircuitBreaker(name = "getPhoneCB", fallbackMethod = "getPhoneFallback")
    public GetEmployeePhoneResponse getPhone(String employeeId) {
        return client.getForObject(properties.getPhoneBaseUrl() + "/employees/{employeeId}/phone", GetEmployeePhoneResponse.class, employeeId);
    }

    private GetEmployeePhoneResponse getPhoneFallback(String employeeId, Throwable e) {
        //return partial response
        return new GetEmployeePhoneResponse("");
    }
}

