package com.poly.helper;

import com.poly.entity.Category;
import com.poly.entity.Product;

import java.util.List;

public class CategoryHelper {
    public int getTotalPage(int soSanPham, List<Category> list) {
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
