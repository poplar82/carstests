package com.topolski.cars.controllers;

import com.topolski.cars.model.Car;
import com.topolski.cars.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "/cars", produces = {MediaType.APPLICATION_JSON_VALUE})
public class CarController {
    private final CarService carService;
    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public ResponseEntity<List<Car>> getCars() {
        List<Car> carsList = carService.findAllCars();
        if (carsList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(carsList);
    }

    @GetMapping(value = "/color")
    public ResponseEntity<List<Car>> getCarsByColor(@RequestParam final String color) {
        List<Car> carsList = carService.findAllCarsByColor(color);
        if (carsList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(carsList);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable final Long id) {
        Optional<Car> carOptional = carService.findCarById(id);
        return ResponseEntity.of(carOptional);
    }

    @PostMapping
    public ResponseEntity saveCar(@Validated @RequestBody final Car car) {
        boolean isCarAdded = carService.saveCar(car);
        if (isCarAdded) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(
                error -> {
                    String name = ((FieldError) error).getField();
                    String message = error.getDefaultMessage();
                    errors.put(name, message);
                }
        );
        return errors;
    }

    @PutMapping
    public ResponseEntity modCar(@Validated @RequestBody final Car car) {
        boolean isModified = carService.modifyCar(car);
        if (isModified) {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping
    public ResponseEntity deleteCarById(@RequestParam final Long id) {
        boolean isDeleted = carService.deleteCarById(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
