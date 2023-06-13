package com.cdapps.sprintbootassessment.services;

import com.cdapps.sprintbootassessment.dao.MovieDao;
import com.cdapps.sprintbootassessment.models.Movie;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovieServiceTest {

    private MovieDao movieDao;
    private MovieService movieService;
    private static Movie movie;

    @BeforeAll
    static void beforeAll() {
        movie = new Movie();
        movie.setId(1);
        movie.setTitle("Test Movie");
        movie.setYear("2023");
    }
    @BeforeEach
    void setUp() {
        movieDao = Mockito.mock(MovieDao.class);
        movieService = new MovieService(movieDao);
    }

    @Test
    void getMovieById() {
        when(movieDao.getMovieById(1)).thenReturn(movie);

        Movie result = movieService.getMovieById(1);

        assertEquals(movie, result);
        verify(movieDao).getMovieById(1);
    }

    @Test
    void getAllMovies() {
        Movie movie2 = new Movie();
        movie2.setId(2);
        movie2.setTitle("Movie Title 2");
        movie2.setYear("2023");
        List<Movie> movies = Arrays.asList(movie, movie2);
        when(movieDao.getAllMovies()).thenReturn(movies);

        List<Movie> result = movieService.getAllMovies();

        assertEquals(movies, result);
        verify(movieDao).getAllMovies();
    }


    @Test
    void saveMovie() {
        movieService.saveMovie(movie);

        verify(movieDao).saveMovie(movie);
    }

    @Test
    void updateMovie() {
        movieService.updateMovie(movie);

        verify(movieDao).updateMovie(movie);
    }

    @Test
    void deleteMovie() {
        movieService.deleteMovie(1);

        verify(movieDao).deleteMovie(1);
    }
}
