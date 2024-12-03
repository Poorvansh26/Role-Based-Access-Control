package com.example.rbac.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/moderator")
public class ModeratorController {

    @GetMapping("/dashboard")
    public String moderatorDashboard() {
        return "Moderator Dashboard Accessed";
    }
}
