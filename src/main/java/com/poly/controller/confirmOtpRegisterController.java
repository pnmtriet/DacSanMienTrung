package com.poly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class confirmOtpRegisterController {
	  @RequestMapping("/confirmOtpRegister")
	    public String index() {
	        return "customer/confirmOtpRegister";
	    }
}
