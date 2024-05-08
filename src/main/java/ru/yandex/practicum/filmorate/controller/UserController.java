package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;

import javax.validation.Valid;
import java.util.List;

@SuppressWarnings("checkstyle:RightCurly")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public User getUser(@PathVariable Integer id) {
        return userService.getUser(id);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getUsers();
    }

    @PostMapping
    public User create(@RequestBody @Valid User user) {
        return userService.create(user);
    }

    @PutMapping
    public User update(@RequestBody @Valid User user) {
        return userService.update(user);
    }

    @DeleteMapping
    public User delete(@RequestBody User user) {
        return userService.delete(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
        userService.deleteFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriends(@PathVariable Integer id) {
        return userService.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> commonFriends(@PathVariable Integer id, @PathVariable Integer otherId) {
        return userService.commonFriends(id, otherId);
    }
}
