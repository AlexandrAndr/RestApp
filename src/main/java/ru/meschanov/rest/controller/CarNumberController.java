package ru.meschanov.rest.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.meschanov.rest.services.CarNumberService;

import java.util.Random;

@RestController
@RequestMapping("/number")
//@AllArgsConstructor
public class CarNumberController {

    @Autowired
   private CarNumberService carNumberService;

    public CarNumberController() {
    }
    public CarNumberController(CarNumberService carNumberService) {
        this.carNumberService = carNumberService;
    }


    @RequestMapping("/random")
    public String showCarNumber(){
        return carNumberService.getRandomCarNumber();
    }
}



//    Random random = new Random();
//    private static final  String REGION = "116 RUS";
//
//    private char[] alpabet = {'А','В', 'Е', 'К', 'М', 'Н','О','Р','С','Т','У','Х'};
//
//    // Метод генерации числа в диапазоне от 1 до 999
//    public int generationNumb(){
//        return (int) ((Math.random() * (1 - 1000)) + 1000);
//    }
//
//    // Метод приводящий сгенерированное число в строку
//    public String generationNumbIntoString() {
//        int numb = generationNumb();
//        String numbIntoString=Integer.toString(numb);
//        if (numb < 10) {
//            return "00" + numbIntoString;
//        } else if (numb < 100) {
//            return "0" + numbIntoString;
//        }
//        return numbIntoString;
//    }

//  @GetMapping("random")
//    public String randomCarNumber(){
//        int initialLetter =random.nextInt(alpabet.length);
//        int secondLetter =random.nextInt(alpabet.length);
//        int thirdLetter  =random.nextInt(alpabet.length);
//        return alpabet[initialLetter]+ "" + generationNumbIntoString() + "" + alpabet[secondLetter] + "" + alpabet[thirdLetter] +" " + REGION;
//    }