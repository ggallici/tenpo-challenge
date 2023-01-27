package com.ggallici.tenpo.repositories;

import com.ggallici.tenpo.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarsRepository extends JpaRepository<Car, Long> {
//    List<Car> findByModel(String model);
}
