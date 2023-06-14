package com.cdapps.sprintbootassessment.controllers;

import com.cdapps.sprintbootassessment.models.People;
import com.cdapps.sprintbootassessment.services.PeopleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PeopleControllerTest {

    @Mock
    private PeopleService peopleService;

    @InjectMocks
    private PeopleController peopleController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private People person;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(peopleController).build();
        objectMapper = new ObjectMapper();

        person = new People();
        person.setId(1);
        person.setName("John Doe");
        person.setBirth(1990);
    }

    @Test
    void getPerson() throws Exception {
        when(peopleService.getPersonById(1)).thenReturn(person);

        mockMvc.perform(get("/people/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(person)));

        verify(peopleService).getPersonById(1);
    }

    @Test
    void createPerson() throws Exception {
        mockMvc.perform(post("/people")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isOk());

        verify(peopleService).savePerson(any(People.class));
    }

    @Test
    void updatePerson() throws Exception {
        mockMvc.perform(put("/people")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isOk());

        verify(peopleService).updatePerson(any(People.class));
    }

    @Test
    void deletePerson() throws Exception {
        mockMvc.perform(delete("/people/{id}", 1))
                .andExpect(status().isOk());

        verify(peopleService).deletePerson(1);
    }
}
