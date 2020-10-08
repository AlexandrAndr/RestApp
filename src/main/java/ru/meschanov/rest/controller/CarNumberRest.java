package ru.meschanov.rest.controller;

import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.meschanov.rest.domains.CarNumberEntity;
import ru.meschanov.rest.repository.CarNumberRepository;
import ru.meschanov.rest.service.impl.CarNumberServiceImpl;

import java.util.Map;

@RestController
@RequestMapping("/number")
@AllArgsConstructor
public class CarNumberRest {

    private CarNumberServiceImpl service;
    private CarNumberRepository repository;

    @GetMapping("/random")
    public String getRandomCarNumber() {
        return service.getRandomCarNumber();
    }

    @GetMapping("/next")
    public String getNextNumber(@RequestParam String number) {
        return service.getNextNumber(number);
    }
}