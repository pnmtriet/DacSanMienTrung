package com.poly.controller;

import com.poly.dao.AccountDAO;
import com.poly.dao.RoleDAO;
import com.poly.dao.SessionDAO;
import com.poly.entity.Account;
import com.poly.message.MESSAGE_CONSTANT;
import com.poly.validate.AccountValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("register")
public class RegisterController {
    MESSAGE_CONSTANT MESSAGE=new MESSAGE_CONSTANT();
    AccountValidate accountValidate=new AccountValidate();

    @Autowired
    AccountDAO accountDAO;

    @Autowired
    RoleDAO roleDAO;

    @Autowired
    SessionDAO session;

    @GetMapping("")
    public String showRegister(Model model,@ModelAttribute("user") Account user) {
        return "customer/register";
    }
    @PostMapping("")
    public String register(Model model,
                        @ModelAttribute("user") Account user) {
        if(session.get("user")!=null){
            return "redirect:/index";
        }
        boolean registerSuccess = true;
        List<String> listCheck=new ArrayList<>();
        listCheck.add(user.getUserName());
        listCheck.add(user.getPassWord());
        listCheck.add(user.getFullName());
        listCheck.add(user.getPhone());
        listCheck.add(user.getAddress());
        if (!accountValidate.listIsNullOrEmpty(listCheck)) {
            List<Account> list = accountDAO.findAll();
            for (Account account : list) {
                if (user.getUserName().equalsIgnoreCase(account.getUserName())) {
                    model.addAttribute("message", MESSAGE.USERNAME_EXIST);
                    registerSuccess = false;
                    return "customer/register";
                }else if (user.getEmail().equalsIgnoreCase(account.getEmail())) {
                    model.addAttribute("message", MESSAGE.EMAIL_EXIST);
                    registerSuccess = false;
                    return "customer/register";
                }
            }
        }
        if(registerSuccess) {
            Account account=new Account();
            account.setUserName(user.getUserName());
            account.setPassWord(user.getPassWord());
            account.setFullName(user.getFullName());
            account.setPhone(user.getPhone());
            account.setEmail(user.getEmail());
            account.setAddress(user.getAddress());
            account.setGender(user.getGender());
            account.setDateOfBirth(user.getDateOfBirth());
            account.setRoleId(3);
            accountDAO.save(account);
            return "redirect:/login";
        }else {
            return "redirect:/register";
        }

    }

}
