package com.github.leandrobove.apicomposition.infrastructure.api.controller;

import com.github.leandrobove.apicomposition.domain.Employee;

public record GetEmployeeDetailsAddressResponse(
        String street,
        String number,
        String zipCode, String city,
        String country
) {

    public static GetEmployeeDetailsAddressResponse from(Employee employee) {
        return new GetEmployeeDetailsAddressResponse(employee.getAddress().street(), employee.getAddress().number(),
                employee.getAddress().zipCode(), employee.getAddress().city(), employee.getAddress().country());
    }
}
