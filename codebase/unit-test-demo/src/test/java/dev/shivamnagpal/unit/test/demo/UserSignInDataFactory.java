package dev.shivamnagpal.unit.test.demo;

import dev.shivamnagpal.unit.test.demo.dtos.web.inputs.UserSignInRequest;

public class UserSignInDataFactory {
    public UserSignInRequest createUserSignInRequestWithValidCredentials() {
        return UserSignInRequest.builder()
                .email("admin@shivamnagpal.dev")
                .password("Test@123")
                .build();
    }

    public UserSignInRequest createUserSignInRequestWithInvalidCredentials() {
        return UserSignInRequest.builder()
                .email("admin@shivamnagpal.dev")
                .password("1234567")
                .build();
    }

    public UserSignInRequest createUserSignInRequestWithNonExistentUser() {
        return UserSignInRequest.builder()
                .email("spam@shivamnagpal.dev")
                .password("1234567")
                .build();
    }
}
