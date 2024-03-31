package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeption.NotFoundExeption;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.impl.InMemoryFilmStorage;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;

@RestController
@Slf4j
public class FilmController {
    private final FilmStorage filmStorage;

    @Autowired
    public FilmController(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    @GetMapping("/films")
    public Collection<Film> getAllFilms() {
        return filmStorage.getFilms();
    }

    @PostMapping("/films")
    public Film create(@RequestBody @Valid Film film) {
        log.info("Создание фильма");

        return filmStorage.create(film);
    }

    @PutMapping("/films")
    public Film update(@RequestBody @Valid Film film) {
        log.info("Обновление фильма");

        return filmStorage.update(film);
    }
}
