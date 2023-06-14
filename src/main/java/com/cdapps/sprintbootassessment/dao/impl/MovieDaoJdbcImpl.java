package com.cdapps.sprintbootassessment.dao.impl;

import com.cdapps.sprintbootassessment.dao.MovieDao;
import com.cdapps.sprintbootassessment.models.Movie;
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
}
