package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

//управляет рест запросами и ответами
@RestController
@RequestMapping("/api")
public class MyRestController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public MyRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public List<User> showAllUsers() {
        return userService.getAllUser();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @PostMapping("/users")
    public User addNew(@RequestBody User user) {
        userService.addUser(user);
        return user;
    }

    @PatchMapping("/users/{id}")
    public User update(@RequestBody User user, @PathVariable("id") int id) {
        userService.updateUser(user);
        User updatedUser = userService.getUserById(id);
        return updatedUser;
    }

    @DeleteMapping("/users/{id}")
    public void delete(@PathVariable("id") int id) {
        userService.deleteUserById(id);
    }

    @GetMapping("/users/current_user")
    public User showCurrentUser(Principal principal) {
        User currentUser = userService.getUserByName(principal.getName());
        return currentUser;
    }
}
