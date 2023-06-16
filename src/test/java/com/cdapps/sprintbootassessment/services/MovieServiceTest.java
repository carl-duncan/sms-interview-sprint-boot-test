package com.cdapps.sprintbootassessment.services;

import com.cdapps.sprintbootassessment.dao.MovieDao;
import com.cdapps.sprintbootassessment.models.Movie;
import com.cdapps.sprintbootassessment.models.People;
import com.cdapps.sprintbootassessment.models.Rating;
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

    @Test
    void getMoviesByDirectorId() {
        final int directorId = 1;
        final int pageSize = 10;
        final int offset = 0;
        Page<Movie> movies = mock(Page.class);
        Pageable pageable = PageRequest.of(offset, pageSize);
        when(movieDao.getMoviesByDirectorId(eq(directorId), eq(pageable))).thenReturn(movies);

        Page<Movie> result = movieService.getMoviesByDirectorId(directorId, pageable);

        assertEquals(movies, result);
        verify(movieDao).getMoviesByDirectorId(eq(directorId), eq(pageable));
    }

    @Test
    void getMoviesByStarId() {
        final int starId = 1;
        final int pageSize = 10;
        final int offset = 0;
        Page<Movie> movies = mock(Page.class);
        Pageable pageable = PageRequest.of(offset, pageSize);
        when(movieDao.getMoviesByStarId(eq(starId), eq(pageable))).thenReturn(movies);

        Page<Movie> result = movieService.getMoviesByStarId(starId, pageable);

        assertEquals(movies, result);
        verify(movieDao).getMoviesByStarId(eq(starId), eq(pageable));
    }

    @Test
    void getStarsByMovieId() {
        final int movieId = 1;
        Page<People> people = mock(Page.class);
        Pageable pageable = PageRequest.of(0, 5);
        when(movieDao.getStarsByMovieId(movieId, pageable)).thenReturn(people);

        Page<People> result = movieService.getStarsByMovieId(movieId, pageable);

        assertEquals(people, result);
        verify(movieDao).getStarsByMovieId(movieId, pageable);
    }

    @Test
    void getDirectorsByMovieId() {
        final int movieId = 1;
        Page<People> people = mock(Page.class);
        Pageable pageable = PageRequest.of(0, 5);
        when(movieDao.getDirectorsByMovieId(movieId, pageable)).thenReturn(people);

        Page<People> result = movieService.getDirectorsByMovieId(movieId, pageable);

        assertEquals(people, result);
        verify(movieDao).getDirectorsByMovieId(movieId, pageable);
    }

    @Test
    void getRatingsByMovieId() {
        final int movieId = 1;
        Page<Rating> ratings = mock(Page.class);
        Pageable pageable = PageRequest.of(0, 5);
        when(movieDao.getRatingsByMovieId(movieId, pageable)).thenReturn(ratings);

        Page<Rating> result = movieService.getRatingsByMovieId(movieId, pageable);

        assertEquals(ratings, result);
        verify(movieDao).getRatingsByMovieId(movieId, pageable);
    }

    @Test
    void searchMoviesByTitle() {
        final String title = "Test Movie";
        Page<Movie> movies = mock(Page.class);
        Pageable pageable = PageRequest.of(0, 5);
        when(movieDao.searchMoviesByTitle(title, pageable)).thenReturn(movies);

        Page<Movie> result = movieService.searchMoviesByTitle(title, pageable);

        assertEquals(movies, result);
        verify(movieDao).searchMoviesByTitle(title, pageable);
    }

}
