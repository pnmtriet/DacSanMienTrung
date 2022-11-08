package com.poly.controller.admin;

import com.poly.dao.CategoryDAO;
import com.poly.dao.ProductDAO;
import com.poly.dao.SessionDAO;
import com.poly.dao.ShoppingCartDAO;
import com.poly.entity.Account;
import com.poly.entity.Category;
import com.poly.entity.Product;
import com.poly.helper.ProductHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/product")
public class ProductAdminController {
    @Autowired
    HttpServletRequest request;
    @Autowired
    ProductDAO productDAO;

    ProductHelper productHelper=new ProductHelper();
    @GetMapping("")
    public String index(Model model, @RequestParam Optional<String> message,
                        @RequestParam("soTrang") Optional<String> soTrangString,
                        @RequestParam("soSanPham") Optional<String> soSanPhamString,
                        @RequestParam("txtSearch") Optional<String> txtSearch) {
        if(!(request.isUserInRole("1") || request.isUserInRole("2"))) {
            return "redirect:/auth/access/denied";
        }
        int soTrang=!soTrangString.isPresent()?1:Integer.parseInt(soTrangString.get());
        int soSanPham=!soSanPhamString.isPresent()?6:Integer.parseInt(soSanPhamString.get());
        int tongSoTrang=txtSearch.isPresent()
                ?productHelper.getTotalPage(soSanPham, productDAO.findByProductName(txtSearch.get()))
                :productHelper.getTotalPage(soSanPham, productDAO.findAll());
        if(soTrang<1){
            soTrang=1;
        }else if(soTrang>tongSoTrang){
            soTrang=tongSoTrang;
        }
        model.addAttribute("soTrangHienTai", soTrang);
        model.addAttribute("soSanPhamHienTai", soSanPham);
        model.addAttribute("tongSoTrang", tongSoTrang);
        Pageable pageable = PageRequest.of(soTrang-1, soSanPham);
        Page<Product> pageProduct=txtSearch.isPresent()
                ?productDAO.findByProductNamePage(pageable,txtSearch.get())
                :productDAO.findAll(pageable);
        List<Product> list=pageProduct.getContent();
        if(message.isPresent()) {
            model.addAttribute("message",message.get());
        }
        model.addAttribute("listProduct",list);
        return "admin/product";
    }
}
