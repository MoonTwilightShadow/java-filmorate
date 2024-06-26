package ru.yandex.practicum.filmorate.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exeption.IncorrectParameterException;
import ru.yandex.practicum.filmorate.exeption.NotFoundExeption;
import ru.yandex.practicum.filmorate.model.ErrorResponse;


@RestControllerAdvice
public class ExeptiponHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse validationHandle(final MethodArgumentNotValidException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse notFoundHandle(final NotFoundExeption e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse incorectParameterHandle(final IncorrectParameterException e) {
        return new ErrorResponse(
                String.format("Ошибка с полем \"%s\".", e.getParameter())
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse throwableHandle(final Throwable e) {
        return new ErrorResponse(
                "Произошла непредвиденная ошибка."
        );
    }
}
