package com.poly.controller.admin;

import com.poly.dao.*;
import com.poly.entity.Brand;
import com.poly.entity.Category;
import com.poly.entity.Product;
import com.poly.helper.ProductHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/product")
public class ProductAdminController {
    @Autowired
    HttpServletRequest request;
    @Autowired
    ProductDAO productDAO;

    @Autowired
    CategoryDAO categoryDAO;

    @Autowired
    BrandDAO brandDAO;

    @Autowired
    OrderDetailDAO orderDetailDAO;

    ProductHelper productHelper=new ProductHelper();
    @GetMapping("")
    public String index(Model model,
                        @RequestParam Optional<String> delete,
                        @RequestParam Optional<String> save,
                        @RequestParam("soTrang") Optional<String> soTrangString,
                        @RequestParam("soSanPham") Optional<String> soSanPhamString,
                        @RequestParam("txtSearch") Optional<String> txtSearch) {
        if(!(request.isUserInRole("1") || request.isUserInRole("2"))) {
            return "redirect:/auth/access/denied";
        }
        int soTrang=!soTrangString.isPresent()?1:Integer.parseInt(soTrangString.get());
        int soSanPham=!soSanPhamString.isPresent()?6:Integer.parseInt(soSanPhamString.get());
        int tongSoTrang=txtSearch.isPresent()
                ?productHelper.getTotalPage(soSanPham, productDAO.findByProductName(txtSearch.get()))
                :productHelper.getTotalPage(soSanPham, productDAO.findAll());
        if(soTrang<1){
            soTrang=1;
        }else if(soTrang>tongSoTrang){
            soTrang=tongSoTrang;
        }
        model.addAttribute("soTrangHienTai", soTrang);
        model.addAttribute("soSanPhamHienTai", soSanPham);
        model.addAttribute("tongSoTrang", tongSoTrang);
        Pageable pageable = PageRequest.of(soTrang-1, soSanPham);
        Page<Product> pageProduct=txtSearch.isPresent()
                ?productDAO.findByProductNamePage(pageable,txtSearch.get())
                :productDAO.findAll(pageable);
        List<Product> list=pageProduct.getContent();
        if(save.isPresent()) {
            if(save.get().equals("true")){
                model.addAttribute("message","Lưu lại thành công!");
            }else{
                model.addAttribute("message","Lưu lại thất bại! (Bạn cần chính xác thông tin sản phẩm)");
            }
        }
        if(delete.isPresent()) {
            if(delete.get().equals("true")){
                model.addAttribute("message","Xóa thành công!");
            }else{
                model.addAttribute("message","Xóa thất bại!");
            }
        }
        model.addAttribute("listProduct",list);
        //Category
        List<Category> listCategory=categoryDAO.findAll();
        model.addAttribute("listCategory",listCategory);
        //Brand
        List<Brand> listBrand=brandDAO.findAll();
        model.addAttribute("listBrand",listBrand);
        return "admin/product";
    }

    @PostMapping("save")
    public String save(@RequestParam("id") Optional<String> id,
                       @RequestParam("productName") Optional<String> productName,
                       @RequestParam("price") Optional<String> price,
                       @RequestParam("discount") Optional<String> discount,
                       @RequestParam("note") Optional<String> note,
                       @RequestParam("unit") Optional<String> unit,
                       @RequestParam("categoryId") Optional<String> categoryId,
                       @RequestParam("brandId") Optional<String> brandId,
                       @RequestParam("images") MultipartFile fileImages,
                       @RequestParam("imagesOld") Optional<String> imagesOld,HttpServletRequest req){
        try{
            //Lưu file vào thư mục của project
            productHelper.save(fileImages);
            // Upload file lên server tomcat
            String imagesNameSaved= productHelper.uploadImage(req);
            Product product=new Product();
            product.setId(id.isPresent()?Integer.parseInt(id.get()):null);
            product.setProductName(productName.isPresent()?productName.get():null);
            product.setPrice(price.isPresent()?Integer.parseInt(price.get()):null);
            product.setDiscount(discount.isPresent()?Integer.parseInt(discount.get()):null);
            product.setNote(note.isPresent()?note.get():null);
            product.setUnit(unit.isPresent()?unit.get():null);
            product.setNumberOfSale(0);
            product.setCategoryId(categoryId.isPresent()?Integer.parseInt(categoryId.get()):null);
            product.setBrandId(brandId.isPresent()?Integer.parseInt(brandId.get()):null);
            product.setImages(imagesNameSaved==null || imagesNameSaved.isEmpty()?imagesOld.get():imagesNameSaved);
            productDAO.save(product);
            return "redirect:/admin/product?save=true";
        }catch (Exception e){
            e.printStackTrace();
            return "redirect:/admin/product?save=false";
        }
    }

    @GetMapping("delete")
    @Transactional
    public String delete(@RequestParam("productId") Optional<String> productId){
        try{
            Integer id=Integer.parseInt(productId.get());
            orderDetailDAO.deleteByProductId(id);
            productDAO.deleteById(id);
            return "redirect:/admin/product?delete=true";
        }catch (Exception e){
            return "redirect:/admin/product?delete=false";
        }
    }
}
