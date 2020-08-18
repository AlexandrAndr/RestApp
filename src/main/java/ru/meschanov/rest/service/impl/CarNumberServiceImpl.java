package ru.meschanov.rest.service.impl;

import org.springframework.stereotype.Service;
import ru.meschanov.rest.domains.CarNumberEntity;
import ru.meschanov.rest.repository.CarNumberRepository;
import ru.meschanov.rest.service.api.CarNumberService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        StringBuilder SBNextCarNumber = new StringBuilder();
        String stringNextCarNumber = null;
        String nextDigitalPart;
        StringBuilder nextSecondLetterPartLetter = new StringBuilder();
        Pattern pattern = Pattern.compile("(^[АЕТОРНУКХСВМ])(\\d{3}(?<!000))([АЕТОРНУКХСВМ]{2})\\s116\\sRUS$");
        Matcher matcher = pattern.matcher(number);
        if (!matcher.find()) {
            throw new RuntimeException("Невалидный номер");
        }

        List<String> numberList = new ArrayList<>();

        for (int i = 0; i < matcher.groupCount(); i++) {
            numberList.add(matcher.group(i));
        }

        String initialLetterPart = numberList.get(0);
        int digitalPart = Integer.parseInt(numberList.get(1));
        String secondLetterPart = numberList.get(2);
        char[] secondLetterPartArr = secondLetterPart.toCharArray();
        char[] initialLetterPartArr = initialLetterPart.toCharArray();

        if (digitalPart >= 1 && digitalPart < 999) {
            digitalPart++;
            nextDigitalPart = Integer.toString(digitalPart);

            if (digitalPart < 10) {
                SBNextCarNumber.append(initialLetterPart);
                SBNextCarNumber.append("00");
                SBNextCarNumber.append(nextDigitalPart);
                SBNextCarNumber.append(secondLetterPartArr[0]);
                SBNextCarNumber.append(secondLetterPartArr[1]);
                SBNextCarNumber.append(" 116 RUS");
                stringNextCarNumber = SBNextCarNumber.toString();
                return stringNextCarNumber;
                // return  initialLetterPart + "00" + nextDigitalPart + secondLetterPartArr[0] + secondLetterPartArr[1];
            } else if (digitalPart < 100) {
                SBNextCarNumber.append(initialLetterPart);
                SBNextCarNumber.append("0");
                SBNextCarNumber.append(nextDigitalPart);
                SBNextCarNumber.append(secondLetterPartArr[0]);
                SBNextCarNumber.append(secondLetterPartArr[1]);
                SBNextCarNumber.append(" 116 RUS");
                stringNextCarNumber = SBNextCarNumber.toString();
                return stringNextCarNumber;
                //  return initialLetterPart + "0" + nextDigitalPart + secondLetterPartArr[0] + secondLetterPartArr[1];
            }
            SBNextCarNumber.append(initialLetterPart);
            SBNextCarNumber.append(nextDigitalPart);
            SBNextCarNumber.append(secondLetterPartArr[0]);
            SBNextCarNumber.append(secondLetterPartArr[1]);
            SBNextCarNumber.append(" 116 RUS");
            stringNextCarNumber = SBNextCarNumber.toString();
            return stringNextCarNumber;
            //return initialLetterPart + nextDigitalPart + secondLetterPartArr[0] + secondLetterPartArr[1];
        } else if (digitalPart == 999) {
            nextDigitalPart = "001";

            if (secondLetterPartArr[1] == ALPHABET.get(11)) {
                SBNextCarNumber.append(initialLetterPart);
                SBNextCarNumber.append(nextDigitalPart);
                SBNextCarNumber.append(getNextLetter(secondLetterPartArr[0]));
                SBNextCarNumber.append(getNextLetter(secondLetterPartArr[1]));
                SBNextCarNumber.append(" 116 RUS");
                stringNextCarNumber = SBNextCarNumber.toString();
                return stringNextCarNumber;
                // return initialLetterPart + nextDigitalPart + getNextLetter(secondLetterPartArr[0]) + getNextLetter(secondLetterPartArr[1]);

            } else if (secondLetterPartArr[1] == ALPHABET.get(11) && secondLetterPartArr[0] == ALPHABET.get(11)) {
                SBNextCarNumber.append(getNextLetter(initialLetterPartArr[0]));
                SBNextCarNumber.append(nextDigitalPart);
                SBNextCarNumber.append(getNextLetter(secondLetterPartArr[0]));
                SBNextCarNumber.append(getNextLetter(secondLetterPartArr[1]));
                SBNextCarNumber.append(" 116 RUS");
                stringNextCarNumber = SBNextCarNumber.toString();
                return stringNextCarNumber;
                // return getNextLetter(initialLetterPartArr[0]) + nextDigitalPart + getNextLetter(secondLetterPartArr[0]) + getNextLetter(secondLetterPartArr[1]);

            } else {
                SBNextCarNumber.append(initialLetterPart);
                SBNextCarNumber.append(nextDigitalPart);
                SBNextCarNumber.append(secondLetterPartArr[0]);
                SBNextCarNumber.append(getNextLetter(secondLetterPartArr[1]));
                SBNextCarNumber.append(" 116 RUS");
                stringNextCarNumber = SBNextCarNumber.toString();
                return stringNextCarNumber;
                // return initialLetterPart + nextDigitalPart + secondLetterPartArr[0] + getNextLetter(secondLetterPartArr[1]);
            }
        }

        carNumberRepository.save(new CarNumberEntity(stringNextCarNumber));
        return getNextNumber(number);
    }

    String getNextLetter(char letter) {
        String result = null;
        int lastLetterNumber = ALPHABET.size() - 1;
        int letterIndex = ALPHABET.indexOf(letter);
        if (letterIndex == lastLetterNumber) {
            result = String.valueOf(ALPHABET.get(0));
        } else {
            result = String.valueOf(ALPHABET.get(letterIndex + 1));
        }
        return result;
    }


}
