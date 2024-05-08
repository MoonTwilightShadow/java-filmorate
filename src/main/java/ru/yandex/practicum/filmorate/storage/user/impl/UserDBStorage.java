package ru.yandex.practicum.filmorate.storage.user.impl;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class UserDBStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public User create(User user) {
        Map<String, Object> values = new HashMap<>();
        values.put("email", user.getEmail());
        values.put("login", user.getLogin());
        values.put("name", user.getName());
        values.put("birthday", user.getBirthday());

        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        user.setId(simpleJdbcInsert.executeAndReturnKey(values).intValue());

        return user;
    }

    @Override
    public User update(User user) {
        jdbcTemplate.update("update users SET email = ?, login = ?, name = ?, birthday = ? where id = ?",
                user.getEmail(), user.getLogin(), user.getName(), user.getBirthday(), user.getId());

        return user;
    }

    @Override
    public User delete(User user) {
        jdbcTemplate.update("delete from users where id = ?", user.getId());

        return user;
    }

    @Override
    public boolean contains(int id) {
        return jdbcTemplate.queryForRowSet("select * from users where id = ?", id).next();
    }

    @Override
    public User getUser(int id) {
        SqlRowSet rs = jdbcTemplate.queryForRowSet("select * from users where id = ?", id);

        if (rs.next()) {
            return new User(rs.getInt("id"), rs.getString("email"), rs.getString("login"),
                    rs.getString("name"), rs.getDate("birthday").toLocalDate());
        } else {
            return null;
        }
    }

    @Override
    public List<User> getUsers() {
        return jdbcTemplate.query("select * from users", userRowMapper());
    }

    @Override
    public void addFriend(Integer id, Integer friendId) {
        SqlRowSet status = jdbcTemplate.queryForRowSet("select * from friends where user_id = ? and friend_id = ?", id, friendId);

        if (status.next()) {
            if (!status.getBoolean("status"))
                jdbcTemplate.update("update friends SET status = ? where user_id = ? and friend_id = ?", true, id, friendId);
        } else {
            jdbcTemplate.update("insert into friends (user_id, friend_id, status) values (?, ?, ?)", id, friendId, true);
            jdbcTemplate.update("insert into friends (user_id, friend_id, status) values (?, ?, ?)", friendId, id, false);
        }
    }

    @Override
    public void deleteFriend(Integer id, Integer friendId) {
        jdbcTemplate.update("delete from friends  where user_id = ? and friend_id = ?", id, friendId);
    }

    @Override
    public List<User> getFriends(Integer id) {
        return jdbcTemplate.query("select u.id, u.email, u.login, u.name , u.birthday from friends as f join users as u on f.friend_id = u.id where user_id  = ? and status = true",
                userRowMapper(), id);
    }

    @Override
    public List<User> commonFriends(Integer id, Integer otherId) {
        return jdbcTemplate.query("select u.id, u.email, u.login, u.name , u.birthday from friends as f " +
                        "join users as u on f.friend_id = u.id where user_id = ? and friend_id in (select friend_id from friends where user_id = ?);",
                userRowMapper(), id, otherId);
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> new User(rs.getInt("id"), rs.getString("email"), rs.getString("login"),
                rs.getString("name"), rs.getDate("birthday").toLocalDate());
    }
}
