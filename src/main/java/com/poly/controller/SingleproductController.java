package com.poly.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SingleproductController {
    @RequestMapping("/Single-product")
    public String index() {
        return "customer/single-product";
    }

}
