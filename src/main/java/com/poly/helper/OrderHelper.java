package com.poly.helper;

import com.poly.entity.Order;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class OrderHelper {
    public Date getDeliveryDate(){
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, 5);
        dt = c.getTime();
        return dt;
    }
    public int getTotalPage(int soSanPham, List<Order> list) {
        int tongSoSanPham = list.size();
        int tongSoTrang = 1;
        float tempFloat = (float) tongSoSanPham / soSanPham;
        int tempInt = (int) tempFloat;
        if (tempFloat - tempInt > 0) {
            tongSoTrang = tempInt + 1;
        } else {
            tongSoTrang = tempInt;
        }
        return tongSoTrang;
    }
}
