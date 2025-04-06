package dev.shivamnagpal.unit.test.demo.controllers;

import dev.shivamnagpal.unit.test.demo.dtos.web.inputs.UserSignInRequest;
import dev.shivamnagpal.unit.test.demo.dtos.web.outputs.UserSignInResponse;
import dev.shivamnagpal.unit.test.demo.dtos.web.outputs.wrapper.ResponseWrapper;
import dev.shivamnagpal.unit.test.demo.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/sign-in")
    public ResponseEntity<ResponseWrapper<UserSignInResponse>> signIn(@RequestBody @Valid UserSignInRequest request) {
        UserSignInResponse response = userService.signIn(request);
        return ResponseEntity.ok(ResponseWrapper.success(response));
    }
}
