package com.poly.controller;

import com.poly.dao.AccountDAO;
import com.poly.dao.MailerService;
import com.poly.entity.Account;
import com.poly.entity.MailInfo;
import com.poly.helper.MailerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("forgotPassword")
public class ForgotPasswordController {
	Account account=new Account();
	String codeRandom="";
	@Autowired
	MailerService mailer;
	@Autowired
	AccountDAO accountDAO;
    @GetMapping("")
    public String index(Model model,
						@RequestParam Optional<String> message,
						@RequestParam Optional<String> messageCode,
						@RequestParam Optional<String> email) {
        if (message.isPresent()) {
			if(message.get().equals("true")){
				model.addAttribute("success","Mã xác thực đã được gửi đến email của bạn");
				model.addAttribute("showCode",true);
			}else {
				model.addAttribute("error","Địa chỉ email chưa chính xác");
				model.addAttribute("showCode",false);
			}
		}
		if(messageCode.isPresent()){
			if(messageCode.get().equals("true")){
				model.addAttribute("success","Thông tin tài khoản đã được gửi đến email của bạn");
				model.addAttribute("showCode",true);
			}else {
				model.addAttribute("error","Mã xác thực không chính xác, hãy thử lại");
				model.addAttribute("showCode",true);
			}

		}
		if(email.isPresent()){
			model.addAttribute("emailShow",email.get());
			model.addAttribute("showCode",true);
		}
        return "customer/forgotPassword";
    }
	@PostMapping("/send-code")
	public String send(@RequestParam Optional<String> email) throws IOException, MessagingException {
		if(email.isPresent()){
			account=accountDAO.findByEmailOrPhone(email.get());
			if(account==null){
				return "redirect:/forgotPassword?message=false";
			}
			MailerHelper helper=new MailerHelper();
			codeRandom=helper.randomAlphaNumeric(6);
			MailInfo mail = new MailInfo();
			mail.setTo(email.get());
			mail.setSubject("Lấy lại mật khẩu");
			String body= helper.htmlCode(codeRandom);
			mail.setBody(body);
			//Gửi mail
			mailer.queue(mail);
			return "redirect:/forgotPassword?message=true&email="+email.get();
		}else{
			return "redirect:/forgotPassword?message=false&email="+email.get();
		}
	}
	@PostMapping("/verify")
	public String verify(@RequestParam Optional<String> code) throws IOException, MessagingException {
		if(code.isPresent()){
			if(code.get().equals(codeRandom)){
				MailerHelper helper=new MailerHelper();
				MailInfo mail = new MailInfo();
				mail.setTo(account.getEmail());
				mail.setSubject("["+account.getFullName()+"] Thông tin tài khoản");
				String body=helper.htmlMail(account.getUserName(),account.getPassWord());
				mail.setBody(body);
				//Gửi mail
				mailer.queue(mail);
				return "redirect:/forgotPassword?messageCode=true&email="+account.getEmail();
			}
		}
		return "redirect:/forgotPassword?messageCode=false&email="+account.getEmail();
	}
}
