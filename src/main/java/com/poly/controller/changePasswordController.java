package com.poly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class changePasswordController {
	 @RequestMapping("/changePassword")
	    public String index() {
	        return "customer/changePassword";
	    }
}
