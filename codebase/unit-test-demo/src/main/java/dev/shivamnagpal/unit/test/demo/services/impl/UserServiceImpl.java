package dev.shivamnagpal.unit.test.demo.services.impl;

import dev.shivamnagpal.unit.test.demo.dtos.kafka.outputs.UserSignInEvent;
import dev.shivamnagpal.unit.test.demo.dtos.web.inputs.UserSignInRequest;
import dev.shivamnagpal.unit.test.demo.dtos.web.outputs.UserSignInResponse;
import dev.shivamnagpal.unit.test.demo.dtos.web.outputs.wrapper.ErrorResponse;
import dev.shivamnagpal.unit.test.demo.enums.ErrorCode;
import dev.shivamnagpal.unit.test.demo.exceptions.RestException;
import dev.shivamnagpal.unit.test.demo.helpers.PasswordHelper;
import dev.shivamnagpal.unit.test.demo.helpers.TokenHelper;
import dev.shivamnagpal.unit.test.demo.helpers.UserHelper;
import dev.shivamnagpal.unit.test.demo.models.User;
import dev.shivamnagpal.unit.test.demo.services.UserService;
import dev.shivamnagpal.unit.test.demo.transformers.UserTransformer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserHelper userHelper;

    private final PasswordHelper passwordHelper;

    private final TokenHelper tokenHelper;

    private final UserTransformer userTransformer;

    public UserServiceImpl(
            UserHelper userHelper,
            PasswordHelper passwordHelper,
            TokenHelper tokenHelper,
            UserTransformer userTransformer
    ) {
        this.userHelper = userHelper;
        this.passwordHelper = passwordHelper;
        this.tokenHelper = tokenHelper;
        this.userTransformer = userTransformer;
    }

    @Override
    public UserSignInResponse signIn(UserSignInRequest request) {
        Optional<User> userOptional = userHelper.fetchUserByEmail(request.getEmail());
        if (userOptional.isEmpty()) {
            // User not found
            UserSignInEvent userSignInEvent = userTransformer.convertToUserSignInUserNotFoundEvent(request.getEmail());
            userHelper.sendUserSignInEvent(userSignInEvent);
            throw new RestException(HttpStatus.BAD_REQUEST, ErrorResponse.from(ErrorCode.INVALID_CREDENTIALS));
        }

        User user = userOptional.get();
        if (!passwordHelper.verifyPassword(request.getPassword(), user.getPassword())) {
            // Invalid Password
            UserSignInEvent userSignInEvent = userTransformer.convertToUserSignInInvalidPasswordEvent(user);
            userHelper.sendUserSignInEvent(userSignInEvent);
            throw new RestException(HttpStatus.BAD_REQUEST, ErrorResponse.from(ErrorCode.INVALID_CREDENTIALS));
        }

        String token = tokenHelper.createToken(user);
        UserSignInEvent userSignInEvent = userTransformer.convertToUserSignInSuccessfulEvent(user);
        userHelper.sendUserSignInEvent(userSignInEvent);
        return userTransformer.convertToUserSignInResponse(user, token);
    }
}
