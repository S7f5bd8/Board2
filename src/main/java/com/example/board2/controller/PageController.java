package com.example.board2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/homepage/")
@RequiredArgsConstructor
public class PageController {

    @GetMapping("/home")
    public String home() {
        return "homepage/Home";
    }

    @GetMapping("/story")
    public String story() {
        return "homepage/Story";
    }

    @GetMapping("/gameplay")
    public String gameplay() {
        return "homepage/Gameplay";
    }

    @GetMapping("/characters")
    public String characters() {
        return "homepage/Characters";
    }

    @GetMapping("/gallery")
    public String gallery() {
        return "homepage/Gallery";
    }

    @GetMapping("/contact")
    public String contact() {
        return "homepage/Contact";
    }
}
