package com.poly.dao;

import com.poly.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductDAO extends JpaRepository<Product, Integer> {
    @Query("SELECT p FROM Product p WHERE p.productName like %?1%")
    List<Product> findByProductName(String productName);

    @Query("SELECT p FROM Product p WHERE p.productName like %?1%")
    Page<Product> findByProductNamePage(Pageable pageable, String productName);

    @Modifying
    @Query("DELETE FROM Product p WHERE p.categoryId=?1")
    void deleteByCategoryId(Integer categoryId);
}
