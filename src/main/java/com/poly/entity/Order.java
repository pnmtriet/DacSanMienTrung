package com.poly.entity;

import org.hibernate.annotations.Nationalized;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "order_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date orderDate;

    @Nationalized
    @Column(name = "note", length = 300)
    private String note;

    @Nationalized
    @Column(name = "order_status", length = 20)
    private String orderStatus;

    @Column(name = "total_money")
    private int totalMoney;

    @Column(name = "delivery_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deliveryDate;

    @Column(name = "payment", length = 50)
    private String payment;

    @Nationalized
    @Column(name = "fullname", length = 50)
    private String fullname;

    @Nationalized
    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "email", length = 50)
    private String email;

    @Nationalized
    @Column(name = "address", length = 300)
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }


    public int getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(int totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}