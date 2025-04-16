package dev.shivamnagpal.unit.test.demo.repositories;

import dev.shivamnagpal.unit.test.demo.models.UserSignInEvent;

public interface UserSignInEventRepository {
    void save(UserSignInEvent userSignInEvent);
}
