package dev.shivamnagpal.unit.test.demo.helpers.impl;

import dev.shivamnagpal.unit.test.demo.constants.Constants;
import dev.shivamnagpal.unit.test.demo.dtos.kafka.outputs.UserSignInEventOutput;
import dev.shivamnagpal.unit.test.demo.helpers.UserHelper;
import dev.shivamnagpal.unit.test.demo.managers.KafkaManager;
import dev.shivamnagpal.unit.test.demo.models.User;
import dev.shivamnagpal.unit.test.demo.models.UserSignInEvent;
import dev.shivamnagpal.unit.test.demo.repositories.UserRepository;
import dev.shivamnagpal.unit.test.demo.repositories.UserSignInEventRepository;
import dev.shivamnagpal.unit.test.demo.transformers.UserTransformer;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserHelperImpl implements UserHelper {
    private final UserRepository userRepository;

    private final UserSignInEventRepository userSignInEventRepository;

    private final KafkaManager kafkaManager;

    private final UserTransformer userTransformer;

    public UserHelperImpl(
            UserRepository userRepository,
            UserSignInEventRepository userSignInEventRepository,
            KafkaManager kafkaManager,
            UserTransformer userTransformer
    ) {
        this.userRepository = userRepository;
        this.userSignInEventRepository = userSignInEventRepository;
        this.kafkaManager = kafkaManager;
        this.userTransformer = userTransformer;
    }

    @Override
    public Optional<User> fetchUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void sendUserSignInEvent(UserSignInEvent userSignInEvent) {
        userSignInEventRepository.save(userSignInEvent);
        UserSignInEventOutput userSignInEventOutput = userTransformer.convertToUserSignInEventOutput(userSignInEvent);
        kafkaManager.sendSingleMessage(Constants.USER_SIGN_IN_EVENT, userSignInEventOutput);
    }
}
