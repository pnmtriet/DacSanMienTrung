package com.poly.controller;

import com.poly.dao.AccountDAO;
import com.poly.dao.SessionDAO;
import com.poly.entity.Account;
import com.poly.message.MESSAGE_CONSTANT;
import com.poly.validate.AccountValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("")
public class LoginController {
    String path="";
    MESSAGE_CONSTANT MESSAGE=new MESSAGE_CONSTANT();
    AccountValidate accountValidate=new AccountValidate();
    @Autowired
    AccountDAO accountDAO;
    @Autowired
    SessionDAO session;

    @GetMapping("/login")
    public String login(Model model, @RequestParam Optional<String> urlReturn, @RequestParam Optional<String> error) {
        if(session.get("user")!=null){
            return "redirect:/index";
        }
        path=urlReturn.isPresent()?urlReturn.get():"";
        if(error.isPresent()) {
            if(error.get().equals("errorNoLogin")){
                model.addAttribute("message", "Vui lòng đăng nhập để tiếp tục!");
            }else{
                model.addAttribute("message", error.get());
            }
        }
        return "customer/login";
    }

    @GetMapping("/logout")
    public String logout(@RequestParam Optional<String> urlReturn) {
        session.clear();
        return urlReturn.isPresent()?"redirect:/" +urlReturn.get():"redirect:/index";
    }

    @PostMapping("/login")
    public String login(Model model, @RequestParam String username, @RequestParam String password) {
        boolean loginSuccess = false;
        List<String> listCheck=new ArrayList<>();
        listCheck.add(username);
        listCheck.add(password);
        if (!accountValidate.listIsNullOrEmpty(listCheck)) {
            List<Account> list = accountDAO.findAll();
            for (Account account : list) {
                if (username.equalsIgnoreCase(account.getUserName()) && password.equals(account.getPassWord())) {
                    session.set("user", account);
                    loginSuccess = true;
                    break;
                }
            }
        }
        if(loginSuccess) {
            return !path.isEmpty() ? "redirect:/"+path : "redirect:/index";
        }else {
            return "redirect:/login";
        }
    }

}