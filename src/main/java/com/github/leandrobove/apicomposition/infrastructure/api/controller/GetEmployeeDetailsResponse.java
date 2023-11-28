package com.github.leandrobove.apicomposition.infrastructure.api.controller;

import com.github.leandrobove.apicomposition.domain.entity.Employee;

public record GetEmployeeDetailsResponse(
        String id,
        String name,
        String phone,
        GetEmployeeDetailsAddressResponse address
) {

    public static GetEmployeeDetailsResponse from(final Employee employee) {
        return new GetEmployeeDetailsResponse(
                employee.getId(),
                employee.getName(),
                employee.getPhone(), GetEmployeeDetailsAddressResponse.from(employee)
        );
    }
}
