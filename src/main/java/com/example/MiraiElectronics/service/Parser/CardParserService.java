package com.example.MiraiElectronics.service.Parser;

import org.springframework.stereotype.Service;

@Service
public class CardParserService extends ParserService {

    public boolean isValidCardNumber(String cardNumber) {
        if (isEmptyString(cardNumber)) return false;
        String digits = extractDigitsOnly(cardNumber);
        return digits.length() == 16;
    }

    public boolean isValidExpiryDate(String expiry) {
        return expiry != null && expiry.matches("(0[1-9]|1[0-2])/\\d{2}");
    }

    public boolean isValidCVV(String cvv) {
        return cvv != null && cvv.matches("\\d{3}");
    }

    public String maskCardNumber(String cardNumber) {
        if (!isValidCardNumber(cardNumber)) return null;
        String digits = extractDigitsOnly(cardNumber);
        return "**** **** **** " + digits.substring(digits.length() - 4);
    }
}
