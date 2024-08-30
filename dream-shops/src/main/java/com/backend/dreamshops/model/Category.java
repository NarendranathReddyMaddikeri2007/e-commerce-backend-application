package com.backend.dreamshops.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Category {

    @Id //Primary Key of Entity
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    //A category can have many Products
    @OneToMany(mappedBy = "category")
    private List<Product> products;
}
