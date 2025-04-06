package dev.shivamnagpal.unit.test.demo.services;

import dev.shivamnagpal.unit.test.demo.dtos.web.inputs.UserSignInRequest;
import dev.shivamnagpal.unit.test.demo.dtos.web.outputs.UserSignInResponse;

public interface UserService {
    UserSignInResponse signIn(UserSignInRequest request);
}
