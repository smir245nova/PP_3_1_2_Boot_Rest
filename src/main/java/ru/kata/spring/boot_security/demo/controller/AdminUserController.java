package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

import java.security.Principal;

@Controller
@RequestMapping("/adminUser")
public class AdminUserController {
    private final UserServiceImp userServiceImp;

    @Autowired
    public AdminUserController(UserServiceImp userServiceImp) {
        this.userServiceImp = userServiceImp;
    }

    @GetMapping
    public String loadByUser(ModelMap model, Principal principal) {
        model.addAttribute("user", userServiceImp.loadUserByUsername(principal.getName()));
        return "adminUser";
    }

}

