package com.example.productservice.services;

import com.example.productservice.exceptions.ProductNotFoundException;
import com.example.productservice.models.Product;

import java.util.List;

public interface ProductService {

    public Product getProductById(Long id) throws ProductNotFoundException;
    public List<Product> getProducts() throws ProductNotFoundException;

    public Product createProduct(String title, String description, Double price,
                                 String imageUrl, String categoryName);

    public Product partialUpdate(Long id, Product product) throws ProductNotFoundException;

    Product partialUpdate(Long id, String title, String description,
                          Double price, String imageUrl, String categoryName);
}
