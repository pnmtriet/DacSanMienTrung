package com.poly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CheckoutController {
    @RequestMapping("/Checkout")
    public String index() {
        return "customer/CheckOut";
    }

}
