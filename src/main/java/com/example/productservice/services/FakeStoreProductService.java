package com.example.productservice.services;

import com.example.productservice.dtos.FakeStoreProductRequestDto;
import com.example.productservice.dtos.FakeStoreProductResponseDto;
import com.example.productservice.exceptions.ProductNotFoundException;
import com.example.productservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoreProductService implements ProductService{
    RestTemplate restTemplate;

    @Autowired
    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    @Override
    public Product getProductById(Long id) throws ProductNotFoundException {
        FakeStoreProductResponseDto responseDto = restTemplate.getForObject(
                "https://fakestoreapi.com/products/" + id,
                FakeStoreProductResponseDto.class
        );

        if(responseDto==null)
            throw new ProductNotFoundException("Product with Id "+id +" not found!");
        return responseDto.toProduct();
    }

    public List<Product> getProducts() throws ProductNotFoundException {
        FakeStoreProductResponseDto[] responseDtos = restTemplate.getForObject(
                "https://fakestoreapi.com/products",
                FakeStoreProductResponseDto[].class
        );
        if(responseDtos==null)
            throw new ProductNotFoundException("No Products to List");

        List<Product> products = new ArrayList<>();
        for(FakeStoreProductResponseDto responseDto: responseDtos) {
            products.add(responseDto.toProduct());
        }

        return products;
    }

    public Product createProduct(String title, String description,
                                 Double price, String imageUrl, String categoryName) {

        FakeStoreProductRequestDto requestDto = new FakeStoreProductRequestDto();
        requestDto.setTitle(title);
        requestDto.setDescription(description);
        requestDto.setCategory(categoryName);
        requestDto.setPrice(price);
        requestDto.setImage(imageUrl);

        FakeStoreProductResponseDto responseDto = restTemplate.postForObject(
                "https://fakestoreapi.com/products",
                requestDto,
                FakeStoreProductResponseDto.class
        );


        return responseDto.toProduct();
    }

    @Override
    public Product partialUpdate(Long id, Product product) throws ProductNotFoundException {
        return null;
    }

    @Override
    public Product partialUpdate(Long id, String title, String description,
                                 Double price, String imageUrl, String categoryName) {
        FakeStoreProductRequestDto fakeStoreProductRequestDto = new FakeStoreProductRequestDto();
        fakeStoreProductRequestDto.setTitle(title);
        fakeStoreProductRequestDto.setDescription(description);
        fakeStoreProductRequestDto.setPrice(price);
        fakeStoreProductRequestDto.setImage(imageUrl);
        fakeStoreProductRequestDto.setCategory(categoryName);
        HttpEntity<FakeStoreProductRequestDto> httpEntity =
                new HttpEntity<>(fakeStoreProductRequestDto); // Add dto object here

        ResponseEntity<FakeStoreProductResponseDto> responseEntity = restTemplate.exchange(
                "https://fakestoreapi.com/products" + id,
                HttpMethod.PATCH,
                httpEntity, // use dto here
                FakeStoreProductResponseDto.class
        );

        FakeStoreProductResponseDto responseDto = responseEntity.getBody();

        return responseDto.toProduct();
    }
}
