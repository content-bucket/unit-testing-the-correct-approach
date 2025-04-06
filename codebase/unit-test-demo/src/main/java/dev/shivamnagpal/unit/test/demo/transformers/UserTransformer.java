package dev.shivamnagpal.unit.test.demo.transformers;

import dev.shivamnagpal.unit.test.demo.dtos.kafka.outputs.UserSignInEvent;
import dev.shivamnagpal.unit.test.demo.dtos.web.outputs.UserSignInResponse;
import dev.shivamnagpal.unit.test.demo.enums.SignInEventType;
import dev.shivamnagpal.unit.test.demo.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserTransformer {
    public UserSignInResponse convertToUserSignInResponse(User user, String token) {
        return UserSignInResponse.builder()
                .id(user.getId())
                .token(token)
                .build();
    }

    public UserSignInEvent convertToUserSignInSuccessfulEvent(User user) {
        return UserSignInEvent.builder()
                .type(SignInEventType.SIGN_IN_SUCCESSFUL)
                .userId(user.getId())
                .email(user.getEmail())
                .build();
    }

    public UserSignInEvent convertToUserSignInInvalidPasswordEvent(User user) {
        return UserSignInEvent.builder()
                .type(SignInEventType.INVALID_PASSWORD)
                .userId(user.getId())
                .email(user.getEmail())
                .build();
    }

    public UserSignInEvent convertToUserSignInUserNotFoundEvent(String email) {
        return UserSignInEvent.builder()
                .type(SignInEventType.USER_NOT_FOUND)
                .email(email)
                .build();
    }
}
