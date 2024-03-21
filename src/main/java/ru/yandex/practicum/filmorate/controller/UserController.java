package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeption.NotFoundExeption;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

@RestController
@Slf4j
public class UserController {
    private HashMap<Integer, User> users = new HashMap<>();
    private HashSet<User> usersSet;
    private int nextId = 1;

    @GetMapping("/users")
    public Collection<User> getAllUsers() {
        return users.values();
    }

    @PostMapping("/users")
    public User create(@RequestBody @Valid User user) {
        log.info("Создание пользователя");

        user.setId(nextId++);

        if (user.getName() == null || user.getName().isBlank())
            user.setName(user.getLogin());

        users.put(user.getId(), user);

        return user;
    }

    @PutMapping("/users")
    public User update(@RequestBody @Valid User user) throws NotFoundExeption {
        log.info("Обновление пользователя");

        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
        } else {
            log.warn("Пользователя с таким id не существует");
            throw new NotFoundExeption();
        }

        return user;
    }
}
