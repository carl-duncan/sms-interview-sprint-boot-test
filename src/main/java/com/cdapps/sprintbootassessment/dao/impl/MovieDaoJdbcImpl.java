package com.cdapps.sprintbootassessment.dao.impl;

import com.cdapps.sprintbootassessment.dao.MovieDao;
import com.cdapps.sprintbootassessment.models.Movie;
import com.cdapps.sprintbootassessment.models.People;
import com.cdapps.sprintbootassessment.models.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MovieDaoJdbcImpl implements MovieDao {

    private final JdbcTemplate jdbcTemplate;

    public MovieDaoJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Movie> movieMapper = (rs, rowNum) -> {
        Movie movie = new Movie();
        movie.setId(rs.getInt("id"));
        movie.setTitle(rs.getString("title"));
        movie.setYear(rs.getString("year"));

        return movie;
    };

    private final RowMapper<People> peopleMapper = (rs, rowNum) -> {
        People people = new People();
        people.setId(rs.getInt("id"));
        people.setName(rs.getString("name"));
        people.setBirth(rs.getInt("birth"));

        return people;
    };

    private final RowMapper<Rating> ratingMapper = (rs, rowNum) -> {
        Rating rating = new Rating();
        rating.setYear(rs.getInt("year"));
        rating.setRating(rs.getInt("rating"));
        rating.setYear(rs.getInt("year"));

        return rating;
    };

    @Override
    public Movie getMovieById(int id) {
        String sql = "SELECT * FROM movies WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, movieMapper, id);
    }

    @Override
    public Page<Movie> getAllMovies(Pageable pageable) {
        String sql = "SELECT * FROM movies ORDER BY id ASC LIMIT ? OFFSET ?";
        List<Movie> movies = jdbcTemplate.query(sql, movieMapper, pageable.getPageSize(), pageable.getOffset());

        String countSql = "SELECT count(*) FROM movies";
        Integer total = jdbcTemplate.queryForObject(countSql, Integer.class);

        return new PageImpl<>(movies, pageable, total);
    }

    @Override
    public void saveMovie(Movie movie) {
        String sql = "INSERT INTO movies (title, year) VALUES (?, ?)";
        jdbcTemplate.update(sql, movie.getTitle(), movie.getYear());
    }

    @Override
    public void updateMovie(Movie movie) {
        String sql = "UPDATE movies SET title = ?, year = ? WHERE id = ?";
        jdbcTemplate.update(sql, movie.getTitle(), movie.getYear(), movie.getId());
    }

    @Override
    public void deleteMovie(int id) {
        String sql = "DELETE FROM movies WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Page<Movie> getMoviesByStarId(int starId, Pageable pageable) {
        String sql = "SELECT m.* FROM movies m INNER JOIN stars s ON m.id = s.movie_id WHERE s.person_id = ?";
        String countSql = "SELECT COUNT(*) FROM movies m INNER JOIN stars s ON m.id = s.movie_id WHERE s.person_id = ?";
        List<Movie> movies = jdbcTemplate.query(sql + " LIMIT ? OFFSET ?", movieMapper, starId, pageable.getPageSize(), pageable.getOffset());
        int total = jdbcTemplate.queryForObject(countSql, Integer.class, starId);

        return new PageImpl<>(movies, pageable, total);
    }

    @Override
    public Page<Movie> getMoviesByDirectorId(int directorId, Pageable pageable) {
        String sql = "SELECT m.* FROM movies m INNER JOIN directors d ON m.id = d.movie_id WHERE d.person_id = ?";
        String countSql = "SELECT COUNT(*) FROM movies m INNER JOIN directors d ON m.id = d.movie_id WHERE d.person_id = ?";
        List<Movie> movies = jdbcTemplate.query(sql + " LIMIT ? OFFSET ?", movieMapper, directorId, pageable.getPageSize(), pageable.getOffset());
        int total = jdbcTemplate.queryForObject(countSql, Integer.class, directorId);

        return new PageImpl<>(movies, pageable, total);
    }

    @Override
    public Page<People> getStarsByMovieId(int movieId, Pageable pageable) {
        String sql = "SELECT p.* FROM people p INNER JOIN stars s ON p.id = s.person_id WHERE s.movie_id = ?";
        String countSql = "SELECT COUNT(*) FROM people p INNER JOIN stars s ON p.id = s.person_id WHERE s.movie_id = ?";
        List<People> people = jdbcTemplate.query(sql + " LIMIT ? OFFSET ?", peopleMapper, movieId, pageable.getPageSize(), pageable.getOffset());
        int total = jdbcTemplate.queryForObject(countSql, Integer.class, movieId);
        return new PageImpl<>(people, pageable, total);
    }
    @Override
    public Page<People> getDirectorsByMovieId(int movieId, Pageable pageable) {
        String sql = "SELECT p.* FROM people p INNER JOIN directors d ON p.id = d.person_id WHERE d.movie_id = ?";
        String countSql = "SELECT COUNT(*) FROM people p INNER JOIN directors d ON p.id = d.person_id WHERE d.movie_id = ?";
        List<People> people = jdbcTemplate.query(sql + " LIMIT ? OFFSET ?", peopleMapper, movieId, pageable.getPageSize(), pageable.getOffset());
        int total = jdbcTemplate.queryForObject(countSql, Integer.class, movieId);

        return new PageImpl<>(people, pageable, total);
    }
    @Override
    public Page<Rating> getRatingsByMovieId(int movieId, Pageable pageable) {
        String sql = "SELECT r.* FROM ratings r WHERE r.movie_id = ?";
        String countSql = "SELECT COUNT(*) FROM ratings r WHERE r.movie_id = ?";
        List<Rating> ratings = jdbcTemplate.query(sql + " LIMIT ? OFFSET ?", ratingMapper, movieId, pageable.getPageSize(), pageable.getOffset());
        int total = jdbcTemplate.queryForObject(countSql, Integer.class, movieId);

        return new PageImpl<>(ratings, pageable, total);
    }
}
