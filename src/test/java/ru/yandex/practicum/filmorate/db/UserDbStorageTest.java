package ru.yandex.practicum.filmorate.db;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.impl.UserDBStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest // указываем, о необходимости подготовить бины для работы с БД
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserDbStorageTest {
    private final JdbcTemplate jdbcTemplate;

    @Test
    public void testFindUserById() {
        // Подготавливаем данные для теста
        UserDBStorage userStorage = new UserDBStorage(jdbcTemplate);

        User user = new User(1, "user@email.ru", "vanya123", "Ivan Petrov", LocalDate.of(1990, 1, 1));
        userStorage.create(user);

        // вызываем тестируемый метод
        User savedUser = userStorage.getUser(1);

        // проверяем утверждения
        assertThat(savedUser)
                .isNotNull() // проверяем, что объект не равен null
                .usingRecursiveComparison() // проверяем, что значения полей нового
                .isEqualTo(user);        // и сохраненного пользователя - совпадают
    }

    @Test
    public void testFindUsers() {
        // Подготавливаем данные для теста
        UserDBStorage userStorage = new UserDBStorage(jdbcTemplate);
        List<User> users = new ArrayList<>();

        User user = new User(1, "user@email.ru", "vanya123", "Ivan Petrov", LocalDate.of(1990, 1, 1));
        userStorage.create(user);
        users.add(user);

        user = new User(2, "user2@email.ru", "vanya2123", "Ivan Petrov2", LocalDate.of(1990, 1, 1));
        userStorage.create(user);
        users.add(user);

        // вызываем тестируемый метод
        List<User> savedUsers = userStorage.getUsers();

        // проверяем утверждения
        assertThat(savedUsers)
                .isNotNull() // проверяем, что объект не равен null
                .usingRecursiveComparison() // проверяем, что значения полей нового
                .isEqualTo(users);        // и сохраненного пользователя - совпадают
    }

    @Test
    public void testUpdate() {
        // Подготавливаем данные для теста
        UserDBStorage userStorage = new UserDBStorage(jdbcTemplate);

        User user = new User(1, "user@email.ru", "vanya123", "Ivan Petrov", LocalDate.of(1990, 1, 1));
        userStorage.create(user);

        user.setLogin("qwerty");
        userStorage.update(user);

        // вызываем тестируемый метод
        User savedUser = userStorage.getUser(1);

        // проверяем утверждения
        assertThat(savedUser)
                .isNotNull() // проверяем, что объект не равен null
                .usingRecursiveComparison() // проверяем, что значения полей нового
                .isEqualTo(user);        // и сохраненного пользователя - совпадают
    }

    @Test
    public void testDelete() {
        // Подготавливаем данные для теста
        UserDBStorage userStorage = new UserDBStorage(jdbcTemplate);

        User user = new User(1, "user@email.ru", "vanya123", "Ivan Petrov", LocalDate.of(1990, 1, 1));
        userStorage.create(user);

        userStorage.delete(user);

        // вызываем тестируемый метод
        int countSavedUsers = userStorage.getUsers().size();

        // проверяем утверждения
        Assertions.assertEquals(0, countSavedUsers, "Количество оставшихся пользователей не 0");
    }
}
