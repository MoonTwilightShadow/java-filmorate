package ru.yandex.practicum.filmorate.storage.film.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.FilmGenre;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FilmGenreBDStorage {
    private final JdbcTemplate jdbcTemplate;
    private final GenreDBStorage genreDBStorage;

    public List<Genre> getFilmGenres(Integer id) {
        String sqlQuery = "select * from genre_films where film_id = ? order by genre_id";
        return jdbcTemplate.query(sqlQuery, this::makeFilmGenre, id).stream()
                .map(FilmGenre::getGenreId)
                .map(genreDBStorage::getGenreById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public void deleteFilmGenres(Integer id) {
        String sqlQuery = "delete from genre_films where film_id = ?";
        jdbcTemplate.update(sqlQuery, id);
    }

    public void addGenreToFilm(Integer filmId, Integer genreId) {
        String sqlQuery = "insert into genre_films (film_id, genre_id) values(?, ?) on conflict do nothing";
        jdbcTemplate.update(sqlQuery, filmId, genreId);
    }

    private FilmGenre makeFilmGenre(ResultSet rs, int rowNum) throws SQLException {
        return new FilmGenre(rs.getInt("film_id"), rs.getInt("genre_id"));
    }
}