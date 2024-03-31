package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeption.NotFoundExeption;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

@RestController
@Slf4j
public class UserController {
    private final UserStorage userStorage;

    @Autowired
    public UserController(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @GetMapping("/users")
    public Collection<User> getAllUsers() {
        return userStorage.getUsers();
    }

    @PostMapping("/users")
    public User create(@RequestBody @Valid User user) {
        log.info("Создание пользователя");

        return userStorage.create(user);
    }

    @PutMapping("/users")
    public User update(@RequestBody @Valid User user) {
        log.info("Обновление пользователя");

        return userStorage.update(user);
    }
}
