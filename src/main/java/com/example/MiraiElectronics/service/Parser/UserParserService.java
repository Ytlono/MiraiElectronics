package com.example.MiraiElectronics.service.Parser;

import com.example.MiraiElectronics.dto.AddressDTO;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.springframework.stereotype.Service;
import java.util.regex.Pattern;

@Service
public class UserParserService extends ParserService {

    private static final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

    private static final String ADDRESS_REGEX = "^[\\w\\s,.-]{5,100}$";

    private static final String USERNAME_REGEX = "^[a-z0-9_]+$";

    public boolean isValidPhoneNumber(String phoneNumber) {
        if (isEmptyString(phoneNumber)) {
            return false;
        }
        try {
            Phonenumber.PhoneNumber number = phoneNumberUtil.parse(phoneNumber.trim(), "");
            return phoneNumberUtil.isValidNumber(number);
        } catch (Exception e) {
            return false;
        }
    }

    public String parsePhoneNumber(String phoneNumber) {
        if (!isValidPhoneNumber(phoneNumber)) {
            return null;
        }
        try {
            Phonenumber.PhoneNumber number = phoneNumberUtil.parse(phoneNumber.trim(), "");
            return phoneNumberUtil.format(number, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isValidAddress(AddressDTO addressDTO) {
        if (addressDTO == null) {
            return false;
        }
        String address = String.join(", ",
                addressDTO.getStreet(),
                addressDTO.getCity(),
                addressDTO.getState(),
                addressDTO.getPostalCode(),
                addressDTO.getCountry()
        ).trim();
        if (isEmptyString(address)) {
            return false;
        }
        return Pattern.matches(ADDRESS_REGEX, address);
    }

    public boolean isValidUsername(String username) {
        if (isEmptyString(username)) {
            return false;
        }
        return Pattern.matches(USERNAME_REGEX, username.trim().toLowerCase());
    }
}
