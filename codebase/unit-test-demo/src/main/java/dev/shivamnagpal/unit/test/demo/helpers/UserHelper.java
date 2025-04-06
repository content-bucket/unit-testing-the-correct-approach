package dev.shivamnagpal.unit.test.demo.helpers;

import dev.shivamnagpal.unit.test.demo.dtos.kafka.outputs.UserSignInEvent;
import dev.shivamnagpal.unit.test.demo.models.User;

import java.util.Optional;

public interface UserHelper {
    Optional<User> fetchUserByEmail(String email);

    void sendUserSignInEvent(UserSignInEvent userSignInEvent);
}
