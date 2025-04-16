package dev.shivamnagpal.unit.test.demo.repositories.impl;

import dev.shivamnagpal.unit.test.demo.models.UserSignInEvent;
import dev.shivamnagpal.unit.test.demo.repositories.UserSignInEventRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserSignInEventRepositoryImpl implements UserSignInEventRepository {
    private final JdbcTemplate jdbcTemplate;

    private static final String SQL_INSERT_USER_SIGN_IN_EVENT = "insert into user_sign_in_event (\"type\", user_id, email) values (?, ?, ?)";

    public UserSignInEventRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(UserSignInEvent userSignInEvent) {
        jdbcTemplate.update(
                SQL_INSERT_USER_SIGN_IN_EVENT,
                userSignInEvent.getType().name(),
                userSignInEvent.getUserId(),
                userSignInEvent.getEmail()
        );
    }
}
