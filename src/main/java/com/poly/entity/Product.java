package com.poly.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Product")
@Getter
@Setter
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Nationalized
    @Column(name = "product_name", length = 500)
    private String productName;

    @Column(name = "price")
    private Integer price;

    @Column(name = "discount")
    private Integer discount;

    @Nationalized
    @Lob
    @Column(name = "note")
    private String note;

    @Lob
    @Column(name = "images")
    private String images;

    @Column(name = "unit")
    private String unit;

    @Column(name = "number_of_sale")
    private Integer numberOfSale;

    @Column(name = "category_id", nullable = true)
    private Integer categoryId;

    @Column(name = "brand_id", nullable = true)
    private Integer brandId;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "brand_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Brand brand;
}