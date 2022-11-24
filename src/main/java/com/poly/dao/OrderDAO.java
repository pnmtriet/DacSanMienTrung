package com.poly.dao;

import com.poly.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderDAO extends JpaRepository<Order,Integer> {

    @Query("SELECT o FROM Order o WHERE o.accountId=?1")
    List<Order> findByAccountId(Integer accountId);

    @Query("SELECT o FROM Order o WHERE o.accountId=?1 AND o.id=?2")
    Order existByAccountIdAndOrderId(Integer accountId, Integer orderId);

    @Query("SELECT o FROM Order o WHERE o.id=?1")
    List<Order> findByOrderId(Integer orderId);
    @Query("SELECT o FROM Order o WHERE o.id=?1")
    Page<Order> findByOrderId(Pageable page, Integer orderId);
}
