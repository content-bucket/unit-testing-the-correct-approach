package dev.shivamnagpal.unit.test.demo.repositories;

import dev.shivamnagpal.unit.test.demo.models.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByEmail(String email);
}
