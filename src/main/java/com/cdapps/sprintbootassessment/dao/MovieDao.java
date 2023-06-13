package com.cdapps.sprintbootassessment.dao;

import com.cdapps.sprintbootassessment.models.Movie;
import java.util.List;

public interface MovieDao {

    Movie getMovieById(int id);

    List<Movie> getAllMovies();

    void saveMovie(Movie movie);

    void updateMovie(Movie movie);

    void deleteMovie(int id);
}
