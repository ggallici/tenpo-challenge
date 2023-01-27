package com.ggallici.tenpo.controllers;

import com.ggallici.tenpo.dtos.CarDto;
import com.ggallici.tenpo.models.Car;
import com.ggallici.tenpo.repositories.CarsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CarsController {
    private final CarsRepository carsRepository;

    @GetMapping("/cars")
    public List<Car> findById() {
        return carsRepository.findAll();
    }

    @PostMapping("/cars")
    public Car save(@RequestBody CarDto car) {
        return carsRepository.save(new Car(car.id(), car.model()));
    }
}
