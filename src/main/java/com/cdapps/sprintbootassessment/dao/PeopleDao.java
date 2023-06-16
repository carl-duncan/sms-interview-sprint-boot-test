package com.cdapps.sprintbootassessment.dao;

import com.cdapps.sprintbootassessment.models.People;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PeopleDao {

    People getPersonById(int id);

    Page<People> getAllPeople(Pageable pageable);

    void savePerson(People person);

    void updatePerson(People person);

    void deletePerson(int id);

    Page<People> searchPeopleByName(String name, Pageable pageable);
}
