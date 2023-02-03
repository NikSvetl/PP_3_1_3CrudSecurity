package ru.kata.spring.boot_security.demo.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class UsersTest {
    private UserService userService;
    private RoleService roleService;

    @Autowired
    public UsersTest(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void testUsersAndRoles() {

        Role role1 = new Role("ROLE_ADMIN");
        Role role2 = new Role("ROLE_USER");
        Set<Role> allRole = new HashSet<>();
        allRole.add(role1);
        allRole.add(role2);

        User user1 = new User("Ivan", "Ivanov", 23, "Ivanov@mail.ru",
                "qwerty", allRole);
        User user2 = new User("Vano", "Vanov", 32, "Vanov@mail.ru",
                "qwerty1", Collections.singleton(role1));
        User user3 = new User("Max", "Maxov", 32, "Maxov@mail.ru",
                "qwerty2", Collections.singleton(role2));
        roleService.addRole(role1);
        roleService.addRole(role2);
        Set<Role> roles = new HashSet<>();
        roles.add((Role) roleService.getRoleById(1));
        roles.add((Role) roleService.getRoleById(2));
        User user4 = new User("Dak", "Mov", 32, "Mov@mail.ru",
                "qwert", roles);

        userService.addUser(user1);
        userService.addUser(user2);
        userService.addUser(user3);
        userService.addUser(user4);
    }
}
