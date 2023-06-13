package com.cdapps.sprintbootassessment.dao.impl;

import com.cdapps.sprintbootassessment.models.Movie;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MovieDaoJdbcImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;
    private MovieDaoJdbcImpl movieDao;
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
        MockitoAnnotations.openMocks(this);
        movieDao = new MovieDaoJdbcImpl(jdbcTemplate);
    }

    @Test
    void getMovieById() {
        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), anyInt())).thenReturn(movie);

        Movie result = movieDao.getMovieById(1);

        assertEquals(movie, result);
        verify(jdbcTemplate).queryForObject(anyString(), any(RowMapper.class), eq(1));
    }

    @Test
    void getAllMovies() {
        Movie movie2 = new Movie();
        movie2.setId(2);
        movie2.setTitle("Test Movie 2");
        movie2.setYear("2023");
        List<Movie> movies = Arrays.asList(movie, movie2);
        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(movies);

        List<Movie> result = movieDao.getAllMovies();

        assertEquals(movies, result);
        verify(jdbcTemplate).query(anyString(), any(RowMapper.class));
    }

    @Test
    void saveMovie() {
        movieDao.saveMovie(movie);

        verify(jdbcTemplate).update(anyString(), eq(movie.getTitle()), eq(movie.getYear()));
    }

    @Test
    void updateMovie() {
        movie.setTitle("Updated Movie");

        movieDao.updateMovie(movie);

        verify(jdbcTemplate).update(anyString(), eq(movie.getTitle()), eq(movie.getYear()), eq(movie.getId()));
    }

    @Test
    void deleteMovie() {
        movieDao.deleteMovie(movie.getId());

        verify(jdbcTemplate).update(anyString(), eq(movie.getId()));
    }
}
