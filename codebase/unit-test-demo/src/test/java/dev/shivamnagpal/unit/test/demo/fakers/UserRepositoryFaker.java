package dev.shivamnagpal.unit.test.demo.fakers;

import dev.shivamnagpal.unit.test.demo.models.User;
import dev.shivamnagpal.unit.test.demo.repositories.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UserRepositoryFaker implements UserRepository {
    private static final List<User> users = List.of(
            User.builder()
                    .id(1234L)
                    .firstName("John")
                    .lastName("Doe")
                    .email("admin@shivamnagpal.dev")
                    .password("Test@123")
                    .build()
    );

    private static final Map<String, User> userMap = users.stream()
            .collect(Collectors.toMap(User::getEmail, Function.identity()));

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userMap.get(email));
    }
}
