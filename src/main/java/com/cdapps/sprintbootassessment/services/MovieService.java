package com.cdapps.sprintbootassessment.services;

import com.cdapps.sprintbootassessment.dao.MovieDao;
import com.cdapps.sprintbootassessment.models.Movie;
import com.cdapps.sprintbootassessment.models.People;
import com.cdapps.sprintbootassessment.models.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    public Page<Movie> getAllMovies(Pageable pageable) {
        return movieDao.getAllMovies(pageable);
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

    public Page<Movie> getMoviesByDirectorId(int directorId, Pageable pageable){
        return movieDao.getMoviesByDirectorId(directorId, pageable);
    }

    public Page<Movie> getMoviesByStarId(int starId, Pageable pageable){
        return movieDao.getMoviesByStarId(starId, pageable);
    }

    public Page<People> getStarsByMovieId(int movieId, Pageable pageable){
        return movieDao.getStarsByMovieId(movieId, pageable);
    }

    public Page<People> getDirectorsByMovieId(int movieId, Pageable pageable){
        return movieDao.getDirectorsByMovieId(movieId, pageable);
    }

    public Page<Rating> getRatingsByMovieId(int movieId, Pageable pageable){
        return movieDao.getRatingsByMovieId(movieId, pageable);
    }

    public Page<Movie> searchMoviesByTitle(String title, Pageable pageable){
        return movieDao.searchMoviesByTitle(title, pageable);
    }
}
