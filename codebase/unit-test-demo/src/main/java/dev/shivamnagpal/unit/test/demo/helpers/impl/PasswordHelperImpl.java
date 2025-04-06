package dev.shivamnagpal.unit.test.demo.helpers.impl;

import dev.shivamnagpal.unit.test.demo.helpers.PasswordHelper;
import org.springframework.stereotype.Component;

@Component
public class PasswordHelperImpl implements PasswordHelper {
    @Override
    public boolean verifyPassword(String inputPassword, String actualPassword) {
        return inputPassword.equals(actualPassword);
    }
}
