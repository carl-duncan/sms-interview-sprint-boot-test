package com.cdapps.sprintbootassessment.controllers;

import com.cdapps.sprintbootassessment.models.People;
import com.cdapps.sprintbootassessment.services.PeopleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@SecurityRequirement(name = "Bearer Authentication")
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

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PostMapping
    public void createPerson(@RequestBody People person) {
        peopleService.savePerson(person);
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PutMapping
    public void updatePerson(@RequestBody People person) {
        peopleService.updatePerson(person);
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable int id) {
        peopleService.deletePerson(id);
    }

    @GetMapping("/search")
    public Page<People> searchPeopleByName(@RequestParam String name, Pageable pageable){
        return peopleService.searchPeopleByName(name, pageable);
    }
}
