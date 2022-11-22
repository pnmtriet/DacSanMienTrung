package com.poly.controller.admin;

import com.poly.common.Utils;
import com.poly.dao.AccountDAO;
import com.poly.dao.OrderDAO;
import com.poly.dao.OrderDetailDAO;
import com.poly.dao.RoleDAO;
import com.poly.entity.Account;
import com.poly.entity.Order;
import com.poly.helper.AccountHelper;
import com.poly.message.MESSAGE_CONSTANT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("admin/account")
public class AccountAdminController {
    MESSAGE_CONSTANT MESSAGE=new MESSAGE_CONSTANT();
    @Autowired
    AccountDAO accountDAO;

    @Autowired
    RoleDAO roleDAO;

    @Autowired
    OrderDetailDAO orderDetailDAO;

    @Autowired
    OrderDAO orderDAO;

    @Autowired
    HttpServletRequest request;

    AccountHelper accountHelper = new AccountHelper();

    @GetMapping("")
    public String index(Model model,
                        @RequestParam Optional<String> message,
                        @RequestParam Optional<String> error,
                        @RequestParam("soTrang") Optional<String> soTrangString,
                        @RequestParam("soSanPham") Optional<String> soSanPhamString,
                        @RequestParam("txtSearch") Optional<String> txtSearch) {
        if (!(request.isUserInRole("1") || request.isUserInRole("2"))) {
            return "redirect:/auth/access/denied";
        }
        int soTrang = !soTrangString.isPresent() ? 1 : Integer.parseInt(soTrangString.get());
        int soSanPham = !soSanPhamString.isPresent() ? 6 : Integer.parseInt(soSanPhamString.get());
        int tongSoTrang = txtSearch.isPresent()
                ? accountHelper.getTotalPage(soSanPham, accountDAO.findBUsernameOrFullName(txtSearch.get()))
                : accountHelper.getTotalPage(soSanPham, accountDAO.findAll());
        if (soTrang < 1) {
            soTrang = 1;
        } else if (soTrang > tongSoTrang) {
            soTrang = tongSoTrang;
        }
        model.addAttribute("soTrangHienTai", soTrang);
        model.addAttribute("soSanPhamHienTai", soSanPham);
        model.addAttribute("tongSoTrang", tongSoTrang);
        Pageable pageable = PageRequest.of(soTrang - 1, soSanPham);
        Page<Account> pageAccount = txtSearch.isPresent()
                ? accountDAO.findBUsernameOrFullName(pageable, txtSearch.get())
                : accountDAO.findAll(pageable);
        List<Account> list = pageAccount.getContent();
        if (message.isPresent()) {
            if (message.get().equals("saved")) {
                model.addAttribute("message", "Lưu thành công!");
            }
            if (message.get().equals("deleted")) {
                model.addAttribute("message", "Xóa thành công!");
            }
            if (message.get().equals("deleteFail")) {
                model.addAttribute("message", "Xóa thất bại!");
            }
        }
        if (error.isPresent()) {
            if (error.get().equals("accountExist")) {
                model.addAttribute("error", MESSAGE.USERNAME_EXIST);
            }
            if (error.get().equals("emailExist")) {
                model.addAttribute("error", MESSAGE.EMAIL_EXIST);
            }
        }
        model.addAttribute("listAccount", list);
        model.addAttribute("listRole", roleDAO.findAll());

        return "admin/account";
    }

    @PostMapping("add")
    public String addAccount(@RequestParam Optional<Integer> id,
                              @RequestParam String username,
                              @RequestParam String password,
                              @RequestParam String fullName,
                              @RequestParam String phone,
                              @RequestParam String email,
                              @RequestParam String address,
                              @RequestParam Boolean gender,
                              @RequestParam String dateOfBirth,
                              @RequestParam Integer roleId) throws ParseException {
        List<Account> list = accountDAO.findAll();
        for (Account account : list) {
            if (username.equalsIgnoreCase(account.getUserName())) {
                return "redirect:/admin/account?error=accountExist";
            } else if (email.equalsIgnoreCase(account.getEmail())) {
                return "redirect:/admin/account?error=emailExist";
            }
        }
        Account account = new Account();
        account.setId(id.isPresent() ? id.get() : null);
        account.setUserName(username);
        account.setPhone(phone);
        account.setPassWord(password);
        account.setFullName(fullName);
        account.setEmail(email);
        account.setAddress(address);
        account.setGender(gender);
        account.setDateOfBirth(Utils.converStringToDate(dateOfBirth));
        account.setRoleId(roleId);
        accountDAO.save(account);
        return "redirect:/admin/account?message=saved";
    }
    @PostMapping("save")
    public String saveAccount(@RequestParam Optional<Integer> id,
                              @RequestParam String username,
                              @RequestParam String password,
                              @RequestParam String fullName,
                              @RequestParam String phone,
                              @RequestParam String email,
                              @RequestParam String address,
                              @RequestParam Boolean gender,
                              @RequestParam String dateOfBirth,
                              @RequestParam Integer roleId) throws ParseException {
        List<Account> list = accountDAO.findAll();
        for (Account account : list) {
            if (email.equalsIgnoreCase(account.getEmail())) {
                return "redirect:/admin/account?error=emailExist";
            }
        }
        Account account = new Account();
        account.setId(id.isPresent() ? id.get() : null);
        account.setUserName(username);
        account.setPhone(phone);
        account.setPassWord(password);
        account.setFullName(fullName);
        account.setEmail(email);
        account.setAddress(address);
        account.setGender(gender);
        account.setDateOfBirth(Utils.converStringToDate(dateOfBirth));
        account.setRoleId(roleId);
        accountDAO.save(account);
        return "redirect:/admin/account?message=saved";
    }

    @GetMapping("delete")
    @Transactional
    public String delete(@RequestParam("accountId") Optional<String> accountId) {
        try {
            Integer id = Integer.parseInt(accountId.get());
            List<Order> listOrder = orderDAO.findByAccountId(id);
            for (Order order : listOrder) {
                orderDetailDAO.deleteByOrderId(order.getId());
                orderDAO.deleteById(order.getId());
            }
            accountDAO.deleteById(id);
            return "redirect:/admin/account?message=deleted";
        } catch (Exception e) {
            return "redirect:/admin/account?message=deleteFail";
        }
    }
}
