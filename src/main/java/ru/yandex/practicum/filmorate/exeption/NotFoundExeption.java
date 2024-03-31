package ru.yandex.practicum.filmorate.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "No Such Id Exeption")
public class NotFoundExeption extends RuntimeException {
    public NotFoundExeption() {
    }
}
