package com.github.leandrobove.apicomposition.application.service;

import com.github.leandrobove.apicomposition.application.client.EmployeeDetailsClientInterface;
import com.github.leandrobove.apicomposition.infrastructure.httpclient.GetEmployeeAddressResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class EmployeeService {

    private final EmployeeDetailsClientInterface client;

    public EmployeeService(final EmployeeDetailsClientInterface client) {
        this.client = client;
    }

    public CompletableFuture<String> getName(final String employeeId) {
        return CompletableFuture.supplyAsync(() -> {
            log.info("getName starts in " + Thread.currentThread());

            var response = client.getName(employeeId);

            log.info("employeeNameData completed");

            return response.name();
        });
    }

    public CompletableFuture<GetEmployeeAddressResponse> getAddress(final String employeeId) {
        return CompletableFuture.supplyAsync(() -> {
            log.info("getAddress starts in " + Thread.currentThread());

            var response = client.getAddress(employeeId);

            log.info("employeeAddressData completed");

            return response;
        });
    }

    public CompletableFuture<String> getPhone(final String employeeId) {
        return CompletableFuture.supplyAsync(() -> {
            log.info("getPhone starts in " + Thread.currentThread());
            var response = client.getPhone(employeeId);
            log.info("employeePhoneData completed");

            return response.phone();
        });
    }
}
