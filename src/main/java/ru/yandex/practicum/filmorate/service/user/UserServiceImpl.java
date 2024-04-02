package ru.yandex.practicum.filmorate.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeption.IncorrectParameterException;
import ru.yandex.practicum.filmorate.exeption.NotFoundExeption;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;

    @Override
    public void addFriend(Integer id, Integer friendId) {
        log.info("Добавление в друзья");

        checkId(id, "id");
        checkId(friendId, "friendId");

        userStorage.addFriend(id, friendId);
    }

    @Override
    public void deleteFriend(Integer id, Integer friendId) {
        log.info("Удаление из друзей");

        checkId(id, "id");
        checkId(friendId, "friendId");

        userStorage.deleteFriend(id, friendId);
    }

    @Override
    public List<User> getFriends(Integer id) {
        log.info("Список друзей пользователя " + id);

        checkId(id, "id");

        return userStorage.getFriends(id);
    }

    @Override
    public List<User> commonFriends(Integer id, Integer otherId) {
        log.info("Список общих друзей");

        checkId(id, "id");
        checkId(otherId, "otherId");

        return userStorage.commonFriends(id, otherId);
    }

    @Override
    public User getUser(Integer id) {
        checkId(id, "id");

        return userStorage.getUser(id);
    }

    @Override
    public List<User> getUsers() {
        return userStorage.getUsers();
    }

    @Override
    public User create(User user) {
        log.info("Создание пользователя");

        if (user.getName() == null || user.getName().isBlank())
            user.setName(user.getLogin());

        return userStorage.create(user);
    }

    @Override
    public User update(User user) {
        log.info("Обновление пользователя");

        checkId(user.getId(), "id");

        return userStorage.update(user);
    }

    @Override
    public User delete(User user) {
        log.info("Удаление пользователя");

        checkId(user.getId(), "id");

        return userStorage.delete(user);
    }

    private void checkId(Integer id, String param) {
        if (id == null) {
            log.warn("Пустой параметр " + param);
            throw new IncorrectParameterException(param);
        } else if (userStorage.getUser(id) == null) {
            log.warn("Пользователя с таким " + param + " не существует");
            throw new NotFoundExeption();
        }
    }
}
