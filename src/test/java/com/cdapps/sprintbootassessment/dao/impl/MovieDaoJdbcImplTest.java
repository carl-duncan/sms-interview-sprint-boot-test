package com.cdapps.sprintbootassessment.dao.impl;

import com.cdapps.sprintbootassessment.models.Movie;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MovieDaoJdbcImplTest {

    @Mock
    private static JdbcTemplate jdbcTemplate;
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
        final int pageSize = 10;
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), anyInt(), anyInt())).thenReturn(List.of(movie));
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class))).thenReturn(1);
        final Pageable pageable = Pageable.ofSize(pageSize);

        var result = movieDao.getAllMovies(pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
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
