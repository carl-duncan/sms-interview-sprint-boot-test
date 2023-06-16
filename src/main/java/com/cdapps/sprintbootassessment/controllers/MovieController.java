package com.cdapps.sprintbootassessment.controllers;

import com.cdapps.sprintbootassessment.models.Movie;
import com.cdapps.sprintbootassessment.models.People;
import com.cdapps.sprintbootassessment.models.Rating;
import com.cdapps.sprintbootassessment.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/{id}")
    public Movie getMovie(@PathVariable int id) {
        return movieService.getMovieById(id);
    }

    @GetMapping
    public Page<Movie> getAllMovies(Pageable pageable) {
        return movieService.getAllMovies(pageable);
    }

    @PostMapping
    public void createMovie(@RequestBody Movie movie) {
        movieService.saveMovie(movie);
    }

    @PutMapping
    public void updateMovie(@RequestBody Movie movie) {
        movieService.updateMovie(movie);
    }

    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable int id) {
        movieService.deleteMovie(id);
    }

    @GetMapping("/director/{directorId}")
    public Page<Movie> getMoviesByDirectorId(@PathVariable int directorId, Pageable pageable){
        return movieService.getMoviesByDirectorId(directorId, pageable);
    }

    @GetMapping("/star/{starId}")
    public Page<Movie> getMoviesByStarId(@PathVariable int starId, Pageable pageable){
        return movieService.getMoviesByStarId(starId, pageable);
    }

    @GetMapping("/{movieId}/stars")
    public Page<People> getStarsByMovieId(@PathVariable int movieId, Pageable pageable){
        return movieService.getStarsByMovieId(movieId, pageable);
    }

    @GetMapping("/{movieId}/directors")
    public Page<People> getDirectorsByMovieId(@PathVariable int movieId, Pageable pageable){
        return movieService.getDirectorsByMovieId(movieId, pageable);
    }

    @GetMapping("/{movieId}/ratings")
    public Page<Rating> getRatingsByMovieId(@PathVariable int movieId, Pageable pageable){
        return movieService.getRatingsByMovieId(movieId, pageable);
    }

    @GetMapping("/search")
    public Page<Movie> searchMovieByTitle(@RequestParam String title, Pageable pageable){
        return movieService.searchMoviesByTitle(title, pageable);
    }
}
