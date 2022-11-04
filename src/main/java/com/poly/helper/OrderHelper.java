package com.poly.helper;

import java.util.Calendar;
import java.util.Date;

public class OrderHelper {
    public Date getDeliveryDate(){
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, 5);
        dt = c.getTime();
        return dt;
    }
}
