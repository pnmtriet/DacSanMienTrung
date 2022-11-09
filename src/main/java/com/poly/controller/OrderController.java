package com.poly.controller;

import com.poly.dao.OrderDAO;
import com.poly.dao.OrderDetailDAO;
import com.poly.dao.SessionDAO;
import com.poly.dao.ShoppingCartDAO;
import com.poly.entity.*;
import com.poly.helper.OrderHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("order")
public class OrderController {
    @Autowired
    OrderDAO orderDAO;
    @Autowired
    OrderDetailDAO orderDetailDAO;

    @Autowired
    SessionDAO session;

    @Autowired
    ShoppingCartDAO shoppingCartDAO;

    OrderHelper orderHelper=new OrderHelper();

    @PostMapping("/add")
    public String add(@ModelAttribute("user") Account user,
                      @RequestParam("payment-method") Optional<String> paymentMethod,
                      @RequestParam("note") Optional<String> note){
        Account sessionLogin=(Account) session.get("user");
        try{
            Order order=new Order();
            order.setOrderDate(new Date());
            order.setOrderStatus("Chờ xác nhận");
            order.setTotalMoney(shoppingCartDAO.getAmout()+15000);
            order.setNote(note.get());
            order.setDeliveryDate(orderHelper.getDeliveryDate());
            order.setPayment(paymentMethod.get());
            order.setFullname(user.getFullName());
            order.setEmail(user.getEmail());
            order.setPhone(user.getPhone());
            order.setAddress(user.getAddress());
            order.setAccount(sessionLogin);
            //Save đơn hàng
            Order orderSaved=orderDAO.save(order);
            Integer orderId=orderSaved.getId();
            //Chi tiết đơn hàng
            Collection<ShoppingCart> listShoppingCart=shoppingCartDAO.getAll();
            for(ShoppingCart item:listShoppingCart){
                OrdersDetail ordersDetail=new OrdersDetail();
                ordersDetail.setOrderId(orderId);
                ordersDetail.setProductId(item.getId());
                ordersDetail.setAmount(item.getSoLuong());
                ordersDetail.setPrice(item.getPrice());
                //Save chi tiết đơn hàng
                orderDetailDAO.save(ordersDetail);
            }
            shoppingCartDAO.clear();
            return "redirect:/shopping-cart?orderId="+orderId+"&orderSaved=true";
        }catch (Exception e){
            return "redirect:/shopping-cart?orderSaved=false";
        }
    }
}
