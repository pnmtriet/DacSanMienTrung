package com.poly.dao;

import com.poly.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface OrderDAO extends JpaRepository<Order,Integer> {
}
