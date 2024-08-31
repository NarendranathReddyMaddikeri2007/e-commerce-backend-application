package com.backend.dreamshops.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class Product {

    @Id //Primary Key of Entity
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;


    //Product and Category won't depend on each other
    //Multiple Products belong to a Category
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;

    //When a product is deleted, all images associated with that product also deleted
    //A Product can have multiple Images
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images;

    public Product(String name, String brand, BigDecimal price, int inventory, String description, Category category) {
       this.name = name;
       this.brand = brand;
       this.price = price;
       this.inventory = inventory;
       this.description = description;
       this.category = category;
    }
}
