package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String index(Model model, Principal principal) {
        model.addAttribute("currentUser", userService.getUserByName(principal.getName()));
        model.addAttribute("users", userService.getAllUser());
        model.addAttribute("rolesList", roleService.getAll());
        model.addAttribute("newUser", new User());
        return "admin";
    }


    @PatchMapping("/update/{id}")
    public String updateUser(User user,@PathVariable("id") int id, @RequestParam String[] roles) {
        userService.updateUser(user, roles);
        return "redirect:/admin";
    }


    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }


    @PostMapping("/save")
    public String create(@ModelAttribute("user") User user, @RequestParam String[] roles) {
        userService.addUser(user, roles);
        return "redirect:/admin";
    }
}
