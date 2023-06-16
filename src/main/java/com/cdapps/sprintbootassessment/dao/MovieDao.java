package com.cdapps.sprintbootassessment.dao;

import com.cdapps.sprintbootassessment.models.Movie;
import com.cdapps.sprintbootassessment.models.People;
import com.cdapps.sprintbootassessment.models.Rating;
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

    Page<People> getStarsByMovieId(int movieId, Pageable pageable);

    Page<People> getDirectorsByMovieId(int movieId, Pageable pageable);

    Page<Rating> getRatingsByMovieId(int movieId, Pageable pageable);

    Page<Movie> searchMoviesByTitle(String title, Pageable pageable);
}
