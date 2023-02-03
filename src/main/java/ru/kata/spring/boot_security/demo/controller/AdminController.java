package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Collections;


@Controller
@PreAuthorize(value = "hasAuthority('ROLE_ADMIN')")
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
    public String allUsers(Model model) {
        model.addAttribute("users", userService.getListUsers());

        return "admin";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("listRoles", roleService.allRoles());
        return "new";
    }

    @PostMapping()
    public String saveUser(@ModelAttribute("user") User user,
                           @RequestParam("listRoles") int id) {
        user.setRoles(Collections.singleton(roleService.getRoleById(id)));
        userService.addUser(user);

        return "redirect:/admin";

    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("listRoles", roleService.allRoles());
        return "edit";
    }

    @PutMapping("/{id}")
    public String update(@ModelAttribute("user") User user,
                         @RequestParam("listRoles") int id) {
        user.setRoles(Collections.singleton(roleService.getRoleById(id)));
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String remove(@PathVariable("id") int id) {
        userService.removeUser(id);
        return "redirect:/admin";
    }

}