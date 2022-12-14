package com.poly.dao;

import com.poly.entity.OrdersDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderDetailDAO extends JpaRepository<OrdersDetail,Integer> {
    @Modifying
    @Query("DELETE FROM OrdersDetail p WHERE p.productId=?1")
    void deleteByProductId(Integer productId);

    @Modifying
    @Query("DELETE FROM OrdersDetail p WHERE p.orderId=?1")
    void deleteByOrderId(Integer orderId);

    @Query("SELECT od FROM OrdersDetail od WHERE od.orderId=?1")
    List<OrdersDetail> findAllByOrderId(Integer orderId);
}
