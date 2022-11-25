package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.entity.User;

import java.util.List;

public interface UserService {

    void addUser(User user, String[] roles);

    void updateUser(User user, String[] roles);

    void removeUser(int id);

    User getUserById(int id);

    List<User> getListUsers();

    void addNewUser(User user);

}