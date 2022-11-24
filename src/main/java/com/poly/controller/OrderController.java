package com.poly.controller;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.poly.common.Utils;
import com.poly.dao.*;
import com.poly.entity.*;
import com.poly.enums.PaypalPaymentIntent;
import com.poly.enums.PaypalPaymentMethod;
import com.poly.helper.OrderHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("order")
public class OrderController {
    private Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private PaypalService paypalService;
    @Autowired
    OrderDAO orderDAO;
    @Autowired
    OrderDetailDAO orderDetailDAO;

    @Autowired
    SessionDAO session;

    @Autowired
    ShoppingCartDAO shoppingCartDAO;

    String URL_PAYPAL = "pay/status";

    OrderHelper orderHelper=new OrderHelper();

    @PostMapping("/add")
    public String add(HttpServletRequest request,
                      @ModelAttribute("user") Account user,
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
            order.setPaymentStatus("Chưa thanh toán");
            order.setAddress(user.getAddress());
            order.setAccountId(sessionLogin.getId());
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

            if (paymentMethod.get().equalsIgnoreCase("Paypal")) {
                String urlReturn = Utils.getBaseURL(request) + "/order/pay/status?orderId=" + orderId;
                Double total = Utils.round(orderSaved.getTotalMoney() / 24000, 2);
                Payment payment = paypalService.createPayment(
                        total,
                        "USD",
                        PaypalPaymentMethod.paypal,
                        PaypalPaymentIntent.sale,
                        "payment description",
                        urlReturn,
                        urlReturn);
                List<Links> list = payment.getLinks();
                for (Links links : payment.getLinks()) {
                    if (links.getRel().equals("approval_url")) {
                        return "redirect:" + links.getHref();
                    }
                }
            }
            return "redirect:/order/pay/status?orderId="+orderId;
        }catch (Exception e){
            return "redirect:/shopping-cart?orderSaved=false";
        }
    }
    @GetMapping("pay/status")
    public String successPay(Model model,
                             @RequestParam("paymentId") Optional<String> paymentId,
                             @RequestParam("PayerID") Optional<String> payerId,
                             @RequestParam Integer orderId) {
        Account sessionLogin = (Account) session.get("user");
        if(sessionLogin==null){
            return "redirect:/login?error=errorNoLogin&urlReturn=order/pay/status?orderId="+orderId;
        }else if(orderDAO.existByAccountIdAndOrderId(sessionLogin.getId(),orderId)==null){
            return "customer/404";
        }
        try {
            if (payerId.isPresent() && payerId.isPresent()) {
                Payment payment = paypalService.executePayment(paymentId.get(), payerId.get());
                if (payment.getState().equals("approved")) {
                    Order order = orderDAO.findById(orderId).get();
                    order.setPaymentStatus("Đã thanh toán");
                    orderDAO.save(order);
                }
            }
            model.addAttribute("order", orderDAO.findById(orderId).get());
            model.addAttribute("orderDetail", orderDetailDAO.findAllByOrderId(orderId));
            return "customer/status";
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
            return "redirect:/index";
        }
    }
}
