package com.topolski.cars.services;

import com.topolski.cars.model.Car;

import java.util.List;
import java.util.Optional;

public interface CarService {
    List<Car> findAllCars();
    List<Car> findAllCarsByColor(String color);
    Optional<Car> findCarById(Long id);
    Boolean saveCar(Car car);
    Boolean deleteCarById(Long id);
    Boolean modifyCar(Car car);
}
