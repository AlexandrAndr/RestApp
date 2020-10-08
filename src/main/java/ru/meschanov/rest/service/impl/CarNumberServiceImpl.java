package ru.meschanov.rest.service.impl;

import org.apache.log4j.Logger;
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
    private static final Logger LOGGER = Logger.getLogger(CarNumberServiceImpl.class.getName());

    private static final List<Character> ALPHABET =
            Arrays.asList('А', 'В', 'Е', 'К', 'М', 'Н', 'О', 'Р', 'С', 'Т', 'У', 'Х');

    private CarNumberRepository carNumberRepository;

    public CarNumberServiceImpl(CarNumberRepository carNumberRepository) {
        this.carNumberRepository = carNumberRepository;
    }


    @Override
    public int generationNumb() {
        LOGGER.debug("Генерируем число от 1 до 1000");
        return (int) ((Math.random() * (1 - 1000)) + 1000);
    }

    @Override
    public String generationNumbIntoString(int numb) {
        LOGGER.debug("Переводим сгенерированное число в строку");
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
        LOGGER.debug("Создаем рандомный автомобильный номер");
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

        StringBuilder sbNextCarNumber = new StringBuilder();
        String stringNextCarNumber = null;
        String nextDigitalPart;

        LOGGER.debug("Проверяем автомобильный номер на валидность");

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

                LOGGER.debug("Получаем следующий номер");

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

                LOGGER.debug("Получаем следующий номер");

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

            LOGGER.debug("Получаем следующий номер");

            sbNextCarNumber.append(initialLetterPart);
            sbNextCarNumber.append(nextDigitalPart);
            sbNextCarNumber.append(secondLetterPartArr[0]);
            sbNextCarNumber.append(secondLetterPartArr[1]);
            sbNextCarNumber.append(" 116 RUS");
            stringNextCarNumber = sbNextCarNumber.toString();
            carNumberRepository.save(new CarNumberEntity(stringNextCarNumber));
            return stringNextCarNumber;

        } else if (digitalPart == 999) {

            LOGGER.debug("Получаем следующий номер");

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

                LOGGER.debug("Получаем следующий номер");

                sbNextCarNumber.append(getNextLetter(initialLetterPartArr[0]));
                sbNextCarNumber.append(nextDigitalPart);
                sbNextCarNumber.append(getNextLetter(secondLetterPartArr[0]));
                sbNextCarNumber.append(getNextLetter(secondLetterPartArr[1]));
                sbNextCarNumber.append(" 116 RUS");
                stringNextCarNumber = sbNextCarNumber.toString();
                carNumberRepository.save(new CarNumberEntity(stringNextCarNumber));
                return stringNextCarNumber;

            } else if (secondLetterPartArr[1] == ALPHABET.get(11)) {

                LOGGER.debug("Получаем следующий номер");

                sbNextCarNumber.append(initialLetterPart);
                sbNextCarNumber.append(nextDigitalPart);
                sbNextCarNumber.append(getNextLetter(secondLetterPartArr[0]));
                sbNextCarNumber.append(getNextLetter(secondLetterPartArr[1]));
                sbNextCarNumber.append(" 116 RUS");
                stringNextCarNumber = sbNextCarNumber.toString();
                carNumberRepository.save(new CarNumberEntity(stringNextCarNumber));
                return stringNextCarNumber;

            } else {

                LOGGER.debug("Получаем следующий номер");

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

        LOGGER.debug("Получаем следующий номер");

        return stringNextCarNumber;
    }

    /** Метод получения следующей буквы из константы "ALPHABET"
     *
     * @param letter - буква из константы "ALPHABET"
     * @return - следующая буква из константы "ALPHABET"
     */
    private String getNextLetter(char letter) {

        LOGGER.debug("Получаем следующею букву");

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
