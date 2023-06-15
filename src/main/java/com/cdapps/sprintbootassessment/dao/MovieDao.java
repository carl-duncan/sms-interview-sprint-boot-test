package com.cdapps.sprintbootassessment.dao;

import com.cdapps.sprintbootassessment.models.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieDao {

    Movie getMovieById(int id);

    Page<Movie> getAllMovies(Pageable pageable);

    void saveMovie(Movie movie);

    void updateMovie(Movie movie);

    void deleteMovie(int id);

    Page<Movie> getMoviesByStarId(int starId, Pageable pageable);

    Page<Movie> getMoviesByDirectorId(int directorId, Pageable pageable);
}
