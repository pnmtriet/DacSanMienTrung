package com.poly.dao;

import java.util.Collection;
import com.poly.entity.ShoppingCart;

public interface ShoppingCartDAO {
	public void add(ShoppingCart item,Integer soLuong);
	public void remove(int id);
	public int getAmout();
	public int getCount();
	public int getTotalByProductId(int productId);
	public Collection<ShoppingCart> getAll();
	public void clear();
	public ShoppingCart update(int productId, int soLuong);
}
