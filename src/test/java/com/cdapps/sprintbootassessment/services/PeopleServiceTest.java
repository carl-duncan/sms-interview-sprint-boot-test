package com.cdapps.sprintbootassessment.services;

import com.cdapps.sprintbootassessment.dao.PeopleDao;
import com.cdapps.sprintbootassessment.models.People;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PeopleServiceTest {

    private PeopleDao peopleDao;
    private PeopleService peopleService;
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
        peopleDao = Mockito.mock(PeopleDao.class);
        peopleService = new PeopleService(peopleDao);
    }

    @Test
    void getPersonById() {
        when(peopleDao.getPersonById(1)).thenReturn(people);

        People result = peopleService.getPersonById(1);

        assertEquals(people, result);
        verify(peopleDao).getPersonById(1);
    }

    @Test
    void getAllPeople() {
        Page<People> peoplePage = mock(Page.class);
        Pageable pageable = PageRequest.of(0, 5);
        when(peopleDao.getAllPeople(pageable)).thenReturn(peoplePage);

        Page<People> result = peopleService.getAllPeople(pageable);

        assertEquals(peoplePage, result);
        verify(peopleDao).getAllPeople(pageable);
    }

    @Test
    void savePerson() {
        peopleService.savePerson(people);

        verify(peopleDao).savePerson(people);
    }

    @Test
    void updatePerson() {
        peopleService.updatePerson(people);

        verify(peopleDao).updatePerson(people);
    }

    @Test
    void deletePerson() {
        peopleService.deletePerson(1);

        verify(peopleDao).deletePerson(1);
    }
}
