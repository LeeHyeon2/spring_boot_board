package com.its.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    @GetMapping("/")
    public String index(HttpSession session){
        String email = "test1";
        session.setAttribute("loginEmail",email);
        return "index";
    }
}
