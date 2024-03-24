package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeption.NotFoundExeption;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;

@RestController
@Slf4j
public class FilmController {
    private HashMap<Integer, Film> films = new HashMap<>();
    private int nextId = 1;

    @GetMapping("/films")
    public Collection<Film> getAllFilms() {
        return films.values();
    }

    @PostMapping("/films")
    public Film create(@RequestBody @Valid Film film) {
        log.info("Создание фильма");

        film.setId(nextId++);
        films.put(film.getId(), film);

        return film;
    }

    @PutMapping("/films")
    public Film update(@RequestBody @Valid Film film) throws NotFoundExeption {
        log.info("Обновление фильма");

        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
        } else {
            log.warn("Фильма с таким id не существует");
            throw new NotFoundExeption();
        }

        return film;
    }
}
