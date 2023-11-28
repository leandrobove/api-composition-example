package com.github.leandrobove.apicomposition.domain.entity;

public record Address(
        String street,
        String number,
        String zipCode,
        String city,
        String country
) {
}
