package ru.kata.spring.boot_security.demo.controller;


import ru.kata.spring.boot_security.demo.exception.ExceptionInfo;
import ru.kata.spring.boot_security.demo.exception.UserUsernameExistException;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class MyRestController {

    private final UserService userService;

    @Autowired
    public MyRestController(RoleService roleService, UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userService.getAllUser(),HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<ExceptionInfo> createUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String error = getErrorsFromBindingResult(bindingResult);
            return new ResponseEntity<>(new ExceptionInfo(error), HttpStatus.BAD_REQUEST);
        }
        try {
            userService.addUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (UserUsernameExistException u) {
            throw new UserUsernameExistException("User with username exist");
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<ExceptionInfo> pageDelete(@PathVariable("id") int id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>(new ExceptionInfo("User deleted"), HttpStatus.OK);
    }

    @GetMapping("users/{id}")
    public ResponseEntity<User> getUser (@PathVariable("id") int id) {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<User> getUserByUsername (Principal principal) {
        User user = userService.getUserByName(principal.getName());
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<ExceptionInfo> pageEdit(@PathVariable("id") int id,
                                                  @Valid @RequestBody User user,
                                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String error = getErrorsFromBindingResult(bindingResult);
            return new ResponseEntity<>(new ExceptionInfo(error), HttpStatus.BAD_REQUEST);
        }
        try {
            String oldPassword = userService.getUserById(id).getPassword();
            if (oldPassword.equals(user.getPassword())) {
                System.out.println("TRUE");
                user.setPassword(oldPassword);
                userService.updateUser(user);
            } else {
                System.out.println("FALSE");
                userService.addUser(user);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (UserUsernameExistException u) {
            throw new UserUsernameExistException("User with username exist");
        }
    }

    private String getErrorsFromBindingResult(BindingResult bindingResult) {
        return bindingResult.getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("; "));
    }
}







/*import org.springframework.beans.factory.annotation.Autowired;
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
}*/
