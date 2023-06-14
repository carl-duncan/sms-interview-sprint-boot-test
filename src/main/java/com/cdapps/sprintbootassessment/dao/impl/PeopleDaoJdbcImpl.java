package com.cdapps.sprintbootassessment.dao.impl;

import com.cdapps.sprintbootassessment.dao.PeopleDao;
import com.cdapps.sprintbootassessment.models.People;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PeopleDaoJdbcImpl implements PeopleDao {

    private final JdbcTemplate jdbcTemplate;

    public PeopleDaoJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<People> peopleMapper = (rs, rowNum) -> {
        People people = new People();
        people.setId(rs.getInt("id"));
        people.setName(rs.getString("name"));
        people.setBirth(rs.getInt("birth"));
        return people;
    };

    @Override
    public People getPersonById(int id) {
        String sql = "SELECT * FROM people WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, peopleMapper, id);
    }
    @Override
    public Page<People> getAllPeople(Pageable pageable) {
        String sql = "SELECT * FROM people ORDER BY id ASC LIMIT ? OFFSET ?";
        List<People> people = jdbcTemplate.query(sql, peopleMapper, pageable.getPageSize(), pageable.getOffset());

        String countSql = "SELECT count(*) FROM people";
        Integer total = jdbcTemplate.queryForObject(countSql, Integer.class);

        return new PageImpl<>(people, pageable, total);
    }

    @Override
    public void savePerson(People people) {
        String sql = "INSERT INTO people (name, birth) VALUES (?, ?)";
        jdbcTemplate.update(sql, people.getName(), people.getBirth());
    }

    @Override
    public void updatePerson(People people) {
        String sql = "UPDATE people SET name = ?, birth = ? WHERE id = ?";
        jdbcTemplate.update(sql, people.getName(), people.getBirth(), people.getId());
    }

    @Override
    public void deletePerson(int id) {
        String sql = "DELETE FROM people WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
