package com.poly.controller;

import com.poly.dao.ProductDAO;
import com.poly.entity.Product;
import com.poly.helper.ProductHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class indexController {
	@Autowired
	ProductDAO productDAO;

	ProductHelper productHelper=new ProductHelper();
	@RequestMapping("/index")
	public String index(Model model, @RequestParam Optional<String> message,
						@RequestParam("soTrang") Optional<String> soTrangString,
						@RequestParam("soSanPham") Optional<String> soSanPhamString) {
		int soTrang=soTrangString.isEmpty()?1:Integer.parseInt(soTrangString.get());
		model.addAttribute("soTrangHienTai", soTrang);
		int soSanPham=soTrangString.isEmpty()?6:Integer.parseInt(soSanPhamString.get());
		model.addAttribute("soSanPhamHienTai", soSanPham);
		int tongSoTrang=productHelper.getTotalPage(soSanPham, productDAO.findAll());
		model.addAttribute("tongSoTrang", tongSoTrang);
		Pageable pageable = PageRequest.of(soTrang-1, soSanPham);
		Page<Product> pageProduct=productDAO.findAll(pageable);
		List<Product> list=pageProduct.getContent();
		if(message.isPresent()) {
			model.addAttribute("message",message.get());
		}
		model.addAttribute("listGiay",list);
		return "customer/index";
	}

}