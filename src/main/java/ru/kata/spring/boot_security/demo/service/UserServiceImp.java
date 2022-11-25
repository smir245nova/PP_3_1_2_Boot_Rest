package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;

@Service
public class UserServiceImp implements UserService, UserDetailsService {

    private final UserDao userDao;

    private final RoleDao roleDao;

    public UserServiceImp(UserDao userDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    @Override
    @Transactional
    public void addUser(User user, String[] roles) {
        getRolesAndSet(user, roles);
        userDao.addUser(user);
    }

    @Override
    @Transactional
    public void updateUser(User user, String[] roles) {
        getRolesAndSet(user, roles);
        userDao.updateUser(user);
    }

    private void getRolesAndSet(User user, String[] roles) {
        if (roles != null) {
            HashSet<Role> roles1 = new HashSet<>();
            for (String s : roles) {
                roles1.add(roleDao.findByRole(s));
                System.out.println(roleDao.findByRole(s));
            }
            user.setRoles(roles1);
        }
    }

    @Override
    @Transactional
    public void removeUser(int id) {
        if (userDao.getUserById(id) != null) {
            userDao.removeUser(id);
        }
    }

    @Override
    public User getUserById(int id) {
        return userDao.getUserById(id);
    }

    @Override
    public List<User> getListUsers() {
        return userDao.getListUsers();
    }

    @Override
    @Transactional
    public void addNewUser(User user) {
        userDao.addUser(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }

}
