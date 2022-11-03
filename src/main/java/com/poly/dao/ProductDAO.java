package com.poly.dao;

import com.poly.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface ProductDAO extends JpaRepository<Product, Integer> {

}
