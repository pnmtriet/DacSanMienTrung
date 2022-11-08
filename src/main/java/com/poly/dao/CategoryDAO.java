package com.poly.dao;

import com.poly.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryDAO extends JpaRepository<Category,Integer> {
    @Query("SELECT c FROM Category c WHERE c.categoryName like %?1%")
    List<Category> findByCategoryName(String categoryName);

    @Query("SELECT c FROM Category c WHERE c.categoryName like %?1%")
    Page<Category> findByCategoryNamePage(Pageable pageable, String categoryName);
}
