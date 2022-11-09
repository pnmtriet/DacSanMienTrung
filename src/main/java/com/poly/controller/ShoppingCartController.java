package com.poly.controller;

import java.util.Collection;
import java.util.Optional;

import com.poly.dao.ProductDAO;
import com.poly.dao.SessionDAO;
import com.poly.dao.ShoppingCartDAO;
import com.poly.entity.Account;
import com.poly.entity.Product;
import com.poly.entity.ShoppingCart;
import com.poly.validate.ProductValidate;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("shopping-cart")
public class ShoppingCartController {
	@Autowired
	ProductDAO productDAO;
	@Autowired
	ShoppingCartDAO shoppingCartDAO;

	@Autowired
	SessionDAO session;
	
	@GetMapping({"","views"})
	public String viewCart(Model model,
						   @RequestParam("orderSaved") Optional<String> orderSaved,
						   @RequestParam("orderId") Optional<String> orderId,
						   @RequestParam("error") Optional<String> error,
						   @ModelAttribute("user") Account user) {
		if(orderSaved.isPresent()){
			if(orderSaved.get().equals("true") && orderId.isPresent()){
				model.addAttribute("orderSaved",true);
				model.addAttribute("orderIdSaved",orderId.get());
			}
		}
		if(error.isPresent()){
			model.addAttribute("error","Đặt hàng thất bại");
		}
		Account khachHang=(Account) session.get("user");
		if(khachHang!=null) {
			model.addAttribute("sessionUsername",khachHang.getUserName());
			model.addAttribute("user",khachHang);
		}
		Collection<ShoppingCart> listGioHang = shoppingCartDAO.getAll();
		model.addAttribute("listGioHang",listGioHang);
		model.addAttribute("tongTienGioHang",shoppingCartDAO.getAmout());
		model.addAttribute("tongSoLuongGioHang",shoppingCartDAO.getCount());
		return "customer/Cart";
	}
	@PostMapping("addToCart")
	public ResponseEntity<String> addToCart(@RequestParam String maSanPham,@RequestParam Integer soLuong) throws JSONException {
		ShoppingCart cartItem=new ShoppingCart();
		Product product=productDAO.findById(Integer.parseInt(maSanPham)).get();
		cartItem.setId(product.getId());
		cartItem.setPrice(product.getPrice());
		cartItem.setDiscount(product.getDiscount());
		cartItem.setImages(product.getImages());
		cartItem.setBrand(product.getBrand());
		cartItem.setCategory(product.getCategory());
		cartItem.setNote(product.getNote());
		cartItem.setProductName(product.getProductName());
		cartItem.setUnit(product.getUnit());
		cartItem.setNumberOfSale(product.getNumberOfSale());
		cartItem.setSoLuong(soLuong);
		shoppingCartDAO.add(cartItem,soLuong);
		int tongSoLuongGioHang = shoppingCartDAO.getCount();
		JSONObject json = new JSONObject();
		json.put("soLuong", tongSoLuongGioHang);
		return ResponseEntity.ok(String.valueOf(json));
	}
	@PostMapping("changeCount")
	public ResponseEntity<String> editCart(@RequestParam String maSanPham,@RequestParam String soLuongSanPham) throws JSONException {
		ShoppingCart cartItem=shoppingCartDAO.update(Integer.parseInt(maSanPham),Integer.parseInt(soLuongSanPham));
		int tongTien = shoppingCartDAO.getAmout();
		int tongSoLuongGioHang = shoppingCartDAO.getCount();
		int tongTienItem=shoppingCartDAO.getTotalByProductId(Integer.parseInt(maSanPham));
		JSONObject json = new JSONObject();
		json.put("soLuong", tongSoLuongGioHang);
		json.put("tongTien", tongTien);
		json.put("tongTienItem",tongTienItem);
		return ResponseEntity.ok(String.valueOf(json));
	}
	@PostMapping("deleteItem")
	public ResponseEntity<String> deleteItem(@RequestParam String maSanPham) throws JSONException {
		if(new ProductValidate().checkIDProduct(maSanPham)) {
			shoppingCartDAO.remove(Integer.parseInt(maSanPham));
			int tongTien = shoppingCartDAO.getAmout();
			int tongSoLuongGioHang = shoppingCartDAO.getCount();
			JSONObject json = new JSONObject();
			json.put("soLuong", tongSoLuongGioHang);
			json.put("tongTien", tongTien);
			return ResponseEntity.ok(String.valueOf(json));
		}else {
			return ResponseEntity.ok("fail");
		}
	}
	@GetMapping("deleteAllItem")
	public String deleteAllItem(Model model) {
		shoppingCartDAO.clear();
		return "redirect:/shopping-cart";
	}
}
