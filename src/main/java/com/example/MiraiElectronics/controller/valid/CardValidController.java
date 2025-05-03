package com.example.MiraiElectronics.controller.valid;

import com.example.MiraiElectronics.service.Parser.CardParserService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardValidController {
    public final CardParserService cardParserService;

    public CardValidController(CardParserService cardParserService) {
        this.cardParserService = cardParserService;
    }

    public boolean checkCVV(@RequestParam String cvv){
        return cardParserService.isValidCVV(cvv);
    }

    public boolean checkNumber(@RequestParam String cardNumber){
        return cardParserService.isValidCardNumber(cardNumber);
    }

    public boolean checkDate(@RequestParam String date){
        return cardParserService.isValidExpiryDate(date);
    }
}
