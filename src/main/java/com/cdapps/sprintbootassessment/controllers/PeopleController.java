package com.cdapps.sprintbootassessment.controllers;

import com.cdapps.sprintbootassessment.models.People;
import com.cdapps.sprintbootassessment.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;

    @Autowired
    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping("/{id}")
    public People getPerson(@PathVariable int id) {
        return peopleService.getPersonById(id);
    }

    @GetMapping
    public Page<People> getAllPeople(Pageable pageable) {
        return peopleService.getAllPeople(pageable);
    }

    @PostMapping
    public void createPerson(@RequestBody People person) {
        peopleService.savePerson(person);
    }

    @PutMapping
    public void updatePerson(@RequestBody People person) {
        peopleService.updatePerson(person);
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable int id) {
        peopleService.deletePerson(id);
    }

    @GetMapping("/search")
    public Page<People> searchPeopleByName(@RequestParam String name, Pageable pageable){
        return peopleService.searchPeopleByName(name, pageable);
    }
}
