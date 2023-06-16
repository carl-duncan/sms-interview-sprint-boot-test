package com.cdapps.sprintbootassessment.dao;

import com.cdapps.sprintbootassessment.models.User;

public interface UserDao {

    User findByUsername(String username);

    void save(User user);
}
