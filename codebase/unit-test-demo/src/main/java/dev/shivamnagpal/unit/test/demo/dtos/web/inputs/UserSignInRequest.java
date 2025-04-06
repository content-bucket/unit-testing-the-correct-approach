package dev.shivamnagpal.unit.test.demo.dtos.web.inputs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class UserSignInRequest {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
}
