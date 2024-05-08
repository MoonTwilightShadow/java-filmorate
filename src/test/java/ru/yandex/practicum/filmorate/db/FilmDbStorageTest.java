package ru.yandex.practicum.filmorate.db;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.film.impl.FilmDBStorage;
import ru.yandex.practicum.filmorate.storage.film.impl.FilmGenreDBStorage;
import ru.yandex.practicum.filmorate.storage.film.impl.GenreDBStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest // указываем, о необходимости подготовить бины для работы с БД
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class FilmDbStorageTest {
    private final JdbcTemplate jdbcTemplate;

    @Test
    public void testFindFilmById() {
        // Подготавливаем данные для теста
        GenreDBStorage genreStorage = new GenreDBStorage(jdbcTemplate);
        FilmGenreDBStorage filmGenreStorage = new FilmGenreDBStorage(jdbcTemplate, genreStorage);
        FilmDBStorage filmStorage = new FilmDBStorage(jdbcTemplate, filmGenreStorage);

        Film film = new Film(1, "name", "description", LocalDate.of(2020, 12, 12), 100,
                new Mpa(1, "G"), List.of(new Genre(1, "Комедия")));

        filmStorage.create(film);

        // вызываем тестируемый метод
        Film savedFilm = filmStorage.getFilm(1);

        // проверяем утверждения
        assertThat(savedFilm)
                .isNotNull() // проверяем, что объект не равен null
                .usingRecursiveComparison() // проверяем, что значения полей нового
                .isEqualTo(film);        // и сохраненного пользователя - совпадают


    }

    @Test
    public void testFindFilms() {
        // Подготавливаем данные для теста
        GenreDBStorage genreStorage = new GenreDBStorage(jdbcTemplate);
        FilmGenreDBStorage filmGenreStorage = new FilmGenreDBStorage(jdbcTemplate, genreStorage);
        FilmDBStorage filmStorage = new FilmDBStorage(jdbcTemplate, filmGenreStorage);
        List<Film> films = new ArrayList<>();

        Film film = new Film(1, "name", "description", LocalDate.of(2020, 12, 12), 100,
                new Mpa(1, "G"), List.of(new Genre(1, "Комедия")));
        filmStorage.create(film);
        films.add(film);

        film = new Film(2, "name2", "description2", LocalDate.of(2020, 12, 12), 100,
                new Mpa(1, "G"), List.of(new Genre(1, "Комедия")));
        filmStorage.create(film);
        films.add(film);

        film = new Film(3, "name3", "description3", LocalDate.of(2020, 12, 12), 100,
                new Mpa(1, "G"), List.of(new Genre(1, "Комедия")));
        filmStorage.create(film);
        films.add(film);

        // вызываем тестируемый метод
        List<Film> savedFilms = filmStorage.getFilms();

        // проверяем утверждения
        assertThat(savedFilms)
                .isNotNull() // проверяем, что объект не равен null
                .usingRecursiveComparison() // проверяем, что значения полей нового
                .isEqualTo(films);        // и сохраненного пользователя - совпадают
    }

    @Test
    public void testUpdateFilm() {
        // Подготавливаем данные для теста
        GenreDBStorage genreStorage = new GenreDBStorage(jdbcTemplate);
        FilmGenreDBStorage filmGenreStorage = new FilmGenreDBStorage(jdbcTemplate, genreStorage);
        FilmDBStorage filmStorage = new FilmDBStorage(jdbcTemplate, filmGenreStorage);

        Film film = new Film(1, "name", "description", LocalDate.of(2020, 12, 12), 100,
                new Mpa(1, "G"), List.of(new Genre(1, "Комедия")));

        filmStorage.create(film);

        film.setName("name2");
        filmStorage.update(film);

        // вызываем тестируемый метод
        Film savedFilm = filmStorage.getFilm(1);

        // проверяем утверждения
        assertThat(savedFilm)
                .isNotNull() // проверяем, что объект не равен null
                .usingRecursiveComparison() // проверяем, что значения полей нового
                .isEqualTo(film);        // и сохраненного пользователя - совпадают
    }

    @Test
    public void testDeleteFilm() {
        // Подготавливаем данные для теста
        GenreDBStorage genreStorage = new GenreDBStorage(jdbcTemplate);
        FilmGenreDBStorage filmGenreStorage = new FilmGenreDBStorage(jdbcTemplate, genreStorage);
        FilmDBStorage filmStorage = new FilmDBStorage(jdbcTemplate, filmGenreStorage);

        Film film = new Film(1, "name", "description", LocalDate.of(2020, 12, 12), 100,
                new Mpa(1, "G"), List.of(new Genre(1, "Комедия")));

        filmStorage.create(film);

        filmStorage.delete(film);

        // вызываем тестируемый метод
        int countSavedFilms = filmStorage.getFilms().size();

        // проверяем утверждения
        Assertions.assertEquals(0, countSavedFilms, "Количество оставшихся фильмов не 0");
    }
}