package com.poly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class newsController {
    @RequestMapping("/news")
    public String index() {
        return "customer/news";
    }

}
