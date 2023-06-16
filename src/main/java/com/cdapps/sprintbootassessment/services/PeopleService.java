package com.cdapps.sprintbootassessment.services;

import com.cdapps.sprintbootassessment.dao.PeopleDao;
import com.cdapps.sprintbootassessment.models.People;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PeopleService {

    private final PeopleDao peopleDao;

    @Autowired
    public PeopleService(PeopleDao peopleDao) {
        this.peopleDao = peopleDao;
    }

    public People getPersonById(int id) {
        return peopleDao.getPersonById(id);
    }

    public Page<People> getAllPeople(Pageable pageable) {
        return peopleDao.getAllPeople(pageable);
    }

    public void savePerson(People person) {
        peopleDao.savePerson(person);
    }

    public void updatePerson(People person) {
        peopleDao.updatePerson(person);
    }

    public void deletePerson(int id) {
        peopleDao.deletePerson(id);
    }

    public Page<People> searchPeopleByName(String title, Pageable pageable){
        return peopleDao.searchPeopleByName(title, pageable);
    }
}
