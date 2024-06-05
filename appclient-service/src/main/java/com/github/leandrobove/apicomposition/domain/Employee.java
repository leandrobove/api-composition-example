package com.github.leandrobove.apicomposition.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Employee {

    @EqualsAndHashCode.Include
    private String id;
    private String name;
    private String phone;
    private Address address;
}
