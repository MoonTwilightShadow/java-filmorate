package ru.yandex.practicum.filmorate.service.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserService {
    public void addFriend(Integer id, Integer friendId);

    public void deleteFriend(Integer id, Integer friendId);

    public List<User> getFriends(Integer id);

    public List<User> commonFriends(Integer id, Integer otherId);

    public User getUser(Integer id);

    public List<User> getUsers();

    public User create(User user);

    public User update(User user);

    public User delete(User user);

}
