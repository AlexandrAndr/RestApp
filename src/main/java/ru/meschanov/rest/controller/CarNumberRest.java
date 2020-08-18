package ru.meschanov.rest.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.meschanov.rest.service.impl.CarNumberServiceImpl;

@RestController
@RequestMapping("/number")
@AllArgsConstructor
public class CarNumberRest {

    private CarNumberServiceImpl service;

    @GetMapping("/random")
    public String getRandomCarNumber() {
        return service.getRandomCarNumber();
    }

//    @GetMapping("/next")
//    public String getNextNumber(@RequestParam String number) {
//        return service.getNextNumber(number);
//    }

    @GetMapping("/next")
    public String getNextNumber() {
        return service.getNextNumber(getRandomCarNumber());
    }

}