package ru.yandex.practicum.filmorate.annotation.validator;

import ru.yandex.practicum.filmorate.annotation.Login;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LoginValidator implements ConstraintValidator<Login, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s != null && !s.isEmpty() && !s.contains(" ");
    }
}
