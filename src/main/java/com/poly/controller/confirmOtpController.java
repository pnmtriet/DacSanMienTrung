package com.poly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class confirmOtpController {
	  @RequestMapping("/confirmOtp")
	    public String index() {
	        return "customer/confirmOtp";
	    }
}
