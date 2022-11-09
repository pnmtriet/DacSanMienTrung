package com.poly.dao;

import com.poly.entity.OrdersDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

@Service
public interface OrderDetailDAO extends JpaRepository<OrdersDetail,Integer> {
    @Modifying
    @Query("DELETE FROM OrdersDetail p WHERE p.productId=?1")
    void deleteByProductId(Integer productId);
}
