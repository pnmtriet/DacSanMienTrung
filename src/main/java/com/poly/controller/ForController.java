package com.poly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ForController {
    @RequestMapping("/404")
    public String index() {
        return "customer/404";
    }

}