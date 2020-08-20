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

    /**
     * Метод генерирующий серию номера и объединяющий сгенерированной число с серией номера в формате "А001АА 116 RUS"
     *
     * @return возвращает автомобильный номер
     */
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

    /**
     * Метод последовательной выдачи номера
     * @param number
     * @return возвращает следующий номер
     */
    @Override
    public String getNextNumber(String number) {
        StringBuilder sbNextCarNumber = new StringBuilder();
        String stringNextCarNumber = null;
        String nextDigitalPart;
        Pattern pattern = Pattern.compile("(^[АВЕКМНОРСТУХ])(\\d{3}(?<!000))([АВЕКМНОРСТУХ]{2})\\s116\\sRUS$");
        Matcher matcher = pattern.matcher(number);
        if (!matcher.find()) {
            throw new RuntimeException("Невалидный номер");
        }

        List<String> numberList = new ArrayList<>();

        for (int i = 1; i <= matcher.groupCount(); i++) {
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
                sbNextCarNumber.append(initialLetterPart);
                sbNextCarNumber.append("00");
                sbNextCarNumber.append(nextDigitalPart);
                sbNextCarNumber.append(secondLetterPartArr[0]);
                sbNextCarNumber.append(secondLetterPartArr[1]);
                sbNextCarNumber.append(" 116 RUS");
                stringNextCarNumber = sbNextCarNumber.toString();

                carNumberRepository.save(new CarNumberEntity(stringNextCarNumber));
                return stringNextCarNumber;

            } else if (digitalPart < 100) {
                sbNextCarNumber.append(initialLetterPart);
                sbNextCarNumber.append("0");
                sbNextCarNumber.append(nextDigitalPart);
                sbNextCarNumber.append(secondLetterPartArr[0]);
                sbNextCarNumber.append(secondLetterPartArr[1]);
                sbNextCarNumber.append(" 116 RUS");
                stringNextCarNumber = sbNextCarNumber.toString();
                carNumberRepository.save(new CarNumberEntity(stringNextCarNumber));
                return stringNextCarNumber;
            }

            sbNextCarNumber.append(initialLetterPart);
            sbNextCarNumber.append(nextDigitalPart);
            sbNextCarNumber.append(secondLetterPartArr[0]);
            sbNextCarNumber.append(secondLetterPartArr[1]);
            sbNextCarNumber.append(" 116 RUS");
            stringNextCarNumber = sbNextCarNumber.toString();
            carNumberRepository.save(new CarNumberEntity(stringNextCarNumber));
            return stringNextCarNumber;

        } else if (digitalPart == 999) {
            nextDigitalPart = "001";

            if (initialLetterPartArr[0] == ALPHABET.get(0) &&
                    secondLetterPartArr[1] == ALPHABET.get(11) &&
                    secondLetterPartArr[0] == ALPHABET.get(11))
            {
                sbNextCarNumber.append(getNextLetter(initialLetterPartArr[0]));
                sbNextCarNumber.append(nextDigitalPart);
                sbNextCarNumber.append(getNextLetter(secondLetterPartArr[0]));
                sbNextCarNumber.append(getNextLetter(secondLetterPartArr[1]));
                sbNextCarNumber.append(" 116 RUS");
                stringNextCarNumber = sbNextCarNumber.toString();
                carNumberRepository.save(new CarNumberEntity(stringNextCarNumber));
                return stringNextCarNumber;

            } else if (secondLetterPartArr[1] == ALPHABET.get(11) && secondLetterPartArr[0] == ALPHABET.get(11)) {
                sbNextCarNumber.append(getNextLetter(initialLetterPartArr[0]));
                sbNextCarNumber.append(nextDigitalPart);
                sbNextCarNumber.append(getNextLetter(secondLetterPartArr[0]));
                sbNextCarNumber.append(getNextLetter(secondLetterPartArr[1]));
                sbNextCarNumber.append(" 116 RUS");
                stringNextCarNumber = sbNextCarNumber.toString();
                carNumberRepository.save(new CarNumberEntity(stringNextCarNumber));
                return stringNextCarNumber;

            } else if (secondLetterPartArr[1] == ALPHABET.get(11)) {
                sbNextCarNumber.append(initialLetterPart);
                sbNextCarNumber.append(nextDigitalPart);
                sbNextCarNumber.append(getNextLetter(secondLetterPartArr[0]));
                sbNextCarNumber.append(getNextLetter(secondLetterPartArr[1]));
                sbNextCarNumber.append(" 116 RUS");
                stringNextCarNumber = sbNextCarNumber.toString();
                carNumberRepository.save(new CarNumberEntity(stringNextCarNumber));
                return stringNextCarNumber;

            } else {
                sbNextCarNumber.append(initialLetterPart);
                sbNextCarNumber.append(nextDigitalPart);
                sbNextCarNumber.append(secondLetterPartArr[0]);
                sbNextCarNumber.append(getNextLetter(secondLetterPartArr[1]));
                sbNextCarNumber.append(" 116 RUS");
                stringNextCarNumber = sbNextCarNumber.toString();
                carNumberRepository.save(new CarNumberEntity(stringNextCarNumber));
                return stringNextCarNumber;
            }
        }
        carNumberRepository.save(new CarNumberEntity(stringNextCarNumber));
        return stringNextCarNumber;
    }

    /**
     * Метод выдающий следующею букву алфавита
     * @param letter
     * @return возвращает следующею букву
     */
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
