package com.topolski.cars.services;

import com.topolski.cars.model.Car;
import com.topolski.cars.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    @Autowired
    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }
    @Override
    public List<Car> findAllCars() {
        Iterable<Car> cars = carRepository.findAll();
        return StreamSupport.stream(cars.spliterator(), true)
                .collect(Collectors.toList());
    }

    @Override
    public List<Car> findAllCarsByColor(String color) {
        Iterable<Car> cars = carRepository.findAllByColor(color);
        return StreamSupport.stream(cars.spliterator(), true)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Car> findCarById(Long id) {
        return carRepository.findById(id);
    }

    @Override
    public Boolean saveCar(Car car) {
        try {
            carRepository.save(car);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean deleteCarById(Long id) {
        try {
            carRepository.deleteById(id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean modifyCar(Car car) {
        return saveCar(car);
    }
}
