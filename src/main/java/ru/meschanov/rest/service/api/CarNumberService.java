package ru.meschanov.rest.service.api;

public interface CarNumberService {

    /**
     * Метод генерации числа
     *
     * @return возвращает число в диапазоне от 1 до 999
     */
    int generationNumb();

    /**
     * Метод переводящий сгенерированное число в строку
     *
     * @return строковое представление числа
     */
    String generationNumbIntoString(int numb);

    /**
     * Метод генерирующий серию номера и объединяющий сгенерированной число с серией номера в формате "А001АА 116 RUS"
     *
     * @return возвращает автомобильный номер
     */
    String getRandomCarNumber();

    /**
     * Метод последовательной выдачи номера
     * @param number
     * @return возвращает следующий номер
     */
    String getNextNumber(String number);

}
