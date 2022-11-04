package com.poly.dao.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.poly.dao.ShoppingCartDAO;
import com.poly.entity.ShoppingCart;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;


@SessionScope
@Service
public class ShoppingCartImpl implements ShoppingCartDAO {
	Map<Integer, ShoppingCart> maps=new HashMap<>();
	
	@Override
	public void add(ShoppingCart item,Integer soLuong) {
		int productId=item.getId();
		ShoppingCart cartItem=maps.get(productId);//truy vấn giỏ hàng theo mã sp
		//Nếu chưa tồn tại
		if(cartItem==null) {
			maps.put(productId, item);
		}else {
			cartItem.setSoLuong(cartItem.getSoLuong()+soLuong);
		}
	}

	@Override
	public void remove(int id) {
		maps.remove(id);
	}
	@Override
	public ShoppingCart update(int productId, int soLuong) {
		ShoppingCart cartItem=maps.get(productId);
		cartItem.setSoLuong(soLuong);
		return cartItem;
	}
	@Override
	public void clear() {
		maps.clear();
	}
	@Override
	public Collection<ShoppingCart> getAll(){
		return maps.values();
	}
	@Override
	public int getCount() {
		return maps.values().stream()
				.mapToInt(item->item.getSoLuong()).sum();
	}

	@Override
	public int getTotalByProductId(int productId) {
		ShoppingCart cartItem=maps.get(productId);
		return cartItem.getSoLuong()*cartItem.getPrice();
	}

	@Override
	public int getAmout() {
		return maps.values().stream()
				.mapToInt(item -> item.getSoLuong()*item.getPrice())
				.sum();
	}
}
