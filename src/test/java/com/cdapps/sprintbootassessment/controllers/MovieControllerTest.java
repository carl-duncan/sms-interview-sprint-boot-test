package com.cdapps.sprintbootassessment.controllers;

import com.cdapps.sprintbootassessment.models.Movie;
import com.cdapps.sprintbootassessment.services.MovieService;
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

class MovieControllerTest {

    @Mock
    private MovieService movieService;

    @InjectMocks
    private MovieController movieController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private Movie movie;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(movieController).build();
        objectMapper = new ObjectMapper();

        movie = new Movie();
        movie.setId(1);
        movie.setTitle("Test Movie");
        movie.setYear("2023");
    }

    @Test
    void getMovie() throws Exception {
        when(movieService.getMovieById(1)).thenReturn(movie);

        mockMvc.perform(get("/movies/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(movie)));

        verify(movieService).getMovieById(1);
    }

    @Test
    void createMovie() throws Exception {
        mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movie)))
                .andExpect(status().isOk());

        verify(movieService).saveMovie(any(Movie.class));
    }

    @Test
    void updateMovie() throws Exception {
        mockMvc.perform(put("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movie)))
                .andExpect(status().isOk());

        verify(movieService).updateMovie(any(Movie.class));
    }

    @Test
    void deleteMovie() throws Exception {
        mockMvc.perform(delete("/movies/{id}", 1))
                .andExpect(status().isOk());

        verify(movieService).deleteMovie(1);
    }
}
