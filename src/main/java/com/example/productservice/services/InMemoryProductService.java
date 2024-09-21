package com.example.productservice.services;

import com.example.productservice.services.ProductService;
import com.example.productservice.exceptions.ProductNotFoundException;
import com.example.productservice.models.Category;
import com.example.productservice.models.Product;
import com.example.productservice.repositories.CategoryRepository;
import com.example.productservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("ProductService")
public class InMemoryProductService implements ProductService {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public InMemoryProductService(ProductRepository productRepository,
                            CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product getProductById(Long id) throws ProductNotFoundException {
        return null;
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(String title, String description, Double price,
                                 String imageUrl, String categoryName) {
        Product product = new Product();
        product.setTitle(title);
        product.setDescription(description);
        product.setPrice(price);
        product.setImageUrl(imageUrl);

        product.setCategory(getCategoryFromDB(categoryName));

        return productRepository.save(product);
    }

    @Override
    public Product partialUpdate(Long id, Product product) throws ProductNotFoundException {
        Optional<Product> productOptional = productRepository.findById(id);
        if(productOptional.isEmpty()) {
            throw new ProductNotFoundException("Product that you want to update is not found");
        }

        Product productToUpdate = productOptional.get();
        if(product.getTitle() != null) {
            productToUpdate.setTitle(product.getTitle());
        }

        if(product.getDescription() != null) {
            productToUpdate.setDescription(product.getDescription());
        }

        // TODO: Add other fields

        if(product.getCategory() != null) {
            productToUpdate.setCategory(
                    getCategoryFromDB(product.getCategory().getName())
            );
        }


        return productRepository.save(productToUpdate);
    }

    @Override
    public Product partialUpdate(Long id, String title, String description, Double price, String imageUrl, String categoryName) {
        return null;
    }

    private Category getCategoryFromDB(String categoryName) {
        Optional<Category> categoryOptional = categoryRepository.findByName(categoryName);

        if(categoryOptional.isEmpty()) {
            Category category = new Category();
            category.setName(categoryName);
            return categoryRepository.save(category);
        }

        return categoryOptional.get();
    }
}