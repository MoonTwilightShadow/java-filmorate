package ru.yandex.practicum.filmorate.exeption;

public class IncorrectParameterException extends RuntimeException {
    private String parameter;

    public IncorrectParameterException(String parameter) {
        this.parameter = parameter;
    }

    public String getParameter() {
        return parameter;
    }
}
