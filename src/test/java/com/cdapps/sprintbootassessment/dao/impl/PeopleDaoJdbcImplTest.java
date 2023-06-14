package com.cdapps.sprintbootassessment.dao.impl;

import com.cdapps.sprintbootassessment.models.People;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PeopleDaoJdbcImplTest {

    @Mock
    private static JdbcTemplate jdbcTemplate;
    private PeopleDaoJdbcImpl peopleDao;
    private static People people;

    @BeforeAll
    static void beforeAll() {
        people = new People();
        people.setId(1);
        people.setName("John Doe");
        people.setBirth(1980);
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        peopleDao = new PeopleDaoJdbcImpl(jdbcTemplate);
    }

    @Test
    void getPersonById() {
        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), anyInt())).thenReturn(people);

        People result = peopleDao.getPersonById(1);

        assertEquals(people, result);
        verify(jdbcTemplate).queryForObject(anyString(), any(RowMapper.class), eq(1));
    }

    @Test
    void getAllPeople() {
        final int pageSize = 10;
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), anyInt(), anyInt())).thenReturn(List.of(people));
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class))).thenReturn(1);
        final Pageable pageable = Pageable.ofSize(pageSize);

        var result = peopleDao.getAllPeople(pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
    }

    @Test
    void savePerson() {
        peopleDao.savePerson(people);

        verify(jdbcTemplate).update(anyString(), eq(people.getName()), eq(people.getBirth()));
    }

    @Test
    void updatePerson() {
        people.setName("Updated Name");

        peopleDao.updatePerson(people);

        verify(jdbcTemplate).update(anyString(), eq(people.getName()), eq(people.getBirth()), eq(people.getId()));
    }

    @Test
    void deletePerson() {
        peopleDao.deletePerson(people.getId());

        verify(jdbcTemplate).update(anyString(), eq(people.getId()));
    }
}
