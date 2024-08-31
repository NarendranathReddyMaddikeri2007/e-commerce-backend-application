package com.backend.dreamshops.request;

import com.backend.dreamshops.model.Category;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class UpdateProductRequest {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
}
