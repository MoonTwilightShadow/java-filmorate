package ru.yandex.practicum.filmorate.storage.user.impl;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class InMemoryUserStorage implements UserStorage {
    private HashMap<Integer, User> users = new HashMap<>();
    private int nextId = 1;

    @Override
    public User create(User user) {
        user.setId(nextId++);
        users.put(user.getId(), user);

        return user;
    }

    @Override
    public User update(User user) {
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User delete(User user) {
        return users.remove(user.getId());
    }

    @Override
    public User getUser(int id) {
        return users.get(id);
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public void addFriend(Integer id, Integer friendId) {
        users.get(id).getFriends().add(friendId);
        users.get(friendId).getFriends().add(id);
    }

    @Override
    public void deleteFriend(Integer id, Integer friendId) {
        users.get(id).getFriends().remove(friendId);
        users.get(friendId).getFriends().remove(id);
    }

    @Override
    public List<User> getFriends(Integer id) {
        return users.get(id).getFriends().stream()
                .map(userId -> users.get(userId))
                .collect(Collectors.toList());
    }

    @Override
    public List<User> commonFriends(Integer id, Integer otherId) {
        Set<Integer> friends = users.get(id).getFriends();
        Set<Integer> otherFriends = users.get(otherId).getFriends();

        return friends.stream()
                .filter(otherFriends::contains)
                .map(userId -> users.get(userId))
                .collect(Collectors.toList());
    }
}
