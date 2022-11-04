package com.poly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class registerController {
    @RequestMapping("/register")
    public String index() {
        return "customer/register";
    }

}