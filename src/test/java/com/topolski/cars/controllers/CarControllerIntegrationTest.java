package com.topolski.cars.controllers;

import org.flywaydb.core.Flyway;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CarControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    Flyway flyway;

    @AfterEach
    void cleanDataBase() {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    @DisplayName("should return all cars")
    void shouldReturnAllCars() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cars"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", Is.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].mark", Is.is("Kia")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].model", Is.is("Optima")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].color", Is.is("RED")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].yearOfProduction", Is.is("2019")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].id", Is.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].mark", Is.is("Audi")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].model", Is.is("S8")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].color", Is.is("RED")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].yearOfProduction", Is.is("2018")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("should return all cars on selected color")
    void shouldReturnAllCarsOnSelectedColor() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cars/color").param("color","RED"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", Is.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].mark", Is.is("Kia")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].model", Is.is("Optima")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].color", Is.is("RED")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].yearOfProduction", Is.is("2019")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Is.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].mark", Is.is("Audi")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].model", Is.is("S8")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].color", Is.is("RED")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].yearOfProduction", Is.is("2018")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("should return error 404 on selected color")
    void shouldReturn404OnSelectedColor() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cars/color").param("color","WHITE"))
                .andExpect(MockMvcResultMatchers.status().is(404))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("should return car by id 1")
    void shouldReturnCarById1() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cars/1"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", Is.is(5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mark", Is.is("Kia")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model", Is.is("Optima")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color", Is.is("RED")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.yearOfProduction", Is.is("2019")))
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    @DisplayName("should return car by id 3")
    void shouldFindCarById3() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cars/3"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", Is.is(5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mark", Is.is("Audi")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model", Is.is("S8")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color", Is.is("RED")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.yearOfProduction", Is.is("2018")))
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    @DisplayName("should return not found for car by id 4")
    void shouldFindCarById4() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cars/4"))
                .andExpect(MockMvcResultMatchers.status().is(404))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("should add car")
    void shouldAddCar() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"mark\":\"BMW\"," +
                                "\"model\":\"E45\"," +
                                "\"color\":\"SILVER\"," +
                                "\"yearOfProduction\":\"2000\"" +
                                "}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
        mockMvc.perform(MockMvcRequestBuilders.get("/cars/4"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", Is.is(5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mark", Is.is("BMW")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model", Is.is("E45")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color", Is.is("SILVER")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.yearOfProduction", Is.is("2000")))
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    @DisplayName("should not add car")
    void shouldNotAddCar() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"mark\":\"BMW\"," +
                                "\"model\":\"E45\"," +
                                "\"color\":\"SILVER\"" +
                                "}"))
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("should modify car")
    void shouldModifyCar() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"id\": 2," +
                                "\"mark\": \"Skoda\"," +
                                "\"model\": \"Octavia\"," +
                                "\"color\": \"ORANGE\"," +
                                "\"yearOfProduction\": \"2000\"" +
                                "}"))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andDo(MockMvcResultHandlers.print());
        mockMvc.perform(MockMvcRequestBuilders.get("/cars/2"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", Is.is(5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mark", Is.is("Skoda")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model", Is.is("Octavia")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color", Is.is("ORANGE")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.yearOfProduction", Is.is("2000")))
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    @DisplayName("should not modify car")
    void shouldNotModifyCar() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"id\": 3," +
                                "\"mark\": \"Skoda\"," +
                                "\"model\": \"Octavia\"," +
                                "\"color\": \"ORANGE\"" +
                                "}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    @DisplayName("should deleted car by id")
    void shouldDeletedCarById() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/cars").param("id", "1"))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("should not deleted car by id")
    void shouldNotDeletedCarById() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/cars").param("id", "5"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }
}
