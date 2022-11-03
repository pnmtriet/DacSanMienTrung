package com.poly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SingleNewController {
    @RequestMapping("/Single-news")
    public String index() {
        return "customer/single-news";
    }

}
