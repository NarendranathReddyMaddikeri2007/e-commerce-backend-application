package com.backend.dreamshops.service.product;

import com.backend.dreamshops.exception.ProductNotFoundException;
import com.backend.dreamshops.model.Category;
import com.backend.dreamshops.model.Product;
import com.backend.dreamshops.repository.CategoryRepository;
import com.backend.dreamshops.repository.ProductRepository;
import com.backend.dreamshops.request.AddProductRequest;
import com.backend.dreamshops.request.UpdateProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{

    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    private final CategoryRepository categoryRepository;

    @Override
    public Product addProduct(AddProductRequest request) {
        //Check if Category is found in DB
        //If Yes, set to new product category
        //If No, then save it as a new Category & set to new Product
        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName())).orElseGet(()->{
            Category newCategory = new Category(request.getCategory().getName());
            return categoryRepository.save(newCategory);
        });
        request.setCategory(category);
        return productRepository.saveAndFlush(createProduct(request,category));
    }

    private Product createProduct(AddProductRequest addProductRequest, Category category){
        return new Product(
                addProductRequest.getName(),
                addProductRequest.getBrand(),
                addProductRequest.getPrice(),
                addProductRequest.getInventory(),
                addProductRequest.getDescription(),
                category
        );
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(()->new ProductNotFoundException("Product not found!"));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id).ifPresentOrElse(productRepository::delete,()-> {
            throw new ProductNotFoundException("Product not found!");
        });
    }

    @Override
    public Product updateProduct(UpdateProductRequest request, Long productId) {
        /*
        1) Find existing product by productId
        2) Get and save the new Product by updateExistingProduct(...) method
        3) If Product not found, throw Exception
         */
        return productRepository.findById(productId)
                .map(existingProduct-> updateExistingProduct(existingProduct,request))
                .map(productRepository::save)
                .orElseThrow(()->new ProductNotFoundException("Product not found!"));
        //end at 1:04:40
    }

    private Product updateExistingProduct(Product existingProduct, UpdateProductRequest request){
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());

        Category category = categoryRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);
        return existingProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        //JPA Query by Name property of Category entity
        return productRepository.findAllByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findAllByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        //JPA Query by Name property of Category entity and brand property of Product entity
        return productRepository.findAllByCategoryNameAndBrand(category,brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findAllByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findAllByBrandAndName(brand, name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndNme(brand,name);
    }
}
