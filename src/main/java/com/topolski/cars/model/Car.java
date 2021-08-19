package com.topolski.cars.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "mark cannot be null")
    private String mark;
    @NotNull(message = "model cannot be null")
    private String model;
    @NotNull(message = "color cannot be null")
    private String color;
    @Column(name = "year_of_production")
    @NotNull(message = "year of production cannot be null")
    private String yearOfProduction;

    public Car() {
    }

    public Car(String mark, String model, String color, String yearOfProduction) {
        this.mark = mark;
        this.model = model;
        this.color = color;
        this.yearOfProduction = yearOfProduction;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getMark() {
        return mark;
    }
    public void setMark(String mark) {
        this.mark = mark;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public String getYearOfProduction() {
        return yearOfProduction;
    }
    public void setYearOfProduction(String yearOfProduction) {
        this.yearOfProduction = yearOfProduction;
    }
}
