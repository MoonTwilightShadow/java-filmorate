package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {

    public User create(User user);

    public User update(User user);

    public User delete(User user);

    public boolean contains(int id);

    public User getUser(int id);

    public List<User> getUsers();

    public void addFriend(Integer id, Integer friendId);

    public void deleteFriend(Integer id, Integer friendId);

    public List<User> getFriends(Integer id);

    public List<User> commonFriends(Integer id, Integer otherId);
}
