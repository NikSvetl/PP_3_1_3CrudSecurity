package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequestMapping("admin")
public class AdminController {

    @GetMapping("")
    public String allUsers() {

        return "admin";
    }
}
