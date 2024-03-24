package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.annotation.Login;

import javax.validation.constraints.Email;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

/**
 * User.
 */

@Data
public class User {
    private int id;
    @Email
    private String email;
    @Login
    private String login;
    private String name;
    @PastOrPresent
    private LocalDate birthday;
}
