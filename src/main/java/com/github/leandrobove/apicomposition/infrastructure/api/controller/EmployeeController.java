package com.github.leandrobove.apicomposition.infrastructure.api.controller;

import com.github.leandrobove.apicomposition.application.service.EmployeeService;
import com.github.leandrobove.apicomposition.domain.entity.Employee;
import com.github.leandrobove.apicomposition.infrastructure.httpclient.GetEmployeeAddressResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/employees", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(final EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GetEmployeeDetailsResponse> getEmployeeDetails(@PathVariable("employeeId") String employeeId) throws InterruptedException, ExecutionException {
        Employee employee = this.getEmployeeDetailsAsync(employeeId);

        GetEmployeeDetailsResponse responseBody = GetEmployeeDetailsResponse.from(employee);

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(Duration.ofMinutes(30)))
                .body(responseBody);
    }

    /*
     * Executes http calls in a fork join pool and waits until all requests are completed.
     */
    private Employee getEmployeeDetailsAsync(final String employeeId) throws ExecutionException, InterruptedException {
        log.info("getEmployeeDetailsAsync Started " + Thread.currentThread());

        CompletableFuture<GetEmployeeAddressResponse> employeeAddress = employeeService.getAddress(employeeId);
        CompletableFuture<String> employeeName = employeeService.getName(employeeId);
        CompletableFuture<String> employeePhone = employeeService.getPhone(employeeId);

        // Wait until they are all done
        CompletableFuture.allOf(employeeAddress, employeeName, employeePhone).join();
        log.info("getEmployeeDetailsAsync Finished " + Thread.currentThread());

        var getEmployeeAddressResponse = employeeAddress.get();

        Employee employee = new Employee(employeeId, employeeName.get(), employeePhone.get(), getEmployeeAddressResponse.toEntity());
        log.info("Employee {}", employee);

        return employee;
    }
}
