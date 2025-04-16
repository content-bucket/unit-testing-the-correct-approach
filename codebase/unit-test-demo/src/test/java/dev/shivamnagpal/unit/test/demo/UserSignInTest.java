package dev.shivamnagpal.unit.test.demo;

import dev.shivamnagpal.unit.test.demo.constants.Constants;
import dev.shivamnagpal.unit.test.demo.dtos.kafka.outputs.UserSignInEventOutput;
import dev.shivamnagpal.unit.test.demo.dtos.web.inputs.UserSignInRequest;
import dev.shivamnagpal.unit.test.demo.dtos.web.outputs.UserSignInResponse;
import dev.shivamnagpal.unit.test.demo.dtos.web.outputs.wrapper.ErrorResponse;
import dev.shivamnagpal.unit.test.demo.enums.SignInEventType;
import dev.shivamnagpal.unit.test.demo.exceptions.RestException;
import dev.shivamnagpal.unit.test.demo.fakers.KafkaManagerFaker;
import dev.shivamnagpal.unit.test.demo.fakers.UserRepositoryFaker;
import dev.shivamnagpal.unit.test.demo.fakers.UserSignInEventRepositoryFaker;
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

import java.util.List;

class UserSignInTest {
    private final UserSignInDataFactory userSignInDataFactory = new UserSignInDataFactory();

    private final UserRepositoryFaker userRepositoryFaker = new UserRepositoryFaker();

    private final KafkaManagerFaker kafkaManager = new KafkaManagerFaker();

    private final UserTransformer userTransformer = new UserTransformer();

    private final UserSignInEventRepositoryFaker userSignInEventRepository = new UserSignInEventRepositoryFaker();

    private final UserHelperImpl userHelper = new UserHelperImpl(
            userRepositoryFaker,
            userSignInEventRepository,
            kafkaManager,
            userTransformer
    );

    private final PasswordHelperImpl passwordHelper = new PasswordHelperImpl();

    private final TokenHelperImpl tokenHelper = new TokenHelperImpl();

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

        long expectedUserId = 1234L;
        Assertions.assertThat(userSignInResponse)
                .extracting(UserSignInResponse::getId)
                .isNotNull()
                .isEqualTo(expectedUserId);

        List<KafkaManagerFaker.KafkaRecord> sentKafkaRecords = kafkaManager.getSentKafkaRecords();
        KafkaManagerFaker.KafkaRecord sentKafkaRecord = Assertions.assertThat(sentKafkaRecords)
                .isNotNull()
                .hasSize(1)
                .element(0)
                .actual();

        Assertions.assertThat(sentKafkaRecord)
                .isNotNull()
                .extracting(KafkaManagerFaker.KafkaRecord::topic)
                .isEqualTo(Constants.USER_SIGN_IN_EVENT);

        UserSignInEventOutput signInEvent = Assertions.assertThat(sentKafkaRecord)
                .extracting(KafkaManagerFaker.KafkaRecord::message)
                .isNotNull()
                .isExactlyInstanceOf(UserSignInEventOutput.class)
                .asInstanceOf(InstanceOfAssertFactories.type(UserSignInEventOutput.class))
                .actual();

        Assertions.assertThat(signInEvent)
                .extracting(UserSignInEventOutput::getType)
                .isNotNull()
                .isEqualTo(SignInEventType.SIGN_IN_SUCCESSFUL);

        Assertions.assertThat(signInEvent)
                .extracting(UserSignInEventOutput::getEmail)
                .isNotNull()
                .isEqualTo(signInRequest.getEmail());

        Assertions.assertThat(signInEvent)
                .extracting(UserSignInEventOutput::getUserId)
                .isNotNull()
                .isEqualTo(expectedUserId);
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

        List<KafkaManagerFaker.KafkaRecord> sentKafkaRecords = kafkaManager.getSentKafkaRecords();
        KafkaManagerFaker.KafkaRecord sentKafkaRecord = Assertions.assertThat(sentKafkaRecords)
                .isNotNull()
                .hasSize(1)
                .element(0)
                .actual();

        Assertions.assertThat(sentKafkaRecord)
                .isNotNull()
                .extracting(KafkaManagerFaker.KafkaRecord::topic)
                .isEqualTo(Constants.USER_SIGN_IN_EVENT);

        UserSignInEventOutput signInEvent = Assertions.assertThat(sentKafkaRecord)
                .extracting(KafkaManagerFaker.KafkaRecord::message)
                .isNotNull()
                .isExactlyInstanceOf(UserSignInEventOutput.class)
                .asInstanceOf(InstanceOfAssertFactories.type(UserSignInEventOutput.class))
                .actual();

        Assertions.assertThat(signInEvent)
                .extracting(UserSignInEventOutput::getType)
                .isNotNull()
                .isEqualTo(SignInEventType.INVALID_PASSWORD);

        Assertions.assertThat(signInEvent)
                .extracting(UserSignInEventOutput::getEmail)
                .isNotNull()
                .isEqualTo(signInRequest.getEmail());

        Assertions.assertThat(signInEvent)
                .extracting(UserSignInEventOutput::getUserId)
                .isNotNull()
                .isEqualTo(1234L);
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

        List<KafkaManagerFaker.KafkaRecord> sentKafkaRecords = kafkaManager.getSentKafkaRecords();
        KafkaManagerFaker.KafkaRecord sentKafkaRecord = Assertions.assertThat(sentKafkaRecords)
                .isNotNull()
                .hasSize(1)
                .element(0)
                .actual();

        Assertions.assertThat(sentKafkaRecord)
                .isNotNull()
                .extracting(KafkaManagerFaker.KafkaRecord::topic)
                .isEqualTo(Constants.USER_SIGN_IN_EVENT);

        UserSignInEventOutput signInEvent = Assertions.assertThat(sentKafkaRecord)
                .extracting(KafkaManagerFaker.KafkaRecord::message)
                .isNotNull()
                .isExactlyInstanceOf(UserSignInEventOutput.class)
                .asInstanceOf(InstanceOfAssertFactories.type(UserSignInEventOutput.class))
                .actual();

        Assertions.assertThat(signInEvent)
                .extracting(UserSignInEventOutput::getType)
                .isNotNull()
                .isEqualTo(SignInEventType.USER_NOT_FOUND);

        Assertions.assertThat(signInEvent)
                .extracting(UserSignInEventOutput::getEmail)
                .isNotNull()
                .isEqualTo(signInRequest.getEmail());

        Assertions.assertThat(signInEvent)
                .extracting(UserSignInEventOutput::getUserId)
                .isNull();
    }
}
