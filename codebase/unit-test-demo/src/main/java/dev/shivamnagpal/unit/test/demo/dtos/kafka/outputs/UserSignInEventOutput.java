package dev.shivamnagpal.unit.test.demo.dtos.kafka.outputs;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.shivamnagpal.unit.test.demo.dtos.core.JsonSerializable;
import dev.shivamnagpal.unit.test.demo.enums.SignInEventType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserSignInEventOutput implements JsonSerializable {
    private SignInEventType type;

    private Long userId;

    private String email;
}
