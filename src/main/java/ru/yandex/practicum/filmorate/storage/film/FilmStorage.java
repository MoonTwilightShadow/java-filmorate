package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    public Film create(Film film);

    public Film getFilm(Integer id);

    public List<Film> getFilms();

    public Film update(Film film);

    public Film delete(Film film);

    public void addLike(Integer filmId, Integer userId);

    public void deleteLike(Integer filmId, Integer userId);

    public List<Film> getTop(Integer count);

    public boolean containsFilm(Integer id);
}
