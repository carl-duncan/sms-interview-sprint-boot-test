package com.cdapps.sprintbootassessment.dao.impl;

import com.cdapps.sprintbootassessment.dao.UserDao;
import com.cdapps.sprintbootassessment.models.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoJdbcImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserDaoJdbcImpl(JdbcTemplate jdbcTemplate, BCryptPasswordEncoder passwordEncoder) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void save(User user) {
        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getRole().name());
    }
}
