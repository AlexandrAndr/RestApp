package ru.meschanov.rest.services;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import ru.meschanov.rest.models.CarNumberModel;
import ru.meschanov.rest.repos.CarNumberRepository;

import java.util.Random;

@Service
//@AllArgsConstructor
public class CarNumberService {
    private CarNumberRepository carNumberRepository;
    private CarNumberModel carNumberModel;
    Random random = new Random();

    public CarNumberService(CarNumberRepository carNumberRepository) {
        this.carNumberRepository = carNumberRepository;
    }

    private static final  String REGION = "116 RUS";
    private char[] alpabet = {'А','В', 'Е', 'К', 'М', 'Н','О','Р','С','Т','У','Х'};

    // Метод генерации числа в диапазоне от 1 до 999
    public int generationNumb(){
        return (int) ((Math.random() * (1 - 1000)) + 1000);
    }

    // Метод приводящий сгенерированное число в строку
    public String generationNumbIntoString() {
        int numb = generationNumb();
        String numbIntoString=Integer.toString(numb);
        if (numb < 10) {
            return "00" + numbIntoString;
        } else if (numb < 100) {
            return "0" + numbIntoString;
        }
        return numbIntoString;
    }

    public String getRandomCarNumber(){

        int initialLetter =random.nextInt(alpabet.length);
        int secondLetter =random.nextInt(alpabet.length);
        int thirdLetter  =random.nextInt(alpabet.length);

        //return alpabet[initialLetter]+ "" + generationNumbIntoString() + "" + alpabet[secondLetter] + "" + alpabet[thirdLetter] +" " + REGION;
        String randomCarNumber = alpabet[initialLetter]+ "" + generationNumbIntoString() + "" + alpabet[secondLetter] + "" + alpabet[thirdLetter] +" " + REGION;
        carNumberRepository.save(carNumberModel);
        return randomCarNumber;
    }

}
