package com.topolski.cars.repositories;

import com.topolski.cars.model.Car;
import org.springframework.data.repository.CrudRepository;

public interface CarRepository extends CrudRepository<Car, Long> {
    Iterable<Car> findAllByColor(String color);
}
