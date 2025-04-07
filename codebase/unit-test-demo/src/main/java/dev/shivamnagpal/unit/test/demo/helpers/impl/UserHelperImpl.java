package dev.shivamnagpal.unit.test.demo.helpers.impl;

import dev.shivamnagpal.unit.test.demo.constants.Constants;
import dev.shivamnagpal.unit.test.demo.dtos.kafka.outputs.UserSignInEvent;
import dev.shivamnagpal.unit.test.demo.helpers.UserHelper;
import dev.shivamnagpal.unit.test.demo.managers.KafkaManager;
import dev.shivamnagpal.unit.test.demo.models.User;
import dev.shivamnagpal.unit.test.demo.repositories.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserHelperImpl implements UserHelper {
    private final UserRepository userRepository;

    private final KafkaManager kafkaManager;

    public UserHelperImpl(
            UserRepository userRepository,
            KafkaManager kafkaManager
    ) {
        this.userRepository = userRepository;
        this.kafkaManager = kafkaManager;
    }

    @Override
    public Optional<User> fetchUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void sendUserSignInEvent(UserSignInEvent userSignInEvent) {
        kafkaManager.sendSingleMessage(Constants.USER_SIGN_IN_EVENT, userSignInEvent);
    }
}
