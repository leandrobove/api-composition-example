package com.github.leandrobove.apicomposition.application.service;

import com.github.leandrobove.apicomposition.application.client.EmployeeAddressClient;
import com.github.leandrobove.apicomposition.application.client.EmployeeNameClient;
import com.github.leandrobove.apicomposition.application.client.EmployeePhoneClient;
import com.github.leandrobove.apicomposition.infrastructure.httpclient.GetEmployeeAddressResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class EmployeeService {

    private final EmployeeNameClient employeeNameClient;
    private final EmployeeAddressClient employeeAddressClient;
    private final EmployeePhoneClient employeePhoneClient;

    public EmployeeService(
            final EmployeeNameClient employeeNameClient,
            final EmployeeAddressClient employeeAddressClient,
            final EmployeePhoneClient employeePhoneClient
    ) {
        this.employeeNameClient = Objects.requireNonNull(employeeNameClient);
        this.employeeAddressClient = Objects.requireNonNull(employeeAddressClient);
        this.employeePhoneClient = Objects.requireNonNull(employeePhoneClient);
    }

    public CompletableFuture<String> getName(final String employeeId) {
        return CompletableFuture.supplyAsync(() -> {
            log.info("getName starts in {}", Thread.currentThread());

            var response = employeeNameClient.get(employeeId);

            log.info("employeeNameData completed");

            return response.name();
        });
    }

    public CompletableFuture<GetEmployeeAddressResponse> getAddress(final String employeeId) {
        return CompletableFuture.supplyAsync(() -> {
            log.info("getAddress starts in {}", Thread.currentThread());

            var response = employeeAddressClient.get(employeeId);

            log.info("employeeAddressData completed");

            return response;
        });
    }

    public CompletableFuture<String> getPhone(final String employeeId) {
        return CompletableFuture.supplyAsync(() -> {
            log.info("getPhone starts in {}", Thread.currentThread());
            var response = employeePhoneClient.get(employeeId);
            log.info("employeePhoneData completed");

            return response.phone();
        });
    }
}
