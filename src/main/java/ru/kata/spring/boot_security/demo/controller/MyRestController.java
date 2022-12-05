package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;

//управляет рест запросами и ответами
@RestController
@RequestMapping("/api")
public class MyRestController {

    private final UserService userService;

    @Autowired
    private MyRestController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    private List<User> showAllUsers() {
        return userService.getAllUser();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users/{id}")
    private User getUser(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/users")
    private User addNew(@RequestBody User user) {
        userService.addUser(user);
        return user;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/users/{id}")
    private User update(@RequestBody User user, @PathVariable("id") int id) {
        userService.updateUser(user);
        User updatedUser = userService.getUserById(id);
        return updatedUser;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/users/{id}")
    private void delete(@PathVariable("id") int id) {
        userService.deleteUserById(id);
    }

    @GetMapping("/users/current_user")
    private User showCurrentUser(Principal principal) {
        User currentUser = userService.getUserByName(principal.getName());
        return currentUser;
    }
}
