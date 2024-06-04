package com.github.leandrobove.apicomposition.infrastructure.employee;

import com.github.leandrobove.apicomposition.domain.Employee;
import com.github.leandrobove.apicomposition.domain.EmployeeGateway;
import com.github.leandrobove.apicomposition.infrastructure.employee.client.EmployeeAddressClient;
import com.github.leandrobove.apicomposition.infrastructure.employee.client.EmployeeNameClient;
import com.github.leandrobove.apicomposition.infrastructure.employee.client.EmployeePhoneClient;
import com.github.leandrobove.apicomposition.infrastructure.employee.models.GetEmployeeAddressResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;

@Component
@Slf4j
public class EmployeeGatewayImpl implements EmployeeGateway {

    private final EmployeeNameClient employeeNameClient;
    private final EmployeeAddressClient employeeAddressClient;
    private final EmployeePhoneClient employeePhoneClient;

    public EmployeeGatewayImpl(
            final EmployeeNameClient employeeNameClient,
            final EmployeeAddressClient employeeAddressClient,
            final EmployeePhoneClient employeePhoneClient
    ) {
        this.employeeNameClient = Objects.requireNonNull(employeeNameClient);
        this.employeeAddressClient = Objects.requireNonNull(employeeAddressClient);
        this.employeePhoneClient = Objects.requireNonNull(employeePhoneClient);
    }

    /*
     * Executes http calls in a fork join pool and waits until all requests are completed.
     */
    @Override
    public Optional<Employee> findById(final String employeeId) {
        log.info("getEmployeeDetailsAsync Started {}", Thread.currentThread());

        CompletableFuture<GetEmployeeAddressResponse> employeeAddress = this.getAddress(employeeId);
        CompletableFuture<String> employeeName = this.getName(employeeId);
        CompletableFuture<String> employeePhone = this.getPhone(employeeId);

        // Wait until they are all done
        CompletableFuture.allOf(employeeAddress, employeeName, employeePhone).join();
        log.info("getEmployeeDetailsAsync Finished {}", Thread.currentThread());

        try {
            var getEmployeeAddressResponse = employeeAddress.get();

            Employee employee = new Employee(employeeId, employeeName.get(), employeePhone.get(), getEmployeeAddressResponse.toEntity());
            log.info("Employee {}", employee);

            return Optional.ofNullable(employee);

        } catch (final CompletionException | ExecutionException | InterruptedException ex) {
            log.error("error", ex);
        }

        return Optional.empty();
    }

    private CompletableFuture<String> getName(final String employeeId) {
        return CompletableFuture.supplyAsync(() -> {
            log.info("getName starts in {}", Thread.currentThread());

            var response = employeeNameClient.get(employeeId);

            log.info("employeeNameData completed");

            return response.name();
        });
    }

    private CompletableFuture<GetEmployeeAddressResponse> getAddress(final String employeeId) {
        return CompletableFuture.supplyAsync(() -> {
            log.info("getAddress starts in {}", Thread.currentThread());

            var response = employeeAddressClient.get(employeeId);

            log.info("employeeAddressData completed");

            return response;
        });
    }

    private CompletableFuture<String> getPhone(final String employeeId) {
        return CompletableFuture.supplyAsync(() -> {
            log.info("getPhone starts in {}", Thread.currentThread());
            var response = employeePhoneClient.get(employeeId);
            log.info("employeePhoneData completed");

            return response.phone();
        });
    }
}
