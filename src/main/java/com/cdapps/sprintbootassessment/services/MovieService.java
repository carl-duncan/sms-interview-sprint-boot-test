package com.cdapps.sprintbootassessment.services;

import com.cdapps.sprintbootassessment.dao.MovieDao;
import com.cdapps.sprintbootassessment.models.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final MovieDao movieDao;

    @Autowired
    public MovieService(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    public Movie getMovieById(int id) {
        return movieDao.getMovieById(id);
    }

    public List<Movie> getAllMovies() {
        return movieDao.getAllMovies();
    }

    public void saveMovie(Movie movie) {
        movieDao.saveMovie(movie);
    }

    public void updateMovie(Movie movie) {
        movieDao.updateMovie(movie);
    }

    public void deleteMovie(int id) {
        movieDao.deleteMovie(id);
    }
}
