package ru.yandex.practicum.filmorate.storage.user.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeption.NotFoundExeption;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private HashMap<Integer, User> users = new HashMap<>();
    private HashSet<User> usersSet;
    private int nextId = 1;

    @Override
    public User create(User user) {
        user.setId(nextId++);

        if (user.getName() == null || user.getName().isBlank())
            user.setName(user.getLogin());

        users.put(user.getId(), user);

        return user;
    }

    @Override
    public User update(User user) {
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
        } else {
            log.warn("Пользователя с таким id не существует");
            throw new NotFoundExeption();
        }

        return user;
    }

    @Override
    public User delete(User user) {
        if (users.containsKey(user.getId())) {
            users.remove(user.getId());
        } else {
            log.warn("Пользователя с таким id не существует");
            throw new NotFoundExeption();
        }

        return user;
    }

    @Override
    public Collection<User> getUsers() {
        return users.values();
    }
}
