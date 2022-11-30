package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final RoleService roleService;
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(RoleService roleService, UserDao userDao, @Qualifier("PasswordEncoder") PasswordEncoder passwordEncoder) {
        this.roleService = roleService;
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAllUser() {
        return userDao.getAllUser();
    }

    @Transactional
    @Override
    public void deleteUserById(int id) {
        userDao.deleteUserById(id);
    }




    @Transactional
    @Override
    public void updateUser(User user) {
        List<Role> listroles = new ArrayList<>();
        for (Role role : user.getRoles()) {
            listroles.add(roleService.getByName(role.getName()));
        }
        user.setRoles(listroles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.updateUser(user);
    }

    @Transactional
    @Override
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        List<Role> listroles = new ArrayList<>();
        for (Role role : user.getRoles()) {
            listroles.add(roleService.getByName(role.getName()));
        }
        user.setRoles(listroles);
        userDao.addUser(user);
    }

    @Override
    public User getUserById(int id) {
        return userDao.getUserById(id);
    }

    @Override
    public User getUserByName(String username) {
        return userDao.getByUserName(username);
    }


}