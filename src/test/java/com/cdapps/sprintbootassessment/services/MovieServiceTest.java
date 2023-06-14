package com.cdapps.sprintbootassessment.services;

import com.cdapps.sprintbootassessment.dao.MovieDao;
import com.cdapps.sprintbootassessment.models.Movie;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        Page<Movie> movies = mock(Page.class);
        Pageable pageable = PageRequest.of(0, 5);
        when(movieDao.getAllMovies(pageable)).thenReturn(movies);

        Page<Movie> result = movieService.getAllMovies(pageable);

        assertEquals(movies, result);
        verify(movieDao).getAllMovies(pageable);
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
