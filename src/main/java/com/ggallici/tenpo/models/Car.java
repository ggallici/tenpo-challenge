package com.ggallici.tenpo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Entity
@AllArgsConstructor
public class Car {
    @Id
    private Long id;
    private String model;

    public Car() { }
}
