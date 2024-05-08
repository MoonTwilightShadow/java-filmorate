package ru.yandex.practicum.filmorate.service.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.NotFoundExeption;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.film.impl.MpaDBStorage;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class MpaService {
    private final MpaDBStorage mpaStorage;

    public Mpa getMpa(Integer id) {
        log.info(String.format("Получение MPA с id=%d", id));

        Optional<Mpa> mpa = mpaStorage.getMpaById(id);
        if (mpa.isPresent()) {
            return mpa.get();
        } else {
            log.warn(String.format("MPA с id = %d не сущуствует", id));
            throw new NotFoundExeption();
        }
    }

    public List<Mpa> getAllMpa() {
        log.info("Получение списка MPA");
        return mpaStorage.getAllMpa();
    }
}
