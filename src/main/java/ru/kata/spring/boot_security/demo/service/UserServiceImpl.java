package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.exception.UserNotCreatedException;
import ru.kata.spring.boot_security.demo.exception.UserNotFoundException;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public List<User> getListUsers() {
        return userRepo.findAll();
    }

    @Override
    @Transactional
    public void addUser(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepo.save(user);
        } catch (RuntimeException e) {
            throw new UserNotCreatedException();
        }
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        try {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        } catch (RuntimeException e) {
            throw new UserNotCreatedException();
        }
    }

    @Override
    @Transactional
    public void removeUser(int id) {
        try {
            userRepo.deleteById(id);
        } catch (RuntimeException e) {
            throw new UserNotFoundException();
        }
    }

    @Override
    @Transactional
    public User getUserById(int id) {
        try {
            return userRepo.findById(id).get();
        } catch (RuntimeException e) {
            throw new UserNotFoundException();
        }
    }

    @Override
    @Transactional
    public User getUserByEmail(String email) {
        try {
            return userRepo.findUsersByEmail(email);
        } catch (RuntimeException e) {
            throw new UserNotFoundException();
        }
    }
}