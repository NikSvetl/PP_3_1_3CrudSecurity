package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.exception.UserExceptionInfo;
import ru.kata.spring.boot_security.demo.exception.UserNotCreatedException;
import ru.kata.spring.boot_security.demo.exception.UserNotFoundException;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@RestController
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequestMapping("/api")
public class RestAdminConroller {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public RestAdminConroller(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> showAllUsers() {
        List<User> userList = userService.getListUsers();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> showUser (@PathVariable int id) {
        User user = userService.getUserById(id);
        if (user == null){
            throw new UserNotFoundException();
        }
        return new ResponseEntity<>(user, HttpStatus.OK);

    }

    @PostMapping("/users")
    public  ResponseEntity<User> addUser(@RequestBody User user){
        if(user==null){
            throw new UserNotCreatedException();
        }
        userService.addUser(user);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/users")
    public ResponseEntity<User> updateUser (@RequestBody User user) {
        if(user == null){
           throw new UserNotCreatedException();
        }
        userService.updateUser(user);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<User> deleteUser (@PathVariable int id) {
        User user = userService.getUserById(id);
        if(user == null) {
            throw  new UserNotFoundException();
        }
        userService.removeUser(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<UserExceptionInfo> handleException (UserNotCreatedException e) {
        UserExceptionInfo exceptionInfo = new UserExceptionInfo("User saving error");
        return new ResponseEntity<>(exceptionInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<UserExceptionInfo> handleException (UserNotFoundException e) {
        UserExceptionInfo exceptionInfo = new UserExceptionInfo("No such user was found");
        return new ResponseEntity<>(exceptionInfo, HttpStatus.NOT_FOUND);
    }

}
