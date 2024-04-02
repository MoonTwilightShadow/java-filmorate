package ru.yandex.practicum.filmorate.service.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.IncorrectParameterException;
import ru.yandex.practicum.filmorate.exeption.NotFoundExeption;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Override
    public void addLike(Integer filmId, Integer userId) {
        log.info("Добавление лайка");

        checkFilmId(filmId);
        checkUserId(userId);

        filmStorage.addLike(filmId, userId);
    }

    @Override
    public void deleteLike(Integer filmId, Integer userId) {
        log.info("Удаление лайка");

        checkFilmId(filmId);
        checkUserId(userId);

        filmStorage.deleteLike(filmId, userId);
    }

    @Override
    public List<Film> getTop(Integer count) {
        log.info("Получение популярных фильмов");
        return filmStorage.getTop(count);
    }

    @Override
    public Film create(Film film) {
        log.info("Добавление фильма");
        return filmStorage.create(film);
    }

    @Override
    public Film update(Film film) {
        log.info("Обновление фильма");

        checkFilmId(film.getId());

        return filmStorage.update(film);
    }

    @Override
    public Film delete(Film film) {
        log.info("Удаление фильма");

        checkFilmId(film.getId());

        return filmStorage.delete(film);
    }

    @Override
    public Film getFilm(Integer id) {
        log.info("Получение фильма");

        checkFilmId(id);

        return filmStorage.getFilm(id);
    }

    @Override
    public List<Film> getFilms() {
        log.info("Получение всех фильмов");
        return filmStorage.getFilms();
    }

    private void checkFilmId(Integer id) {
        if (id == null) {
            log.warn("Не задано id");
            throw new IncorrectParameterException("id");
        }
        if (filmStorage.getFilm(id) == null) {
            log.warn("Фмльма с таким id не существует");
            throw new NotFoundExeption();
        }
    }

    private void checkUserId(Integer id) {
        if (id == null) {
            log.warn("Не задано userId");
            throw new IncorrectParameterException("userId");
        }
        if (userStorage.getUser(id) == null) {
            log.warn("Пользователя с таким userId не существует");
            throw new NotFoundExeption();
        }
    }
}
