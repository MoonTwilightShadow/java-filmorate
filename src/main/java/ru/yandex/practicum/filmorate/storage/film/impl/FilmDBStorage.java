package ru.yandex.practicum.filmorate.storage.film.impl;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class FilmDBStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;
    private final FilmGenreDBStorage filmGenreStorage;

    @Override
    public Film create(Film film) {
        String sqlQuery = "insert into films (name, description, release_date, duration, mpa_id) values (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"id"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            stmt.setDate(3, Date.valueOf(film.getReleaseDate()));
            stmt.setInt(4, film.getDuration());
            stmt.setInt(5, film.getMpa().getId());
            return stmt;
        }, keyHolder);


        int id = keyHolder.getKey().intValue();
        if (film.getGenres() != null) {
            for (Genre genre : film.getGenres()) {
                filmGenreStorage.addGenreToFilm(id, genre.getId());
            }
        }

        return getFilm(id);
    }

    @Override
    public boolean containsFilm(Integer id) {
        SqlRowSet genreRows = jdbcTemplate.queryForRowSet("select * from films where id = ?", id);
        return genreRows.next();
    }

    @Override
    public Film getFilm(Integer id) {
        String sqlQuery = "select f.id, f.name, f.description, f.release_date, f.duration, m.mpa_id, m.mpa_name from films f" +
                " left join mpa m on f.mpa_id = m.mpa_id where f.id = ?";

        Film film = jdbcTemplate.queryForObject(sqlQuery, this::makeFilm, id);
        return film;
    }

    @Override
    public List<Film> getFilms() {
        String sqlQuery = "select f.id, f.name, f.description, f.release_date, f.duration, m.mpa_id, m.mpa_name from films f" +
                " left join mpa m on f.mpa_id = m.mpa_id order by f.id";

        return jdbcTemplate.query(sqlQuery, this::makeFilm);
    }

    @Override
    public Film update(Film film) {
        jdbcTemplate.update("update films SET name = ?, description = ?, release_date = ?, duration = ?, mpa_id = ? where id = ?",
                film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getMpa().getId(), film.getId());

        return film;
    }

    @Override
    public Film delete(Film film) {
        jdbcTemplate.update("delete from films where id = ?", film.getId());
        return film;
    }

    @Override
    public void addLike(Integer filmId, Integer userId) {
        jdbcTemplate.update("insert into likes (film_id, user_id) values (?, ?)", filmId, userId);
    }

    @Override
    public void deleteLike(Integer filmId, Integer userId) {
        jdbcTemplate.update("delete from likes where film_id = ? and user_id = ?", filmId, userId);
    }

    @Override
    public List<Film> getTop(Integer count) {
        String sqlQuery = "select film_id, count(user_id) as count_likes from likes group by film_id order by count_likes desc";

        return jdbcTemplate.queryForList(sqlQuery).stream()
                .map(fl -> getFilm((Integer) fl.get("film_id")))
                .limit(count)
                .collect(Collectors.toList());
    }

    private Film makeFilm(ResultSet rs, int rowNum) throws SQLException {
        return Film.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .releaseDate(rs.getDate("release_date").toLocalDate())
                .duration(rs.getInt("duration"))
                .genres(filmGenreStorage.getFilmGenres(rs.getInt("id")))
                .mpa(new Mpa(rs.getInt("mpa_id"), rs.getString("mpa_name")))
                .build();
    }
}
