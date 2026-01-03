package com.radud.batwatch.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testuser")
public class TestControllerUser {

    @GetMapping
    public String userEndpoint() {
        return "User access granted";
    }
}
