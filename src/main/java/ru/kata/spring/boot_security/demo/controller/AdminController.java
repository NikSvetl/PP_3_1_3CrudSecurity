package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


@Controller
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
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
    public String allUsers(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("logUser", user);
        model.addAttribute("users", userService.getListUsers());
        model.addAttribute("newUser", new User());
        model.addAttribute("updateUser", new User());
        model.addAttribute("roles", roleService.allRoles());
        return "admin";
    }

    @PostMapping()
    public String saveUser(@ModelAttribute("newUser") User user,
                           @RequestParam(required = false, name = "roles") int id) {
        user.setRoles(Collections.singleton(roleService.getRoleById(id)));
        userService.addUser(user);
        return "redirect:/admin";
    }

    @PutMapping()
    public String updateUser(@RequestParam(required = false, name = "nameEdit") String nameEdit,
                             @RequestParam(required = false, name = "lastNameEdit") String lastNameEdit,
                             @RequestParam(required = false, name = "ageEdit") int ageEdit,
                             @RequestParam(required = false, name = "emailEdit") String emailEdit,
                             @RequestParam(required = false, name = "passwordEdit") String passwordEdit,
                             @RequestParam(required = false, name = "roles[]") String[] ROLES,
                             @RequestParam(name = "idEdit") int idEdit) {
        User updateUser = userService.getUserById(idEdit);
        updateUser.setName(nameEdit);
        updateUser.setLastName(lastNameEdit);
        updateUser.setAge(ageEdit);
        updateUser.setEmail(emailEdit);
        updateUser.setPassword(passwordEdit);
        Set<Role> roleSet = new HashSet<>();
        if (ROLES == null) {
            roleSet.add(roleService.getRoleById(2));
        } else {
            for (String role : ROLES) {
                roleSet.add(roleService.getRoleById(Integer.parseInt(role)));
            }
        }
        updateUser.setRoles(roleSet);
        userService.updateUser(updateUser);
        return "redirect:/admin";
    }

    @DeleteMapping("/")
    public String remove(@RequestParam(name = "idDelete") int idDelete) {
        userService.removeUser(idDelete);
        return "redirect:/admin";
    }

}