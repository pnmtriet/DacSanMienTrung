package com.poly.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static Date converStringToDate(String dateString) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dateResult = (Date)formatter.parse(dateString);
        return dateResult;
    }
}
