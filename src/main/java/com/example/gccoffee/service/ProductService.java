package com.example.gccoffee.service;

import java.util.List;

import com.example.gccoffee.model.Category;
import com.example.gccoffee.model.Product;

public interface ProductService {

    List<Product> getProductsByCategory(Category category);

    List<Product> getAllProducts();

    Product createProduct(String productName, Category category, long price);

    Product createProduct(String productName, Category category, long price, String description);
}
