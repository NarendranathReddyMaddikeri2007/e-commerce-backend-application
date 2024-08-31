package com.backend.dreamshops.repository;

import com.backend.dreamshops.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findAllByBrand(String brand);

    List<Product> findAllByName(String name);

    List<Product> findAllByBrandAndName(String brand, String name);

    List<Product> findAllByCategoryNameAndBrand(String category, String brand);

    List<Product> findAllByCategoryName(String category);

    Long countByBrandAndNme(String brand, String name);
}
