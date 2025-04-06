package dev.shivamnagpal.unit.test.demo.helpers.impl;

import dev.shivamnagpal.unit.test.demo.constants.Constants;
import dev.shivamnagpal.unit.test.demo.dtos.core.JsonSerializable;
import dev.shivamnagpal.unit.test.demo.dtos.kafka.outputs.UserSignInEvent;
import dev.shivamnagpal.unit.test.demo.helpers.UserHelper;
import dev.shivamnagpal.unit.test.demo.models.User;
import dev.shivamnagpal.unit.test.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserHelperImpl implements UserHelper {
    private final UserRepository userRepository;

    private final KafkaTemplate<String, JsonSerializable> jsonKafkaTemplate;

    public UserHelperImpl(
            UserRepository userRepository,
            @Qualifier(Constants.JSON_SERIALIZABLE_KAFKA_TEMPLATE) KafkaTemplate<String, JsonSerializable> jsonKafkaTemplate
    ) {
        this.userRepository = userRepository;
        this.jsonKafkaTemplate = jsonKafkaTemplate;
    }

    @Override
    public Optional<User> fetchUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void sendUserSignInEvent(UserSignInEvent userSignInEvent) {
        jsonKafkaTemplate.send(Constants.USER_SIGN_IN_EVENT, userSignInEvent);
        jsonKafkaTemplate.flush();
    }
}
