package dev.shivamnagpal.unit.test.demo;

import dev.shivamnagpal.unit.test.demo.dtos.web.inputs.UserSignInRequest;
import dev.shivamnagpal.unit.test.demo.dtos.web.outputs.UserSignInResponse;
import dev.shivamnagpal.unit.test.demo.dtos.web.outputs.wrapper.ErrorResponse;
import dev.shivamnagpal.unit.test.demo.exceptions.RestException;
import dev.shivamnagpal.unit.test.demo.fakers.KafkaManagerFaker;
import dev.shivamnagpal.unit.test.demo.fakers.UserRepositoryFaker;
import dev.shivamnagpal.unit.test.demo.helpers.impl.PasswordHelperImpl;
import dev.shivamnagpal.unit.test.demo.helpers.impl.TokenHelperImpl;
import dev.shivamnagpal.unit.test.demo.helpers.impl.UserHelperImpl;
import dev.shivamnagpal.unit.test.demo.services.UserService;
import dev.shivamnagpal.unit.test.demo.services.impl.UserServiceImpl;
import dev.shivamnagpal.unit.test.demo.transformers.UserTransformer;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class UserSignInTest {
    private final UserSignInDataFactory userSignInDataFactory = new UserSignInDataFactory();

    private final UserRepositoryFaker userRepositoryFaker = new UserRepositoryFaker();

    private final KafkaManagerFaker kafkaManager = new KafkaManagerFaker();

    private final UserHelperImpl userHelper = new UserHelperImpl(userRepositoryFaker, kafkaManager);

    private final PasswordHelperImpl passwordHelper = new PasswordHelperImpl();

    private final TokenHelperImpl tokenHelper = new TokenHelperImpl();

    private final UserTransformer userTransformer = new UserTransformer();

    private final UserService userService = new UserServiceImpl(
            userHelper, passwordHelper, tokenHelper, userTransformer
    );

    @Test
    void givenUserExists_whenSignInWithCorrectCredentials_thenSignInSucceeds() {
        UserSignInRequest signInRequest = userSignInDataFactory.createUserSignInRequestWithValidCredentials();
        UserSignInResponse userSignInResponse = userService.signIn(signInRequest);
        Assertions.assertThat(userSignInResponse)
                .isNotNull();
        Assertions.assertThat(userSignInResponse)
                .extracting(UserSignInResponse::getToken)
                .isNotNull()
                .matches(s -> s.startsWith("1234"));

        Assertions.assertThat(userSignInResponse)
                .extracting(UserSignInResponse::getId)
                .isNotNull()
                .isEqualTo(1234L);
    }

    @Test
    void givenUserExists_whenSignInWithInvalidCredentials_thenSignInFails() {
        UserSignInRequest signInRequest = userSignInDataFactory.createUserSignInRequestWithInvalidCredentials();
        RestException restException = Assertions.assertThatThrownBy(() -> userService.signIn(signInRequest))
                .isExactlyInstanceOf(RestException.class)
                .asInstanceOf(InstanceOfAssertFactories.type(RestException.class))
                .actual();

        Assertions.assertThat(restException)
                .extracting(RestException::getHttpStatus)
                .isNotNull()
                .isEqualTo(HttpStatus.BAD_REQUEST);

        Assertions.assertThat(restException)
                .extracting(RestException::getErrorResponses)
                .isNotNull()
                .asInstanceOf(InstanceOfAssertFactories.list(ErrorResponse.class))
                .hasSize(1)
                .element(0)
                .satisfies(
                        errorResponse -> Assertions.assertThat(errorResponse)
                                .extracting(ErrorResponse::errorCode)
                                .isEqualTo("UTD-E-006"),
                        errorResponse -> Assertions.assertThat(errorResponse)
                                .extracting(ErrorResponse::message)
                                .isEqualTo("Invalid Credentials")
                );

    }

    @Test
    void givenUserDoesNotExists_whenSignInAttempted_thenSignInFails() {
        UserSignInRequest signInRequest = userSignInDataFactory.createUserSignInRequestWithNonExistentUser();
        RestException restException = Assertions.assertThatThrownBy(() -> userService.signIn(signInRequest))
                .isExactlyInstanceOf(RestException.class)
                .asInstanceOf(InstanceOfAssertFactories.type(RestException.class))
                .actual();

        Assertions.assertThat(restException)
                .extracting(RestException::getHttpStatus)
                .isNotNull()
                .isEqualTo(HttpStatus.BAD_REQUEST);

        Assertions.assertThat(restException)
                .extracting(RestException::getErrorResponses)
                .isNotNull()
                .asInstanceOf(InstanceOfAssertFactories.list(ErrorResponse.class))
                .hasSize(1)
                .element(0)
                .satisfies(
                        errorResponse -> Assertions.assertThat(errorResponse)
                                .extracting(ErrorResponse::errorCode)
                                .isEqualTo("UTD-E-006"),
                        errorResponse -> Assertions.assertThat(errorResponse)
                                .extracting(ErrorResponse::message)
                                .isEqualTo("Invalid Credentials")
                );

    }
}
