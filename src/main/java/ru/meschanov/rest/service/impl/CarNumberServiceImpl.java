package ru.meschanov.rest.service.impl;

import org.springframework.stereotype.Service;
import ru.meschanov.rest.domains.CarNumberEntity;
import ru.meschanov.rest.repository.CarNumberRepository;
import ru.meschanov.rest.service.api.CarNumberService;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class CarNumberServiceImpl implements CarNumberService {

    private static final String NUMBER_FORMAT = "%s%s%s%s 116 RUS";

    private static final List<Character> ALPHABET =
            Arrays.asList('А', 'В', 'Е', 'К', 'М', 'Н', 'О', 'Р', 'С', 'Т', 'У', 'Х');

    private CarNumberRepository carNumberRepository;

    public CarNumberServiceImpl(CarNumberRepository carNumberRepository) {
        this.carNumberRepository = carNumberRepository;
    }

    /**
     * Метод генерации числа
     *
     * @return возвращает число в диапазоне от 1 до 999
     */
    private int generationNumb() {
        return (int) ((Math.random() * (1 - 1000)) + 1000);
    }

    /**
     * Метод переводящий сгенерированное число в строку
     *
     * @return строковое представление числа
     */
    private String generationNumbIntoString(int numb) {
        String numbIntoString = Integer.toString(numb);
        if (numb < 10) {
            return "00" + numbIntoString;
        } else if (numb < 100) {
            return "0" + numbIntoString;
        }
        return numbIntoString;
    }

    @Override
    public String getRandomCarNumber() {
        Random random = new Random();
        int initialLetter = random.nextInt(ALPHABET.size());
        int secondLetter = random.nextInt(ALPHABET.size());
        int thirdLetter = random.nextInt(ALPHABET.size());

        String randomCarNumber = String.format(
                NUMBER_FORMAT,
                ALPHABET.get(initialLetter),
                generationNumbIntoString(generationNumb()),
                ALPHABET.get(secondLetter),
                ALPHABET.get(thirdLetter)
        );
        carNumberRepository.save(new CarNumberEntity(randomCarNumber));
        return randomCarNumber;
    }

    @Override
    public String getNextNumber(String number) {
        //TODO Валидация номера
        char[] arr = number.toCharArray();
        final int firstSectionNumber = 0;
        final int startLastSectionNumber = 5;
        final int startNumberSectionNumber = 3;

        StringBuilder numberBuilder = new StringBuilder();
        String firstSection = null;
        StringBuilder lastSectionBuilder = new StringBuilder();
        String numberSection;
        String lastSection;
        for(int i = 0; i < arr.length; i++) {
            if (i == firstSectionNumber) {
                firstSection = String.valueOf(arr[i]);
            } else if (i <= startNumberSectionNumber){
                numberBuilder.append(arr[i]);
            } else if (i <= startLastSectionNumber) {
                lastSectionBuilder.append(arr[i]);
            }
        }
        numberSection = numberBuilder.toString();
        lastSection = lastSectionBuilder.toString();
        int numberValue = Integer.parseInt(numberSection);
        String nextNumberStringValue;
        if (numberValue < 999) {
            nextNumberStringValue = generationNumbIntoString(numberValue + 1);
        } else {
            nextNumberStringValue = "001";

        }



        return "FIRST_SECTION: " + firstSection + " NUMBER_SECTION: " + numberBuilder.toString() + " LAST_SECTION: " + lastSectionBuilder;
    }

    String getNextLetter(char letter) {
        String result = null;
        int alphabetSize = ALPHABET.size() - 1;
        int letterIndex = ALPHABET.indexOf(letter);
        if (letterIndex == alphabetSize) {
            result = String.valueOf(ALPHABET.get(0));
        } else {
            result = String.valueOf(ALPHABET.get(letterIndex + 1));
        }
        return result;
    }

}
