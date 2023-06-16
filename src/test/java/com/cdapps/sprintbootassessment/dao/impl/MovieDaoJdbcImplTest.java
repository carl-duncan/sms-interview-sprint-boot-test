package com.cdapps.sprintbootassessment.dao.impl;

import com.cdapps.sprintbootassessment.models.Movie;
import com.cdapps.sprintbootassessment.models.People;
import com.cdapps.sprintbootassessment.models.Rating;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @Test
    void getMoviesByStarId() {
        final int starId = 1;
        final int pageSize = 10;
        final int offset = 0;

        when(jdbcTemplate.query(anyString(), any(RowMapper.class), any(), any(), any())).thenReturn(List.of(movie));
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), eq(starId))).thenReturn(1);

        final Pageable pageable = PageRequest.of(offset, pageSize);

        Page<Movie> result = movieDao.getMoviesByStarId(starId, pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals(movie, result.getContent().get(0));
    }

    @Test
    void getMoviesByDirectorId() {
        final int directorId = 1;
        final int pageSize = 10;
        final int offset = 0;

        when(jdbcTemplate.query(anyString(), any(RowMapper.class), any(), any(), any())).thenReturn(List.of(movie));
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), eq(directorId))).thenReturn(1);

        final Pageable pageable = PageRequest.of(offset, pageSize);

        Page<Movie> result = movieDao.getMoviesByDirectorId(directorId, pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals(movie, result.getContent().get(0));
    }

    @Test
    void getStarsByMovieId() {
        final int movieId = 1;
        final int pageSize = 10;
        final int offset = 0;

        when(jdbcTemplate.query(anyString(), any(RowMapper.class), any(), any(), any())).thenReturn(List.of(new People()));
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), eq(movieId))).thenReturn(1);

        final Pageable pageable = PageRequest.of(offset, pageSize);

        Page<People> result = movieDao.getStarsByMovieId(movieId, pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
    }

    @Test
    void getDirectorsByMovieId() {
        final int movieId = 1;
        final int pageSize = 10;
        final int offset = 0;

        when(jdbcTemplate.query(anyString(), any(RowMapper.class), any(), any(), any())).thenReturn(List.of(new People()));
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), eq(movieId))).thenReturn(1);

        final Pageable pageable = PageRequest.of(offset, pageSize);

        Page<People> result = movieDao.getDirectorsByMovieId(movieId, pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
    }

    @Test
    void getRatingsByMovieId() {
        final int movieId = 1;
        final int pageSize = 10;
        final int offset = 0;

        when(jdbcTemplate.query(anyString(), any(RowMapper.class), any(), any(), any())).thenReturn(List.of(new Rating()));
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), eq(movieId))).thenReturn(1);

        final Pageable pageable = PageRequest.of(offset, pageSize);

        Page<Rating> result = movieDao.getRatingsByMovieId(movieId, pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
    }

    @Test
    void searchMoviesByTitle() {
        final int pageSize = 10;
        String title = "Test";
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), anyString(), anyInt(), anyInt()))
                .thenReturn(List.of(movie));
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), anyString()))
                .thenReturn(1);
        final Pageable pageable = Pageable.ofSize(pageSize);

        var result = movieDao.searchMoviesByTitle(title, pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        verify(jdbcTemplate).query(anyString(), any(RowMapper.class), eq("%" + title + "%"), eq(pageSize), eq(pageable.getOffset()));
        verify(jdbcTemplate).queryForObject(anyString(), eq(Integer.class), eq("%" + title + "%"));
    }
}
