package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.entity.User;

import java.util.List;

public interface UserDao {

    void addUser(User user);

    void updateUser(User user);

    void removeUser(int id);

    User getUserById(int id);

    User getUserByUsername(String username);

    List<User> getListUsers();

}
