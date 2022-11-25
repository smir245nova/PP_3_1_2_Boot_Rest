package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserServiceImp userService;

    @Autowired
    public AdminController(UserServiceImp userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getUserList(Model model, Principal principal) {
        model.addAttribute("user", userService.loadUserByUsername(principal.getName()));
        model.addAttribute("users", userService.getListUsers());
        return "admin";
    }

    @GetMapping("/addUser")
    public String createUserFrom(User user, Model model) {
        model.addAttribute("user", user);
        return "addUser";
    }

    @PostMapping("/addUser")
    public String createUser(@ModelAttribute("user") User user,
                             @RequestParam(value = "rolesList", required = false) String[] roles) {
        userService.addUser(user, roles);
        return "redirect:/admin";
    }

    @GetMapping("deleteUser/{id}")
    public String removeUser(@PathVariable("id") int id) {
        userService.removeUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/updateUser/{id}")
    public String updateUserForm(User user, @RequestParam(value = "rolesList", required = false) String[] roles) {
        userService.updateUser(user, roles);
        return "redirect:/admin";
    }

}