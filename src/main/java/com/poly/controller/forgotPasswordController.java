package com.poly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class forgotPasswordController {
	  @RequestMapping("/forgotPassword")
	    public String index() {
	        return "customer/forgotPassword";
	    }
}
