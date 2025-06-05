package com.example.MiraiElectronics.Parser;

import org.springframework.stereotype.Service;

@Service
public class ParserService {

    public String trimString(String input) {
        if (input == null)
            return null;
        return input.trim();
    }

    public boolean isEmptyString(String input) {
        return input == null || input.trim().isEmpty();
    }

    public boolean isInteger(String input) {
        if (isEmptyString(input))
            return false;
        try {
            Integer.parseInt(input.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isDouble(String input) {
        if (isEmptyString(input))
            return false;
        try {
            Double.parseDouble(input.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public Integer parseInteger(String input) {
        if (!isInteger(input))
            return null;
        return Integer.parseInt(input.trim());
    }

    public Double parseDouble(String input) {
        if (!isDouble(input))
            return null;
        return Double.parseDouble(input.trim());
    }

    public String extractDigitsOnly(String input) {
        if (input == null)
            return null;
        return input.replaceAll("[^0-9]", "");
    }
}
