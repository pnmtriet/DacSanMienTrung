package com.poly.controller.admin;

import com.poly.common.Utils;
import com.poly.dao.OrderDAO;
import com.poly.dao.OrderDetailDAO;
import com.poly.entity.Order;
import com.poly.helper.OrderHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("admin/order")
public class OrderAdminController {
    @Autowired
    OrderDetailDAO orderDetailDAO;

    @Autowired
    OrderDAO orderDAO;

    @Autowired
    HttpServletRequest request;

    OrderHelper orderHelper=new OrderHelper();

    @GetMapping("")
    public String index(Model model,
                        @RequestParam("save") Optional<String> save,
                        @RequestParam("delete") Optional<String> delete,
                        @RequestParam("soTrang") Optional<String> soTrangString,
                        @RequestParam("soSanPham") Optional<String> soSanPhamString,
                        @RequestParam("txtSearch") Optional<String> txtSearch){
        if(!(request.isUserInRole("1") || request.isUserInRole("2"))) {
            return "redirect:/auth/access/denied";
        }
        int soTrang=!soTrangString.isPresent()?1:Integer.parseInt(soTrangString.get());
        int soSanPham=!soSanPhamString.isPresent()?6:Integer.parseInt(soSanPhamString.get());
        int tongSoTrang=orderHelper.getTotalPage(soSanPham, orderDAO.findAll());
        List<Order> listOrderByOrderId=new ArrayList<>();
        Pageable pageable = PageRequest.of(soTrang-1, soSanPham);
        List<Order> list=new ArrayList<>();
        if(txtSearch.isPresent()){
            if(Utils.checkIsNumber(txtSearch.get())){
                tongSoTrang=orderHelper.getTotalPage(soSanPham, listOrderByOrderId);
                listOrderByOrderId=orderDAO.findByOrderId(Integer.parseInt(txtSearch.get()));
                Page<Order> pageOrderByOrderId=orderDAO.findByOrderId(pageable,Integer.parseInt(txtSearch.get()));
                Page<Order> pageOrder=pageOrderByOrderId;
                list=pageOrder.getContent();
            }
        }else{
            Page<Order> pageOrder=orderDAO.findAll(pageable);
            list=pageOrder.getContent();
        }
        model.addAttribute("listOrder",list);
        if(soTrang<1){
            soTrang=1;
        }else if(soTrang>tongSoTrang){
            soTrang=tongSoTrang;
        }
        model.addAttribute("soTrangHienTai", soTrang);
        model.addAttribute("soSanPhamHienTai", soSanPham);
        model.addAttribute("tongSoTrang", tongSoTrang);
        if(save.isPresent()) {
            if(save.get().equals("true")){
                model.addAttribute("message","Lưu lại thành công!");
            }else{
                model.addAttribute("message","Lưu lại thất bại!");
            }
        }
        if(delete.isPresent()) {
            if(delete.get().equals("true")){
                model.addAttribute("message","Xóa thành công!");
            }else{
                model.addAttribute("message","Xóa thất bại!");
            }
        }
        return "admin/order";
    }

    @GetMapping("detail")
    public String detail(Model model,@RequestParam("orderId") Integer orderId){
        model.addAttribute("order", orderDAO.findById(orderId).get());
        model.addAttribute("orderDetail", orderDetailDAO.findAllByOrderId(orderId));
        return "admin/order-detail";
    }

    @PostMapping("save")
    public String save(@RequestParam("orderId") Optional<Integer> id,
                       @RequestParam("orderDate") Optional<String> orderDate,
                       @RequestParam("deliveryDate") Optional<String> deliveryDate,
                       @RequestParam("orderStatus") Optional<String> orderStatus,
                       @RequestParam("totalMoney") Optional<Integer> totalMoney,
                       @RequestParam("payment") Optional<String> payment,
                       @RequestParam("paymentStatus") Optional<String> paymentStatus,
                       @RequestParam("accountId") Optional<Integer> accountId,
                       @RequestParam("fullName") Optional<String> fullName,
                       @RequestParam("phone") Optional<String> phone,
                       @RequestParam("email") Optional<String> email,
                       @RequestParam("address") Optional<String> address,
                       @RequestParam("note") Optional<String> note,
                       HttpServletRequest req){
        if(!(request.isUserInRole("1") || request.isUserInRole("2"))) {
            return "redirect:/auth/access/denied";
        }
        try{
            Order order = new Order();
            order.setId(id.get());
            order.setOrderDate(Utils.converStringToDate(orderDate.get()));
            order.setOrderDate(Utils.converStringToDate(deliveryDate.get()));
            order.setOrderStatus(orderStatus.get());
            order.setTotalMoney(totalMoney.get());
            order.setPayment(payment.get());
            order.setPaymentStatus(paymentStatus.get());
            order.setAccountId(accountId.get());
            order.setFullname(fullName.get());
            order.setPhone(phone.get());
            order.setEmail(email.get());
            order.setAddress(address.get());
            order.setNote(note.get());
            return "redirect:/admin/order?save=true";
        }catch (Exception e){
            return "redirect:/admin/order?save=false";
        }

    }

    @GetMapping("delete")
    @Transactional
    public String delete(@RequestParam("orderId") Optional<String> orderId){
        try{
            Integer id=Integer.parseInt(orderId.get());
            orderDetailDAO.deleteByOrderId(id);
            orderDAO.deleteById(id);
            return "redirect:/admin/order?delete=true";
        }catch (Exception e){
            return "redirect:/admin/order?delete=false";
        }
    }
}
