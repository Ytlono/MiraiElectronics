package com.example.MiraiElectronics.service;

import org.springframework.stereotype.Service;

/**
 * Сервис для парсинга и валидации входных данных
 */
@Service
public class ParserService {

    /**
     * Удаляет пробелы по краям строки
     * @param input строка для обработки
     * @return обработанная строка
     */
    public String trimString(String input) {
        if (input == null) {
            return null;
        }
        return input.trim();
    }
    
    /**
     * Проверяет, пуста ли строка после удаления пробелов
     * @param input строка для проверки
     * @return true, если строка пуста или null
     */
    public boolean isEmptyString(String input) {
        return input == null || input.trim().isEmpty();
    }
    
    /**
     * Проверяет, является ли строка целым числом
     * @param input строка для проверки
     * @return true, если строка является целым числом
     */
    public boolean isInteger(String input) {
        if (isEmptyString(input)) {
            return false;
        }
        try {
            Integer.parseInt(input.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Проверяет, является ли строка числом с плавающей точкой
     * @param input строка для проверки
     * @return true, если строка является числом с плавающей точкой
     */
    public boolean isDouble(String input) {
        if (isEmptyString(input)) {
            return false;
        }
        try {
            Double.parseDouble(input.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Преобразует строку в целое число
     * @param input строка для преобразования
     * @return целое число или null, если преобразование невозможно
     */
    public Integer parseInteger(String input) {
        if (!isInteger(input)) {
            return null;
        }
        return Integer.parseInt(input.trim());
    }
    
    /**
     * Преобразует строку в число с плавающей точкой
     * @param input строка для преобразования
     * @return число с плавающей точкой или null, если преобразование невозможно
     */
    public Double parseDouble(String input) {
        if (!isDouble(input)) {
            return null;
        }
        return Double.parseDouble(input.trim());
    }
    
    /**
     * Удаляет все не-цифровые символы из строки
     * @param input строка для обработки
     * @return строка, содержащая только цифры
     */
    public String extractDigitsOnly(String input) {
        if (input == null) {
            return null;
        }
        return input.replaceAll("[^0-9]", "");
    }
}
