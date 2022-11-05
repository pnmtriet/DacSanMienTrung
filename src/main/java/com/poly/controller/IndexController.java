package com.poly.controller;

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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class IndexController {
	@Autowired
	ProductDAO productDAO;

	@Autowired
	CategoryDAO categoryDAO;

	@Autowired
	ShoppingCartDAO shoppingCartDAO;

	@Autowired
	SessionDAO session;

	ProductHelper productHelper=new ProductHelper();
	@GetMapping("/index")
	public String index(Model model, @RequestParam Optional<String> message,
						@RequestParam("soTrang") Optional<String> soTrangString,
						@RequestParam("soSanPham") Optional<String> soSanPhamString,
						@RequestParam("txtSearch") Optional<String> txtSearch) {
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
		//Category
		List<Category> listCategory=categoryDAO.findAll();
		model.addAttribute("listCategory",listCategory);
		model.addAttribute("tongSoLuongGioHang",shoppingCartDAO.getCount());
		Account khachHang=(Account) session.get("user");
		if(khachHang!=null) {
			model.addAttribute("sessionUsername",khachHang.getUserName());
		}
		return "customer/index";
	}

}
