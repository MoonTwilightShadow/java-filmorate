package ru.yandex.practicum.filmorate.annotation.validator;

import ru.yandex.practicum.filmorate.annotation.ReleaseDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class ReleaseDateValidator implements ConstraintValidator<ReleaseDate, LocalDate> {

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        return localDate.isAfter(LocalDate.of(1895, 12, 27));
    }
}
