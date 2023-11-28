package com.github.leandrobove.apicomposition.infrastructure.api.controller;

public record GetEmployeeDetailsResponse(
        String id,
        String name,
        String phone,
        GetEmployeeDetailsAddressResponse address
) {
}
