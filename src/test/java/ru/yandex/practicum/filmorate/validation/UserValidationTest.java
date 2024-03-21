package ru.yandex.practicum.filmorate.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.*;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserValidationTest {
    private Validator validator;

    @BeforeEach
    public void beforeEach() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void validUserTest() {
        User user = new User();
        user.setEmail("user@user.ru");
        user.setLogin("login");
        user.setName("name");
        user.setBirthday(LocalDate.of(2000, 3, 21));

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void emailIncorrectTest() {
        User user = new User();
        user.setEmail("user.ru");
        user.setLogin("login");
        user.setName("name");
        user.setBirthday(LocalDate.of(2000, 3, 21));

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertFalse(violations.isEmpty());
    }

    @Test
    public void emptyLoginTest() {
        User user = new User();
        user.setEmail("user@user.ru");
        user.setName("name");
        user.setBirthday(LocalDate.of(2000, 3, 21));

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertFalse(violations.isEmpty());
    }

    @Test
    public void loginWithSpacesTest() {
        User user = new User();
        user.setEmail("user@user.ru");
        user.setLogin("log in");
        user.setName("name");
        user.setBirthday(LocalDate.of(2000, 3, 21));

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertFalse(violations.isEmpty());
    }

    @Test
    public void bithdayBorderTest() {
        User user = new User();
        user.setEmail("user@user.ru");
        user.setLogin("login");
        user.setName("name");
        user.setBirthday(LocalDate.of(2024, 3, 21));

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void bithdayInFutureTest() {
        User user = new User();
        user.setEmail("user@user.ru");
        user.setLogin("login");
        user.setName("name");
        user.setBirthday(LocalDate.of(2025, 3, 21));

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertFalse(violations.isEmpty());
    }
}
