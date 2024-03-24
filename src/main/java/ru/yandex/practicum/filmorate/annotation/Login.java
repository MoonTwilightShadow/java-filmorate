package ru.yandex.practicum.filmorate.annotation;

import ru.yandex.practicum.filmorate.annotation.validator.LoginValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {LoginValidator.class})
public @interface Login {
    String message() default "Логин не может быть пустым и содержать пробелы";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
