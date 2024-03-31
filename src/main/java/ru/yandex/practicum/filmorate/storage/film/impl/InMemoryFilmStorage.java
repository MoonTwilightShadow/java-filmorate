package ru.yandex.practicum.filmorate.storage.film.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeption.NotFoundExeption;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.Collection;
import java.util.HashMap;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    private HashMap<Integer, Film> films = new HashMap<>();
    private int nextId = 1;


    @Override
    public Film create(Film film) {
        film.setId(nextId++);
        films.put(film.getId(), film);

        return film;
    }

    @Override
    public Film update(Film film) {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
        } else {
            log.warn("Фильма с таким id не существует");
            throw new NotFoundExeption();
        }

        return film;
    }

    @Override
    public Film delete(Film film) {
        if (films.containsKey(film.getId())) {
            films.remove(film.getId());
        } else {
            log.warn("Фильма с таким id не существует");
            throw new NotFoundExeption();
        }

        return film;
    }

    @Override
    public Collection<Film> getFilms() {
        return films.values();
    }
}
