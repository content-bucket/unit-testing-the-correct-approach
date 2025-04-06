package dev.shivamnagpal.unit.test.demo.helpers;

import dev.shivamnagpal.unit.test.demo.models.User;

public interface TokenHelper {
    String createToken(User user);
}
