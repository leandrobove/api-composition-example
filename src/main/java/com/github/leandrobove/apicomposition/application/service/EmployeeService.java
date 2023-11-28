package com.github.leandrobove.apicomposition.application.service;

import com.github.leandrobove.apicomposition.application.client.EmployeeDetailsClientInterface;
import com.github.leandrobove.apicomposition.infrastructure.httpclient.GetEmployeeAddressResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class EmployeeService {

    private final EmployeeDetailsClientInterface client;

    public EmployeeService(final EmployeeDetailsClientInterface client) {
        this.client = client;
    }

    @Async
    public CompletableFuture<String> getName(final String employeeId) {
        log.info("getName starts in thread" + Thread.currentThread());

        var response = client.getName(employeeId);

        log.info("employeeNameData completed");
        return CompletableFuture.completedFuture(response.name());
    }

    @Async
    public CompletableFuture<GetEmployeeAddressResponse> getAddress(final String employeeId) {
        log.info("getAddress starts in thread" + Thread.currentThread());

        var response = client.getAddress(employeeId);

        log.info("employeeAddressData completed");
        return CompletableFuture.completedFuture(response);
    }

    @Async
    public CompletableFuture<String> getPhone(final String employeeId) {
        log.info("getPhone starts in thread" + Thread.currentThread());

        var response = client.getPhone(employeeId);

        log.info("employeePhoneData completed");
        return CompletableFuture.completedFuture(response.phone());
    }
}
