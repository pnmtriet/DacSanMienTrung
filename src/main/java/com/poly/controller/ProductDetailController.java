package com.poly.controller;

import com.poly.dao.CategoryDAO;
import com.poly.dao.ProductDAO;
import com.poly.dao.SessionDAO;
import com.poly.dao.ShoppingCartDAO;
import com.poly.entity.Account;
import com.poly.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("single-product")
public class ProductDetailController {
    @Autowired
    ProductDAO productDAO;

    @Autowired
    ShoppingCartDAO shoppingCartDAO;

    @Autowired
    SessionDAO session;

    @GetMapping("")
    public String index(Model model, @RequestParam("id") Optional<String> productIdParam){
        if(productIdParam.isPresent()){
            int productId=Integer.parseInt(productIdParam.get());
            Product product=productDAO.findById(productId).get();
            model.addAttribute("product",product);
            model.addAttribute("category",product.getCategory());
            model.addAttribute("tongSoLuongGioHang",shoppingCartDAO.getCount());
            Account khachHang=(Account) session.get("user");
            if(khachHang!=null) {
                model.addAttribute("sessionUsername",khachHang.getUserName());
            }
        }
        return "customer/single-product";
    }
}
