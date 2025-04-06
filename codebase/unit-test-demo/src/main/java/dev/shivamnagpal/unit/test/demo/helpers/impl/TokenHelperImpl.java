package dev.shivamnagpal.unit.test.demo.helpers.impl;

import dev.shivamnagpal.unit.test.demo.helpers.TokenHelper;
import dev.shivamnagpal.unit.test.demo.models.User;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TokenHelperImpl implements TokenHelper {

    @Override
    public String createToken(User user) {
        // Dummy Logic to generate token
        return user.getId() + "-" + UUID.randomUUID();
    }
}
