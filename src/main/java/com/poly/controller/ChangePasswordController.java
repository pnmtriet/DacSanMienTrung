package com.poly.controller;

import com.poly.dao.AccountDAO;
import com.poly.dao.SessionDAO;
import com.poly.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("changePassword")
public class ChangePasswordController {
	@Autowired
	SessionDAO session;

	@Autowired
	AccountDAO accountDAO;
    @GetMapping("")
    public String index(Model model,
						@RequestParam Optional<String> password,
						@RequestParam Optional<String> rePassword) {
		if(password.isPresent()){
			if(password.get().equals("true")){
				model.addAttribute("success","Đổi mật khẩu thành công!");
			}
		}
		if(rePassword.isPresent()){
			if(rePassword.get().equals("1")){
				model.addAttribute("error","Nhập lại mật khẩu không đúng!");
			}
		}
        return "customer/changePassword";
    }
	@PostMapping("")
	public String changePassword(@RequestParam Optional<String> newPassword,
								 @RequestParam Optional<String> confirmPassword){
		if(!newPassword.isPresent()){
			//Lỗi 0 là lỗi chưa điền newPassword
			return "redirect:/changePassword?password=0";
		}
		if(!confirmPassword.isPresent()){
			//Lỗi 0 là lỗi chưa điền rePassword
			return "redirect:/changePassword?rePassword=0";
		}
		if(!newPassword.get().equals(confirmPassword.get())){
			//Lỗi 1 là lỗi điền sai rePassword
			return "redirect:/changePassword?rePassword=1";
		}
		Account account=(Account) session.get("user");
		account.setPassWord(newPassword.get());
		accountDAO.save(account);
		return "redirect:/changePassword?password=true";

	}
}
