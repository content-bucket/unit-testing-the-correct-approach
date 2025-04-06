package dev.shivamnagpal.unit.test.demo.repositories.impl;

import dev.shivamnagpal.unit.test.demo.models.User;
import dev.shivamnagpal.unit.test.demo.repositories.UserRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

    private static final String SQL_FIND_USER_BY_EMAIL = "SELECT * FROM users WHERE email = ?";

    private final BeanPropertyRowMapper<User> userRowMapper;

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        userRowMapper = BeanPropertyRowMapper.newInstance(User.class);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        List<User> users = jdbcTemplate.query(SQL_FIND_USER_BY_EMAIL, userRowMapper, email);
        if (users.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(users.getFirst());
    }
}
