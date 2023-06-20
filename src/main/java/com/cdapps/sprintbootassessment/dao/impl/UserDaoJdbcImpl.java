package com.cdapps.sprintbootassessment.dao.impl;

import com.cdapps.sprintbootassessment.dao.UserDao;
import com.cdapps.sprintbootassessment.models.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoJdbcImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    public UserDaoJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS test_users (" +
                "id SERIAL PRIMARY KEY," +
                "username VARCHAR(50)," +
                "password VARCHAR(255)," +
                "role VARCHAR(50))");
    }

    @Override
    public User findByUsername(String username) {
        String sql = "SELECT * FROM test_users WHERE username = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void save(User user) {
        String sql = "INSERT INTO test_users (username, password, role) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getRole().name());
    }
}
