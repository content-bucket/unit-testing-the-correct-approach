package dev.shivamnagpal.unit.test.demo.models;

import dev.shivamnagpal.unit.test.demo.enums.SignInEventType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table("user_sign_in_event")
public class UserSignInEvent {
    @Id
    private Long id;

    private SignInEventType type;

    private Long userId;

    private String email;
}
