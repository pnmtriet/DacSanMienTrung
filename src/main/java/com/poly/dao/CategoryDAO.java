package com.poly.dao;

import com.poly.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface CategoryDAO extends JpaRepository<Category,Integer> {
}
