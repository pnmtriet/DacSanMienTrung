package com.poly.controller.admin;


import com.poly.dao.CategoryDAO;
import com.poly.dao.ProductDAO;
import com.poly.entity.Category;
import com.poly.helper.CategoryHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/category")
public class CategoriesAdminController {
    @Autowired
    HttpServletRequest request;
    @Autowired
    CategoryDAO categoryDAO;

    @Autowired
    ProductDAO productDAO;

    CategoryHelper categoryHelper = new CategoryHelper();

    @GetMapping("")
    public String index(Model model,
                        @RequestParam Optional<String> message,
                        @RequestParam("soTrang") Optional<String> soTrangString,
                        @RequestParam("soSanPham") Optional<String> soSanPhamString,
                        @RequestParam("txtSearch") Optional<String> txtSearch) {
        if(!(request.isUserInRole("1") || request.isUserInRole("2"))) {
            return "redirect:/auth/access/denied";
        }
        int soTrang = !soTrangString.isPresent() ? 1 : Integer.parseInt(soTrangString.get());
        int soSanPham = !soSanPhamString.isPresent() ? 6 : Integer.parseInt(soSanPhamString.get());
        int tongSoTrang = txtSearch.isPresent()
                ? categoryHelper.getTotalPage(soSanPham, categoryDAO.findByCategoryName(txtSearch.get()))
                : categoryHelper.getTotalPage(soSanPham, categoryDAO.findAll());
        if (soTrang < 1) {
            soTrang = 1;
        } else if (soTrang > tongSoTrang) {
            soTrang = tongSoTrang;
        }
        model.addAttribute("soTrangHienTai", soTrang);
        model.addAttribute("soSanPhamHienTai", soSanPham);
        model.addAttribute("tongSoTrang", tongSoTrang);
        Pageable pageable = PageRequest.of(soTrang - 1, soSanPham);
        Page<Category> pageCategory = txtSearch.isPresent()
                ? categoryDAO.findByCategoryNamePage(pageable, txtSearch.get())
                : categoryDAO.findAll(pageable);
        List<Category> list = pageCategory.getContent();
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
        model.addAttribute("listCategory", list);

        return "admin/category";
    }

    @PostMapping("save")
    public String update(@RequestParam("categoryName") Optional<String> categoryName,
                         @RequestParam("categoryId") Optional<String> categoryId) {
        if(!(request.isUserInRole("1") || request.isUserInRole("2"))) {
            return "redirect:/auth/access/denied";
        }
        Category category = new Category();
        if (categoryId.isPresent()) {
            category.setId(Integer.parseInt(categoryId.get()));
        }
        if (categoryName.isPresent()) {
            category.setCategoryName(categoryName.get());
            categoryDAO.save(category);
        }
        return "redirect:/admin/category?message=saved";
    }

    @GetMapping("delete")
    @Transactional
    public String delete(@RequestParam("categoryId") Optional<String> categoryId) {
        if(!(request.isUserInRole("1") || request.isUserInRole("2"))) {
            return "redirect:/auth/access/denied";
        }
        try {
            Integer id = Integer.parseInt(categoryId.get());
            productDAO.deleteByCategoryId(id);
            categoryDAO.deleteById(id);
            return "redirect:/admin/category?message=deleted";
        } catch (Exception e) {
            return "redirect:/admin/category?message=deleteFail";
        }
    }
}
