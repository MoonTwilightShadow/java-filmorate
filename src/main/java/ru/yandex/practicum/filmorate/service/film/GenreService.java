package ru.yandex.practicum.filmorate.service.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.NotFoundExeption;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.film.impl.GenreDBStorage;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenreService {
    private final GenreDBStorage genreStorage;

    public Genre getGenre(Integer id) {
        log.info(String.format("Получение жанра с id=%d", id));

        Optional<Genre> genre = genreStorage.getGenreById(id);
        if (genre.isPresent()) {
            return genre.get();
        } else {
            log.warn(String.format("Жанра с id=%d не существует", id));
            throw new NotFoundExeption();
        }
    }

    public List<Genre> getGenres() {
        log.info("Получение списка жанров");
        return genreStorage.getGenres();
    }
}
