package com.example.productservice.controllers;

import com.example.productservice.commons.AuthenticationCommons;
import com.example.productservice.dtos.ProductRequestDto;
import com.example.productservice.dtos.ProductResponseDto;
import com.example.productservice.dtos.UserDto;
import com.example.productservice.exceptions.InvalidToken;
import com.example.productservice.exceptions.ProductNotFoundException;
import com.example.productservice.models.Product;
import com.example.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {
    private ProductService productService;
    private AuthenticationCommons authenticationCommons;

    @Autowired
    public ProductController(@Qualifier("ProductService") ProductService productService, AuthenticationCommons authenticationCommons) {
        this.productService = productService;
        this.authenticationCommons = authenticationCommons;
    }

    @GetMapping("/Product/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable("id") Long id, @RequestHeader("Authorization") String token) throws ProductNotFoundException, InvalidToken {
        UserDto userDto = authenticationCommons
                .validateToken(token);
        if(userDto == null) {
            throw new InvalidToken("Invalid Token");
        }
        Product product = productService.getProductById(id);
        ProductResponseDto response = ProductResponseDto.from(product);
        ResponseEntity<ProductResponseDto> resp =
                new ResponseEntity<>(response, HttpStatusCode.valueOf(200));
        return resp;
    }

    @GetMapping("/Product")
    public ResponseEntity<List<ProductResponseDto>> getAllproducts() throws ProductNotFoundException {
        List<Product> productList = productService.getProducts();
        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();
        for(Product product : productList) {
            ProductResponseDto response = ProductResponseDto.from(product);
            productResponseDtoList.add(response);
        }
        return new ResponseEntity<>(productResponseDtoList,HttpStatusCode.valueOf(200));
    }

    @PostMapping("/Product")
    public ProductResponseDto createProduct(@RequestBody ProductRequestDto productRequestDto) {
        Product product = productService.createProduct(
                productRequestDto.getTitle(),
                productRequestDto.getDescription(),
                productRequestDto.getPrice(),
                productRequestDto.getImage(),
                productRequestDto.getCategory()
        );

        return ProductResponseDto.from(product);
    }
    @PatchMapping("/Product/{id}")
    public ProductResponseDto updateProduct(@PathVariable("id") Long id,@RequestBody ProductRequestDto productRequestDto) {
/*        Product product = productService.partialUpdate(id,
                productRequestDto.getTitle(),
                productRequestDto.getDescription(),
                productRequestDto.getPrice(),
                productRequestDto.getImage(),
                productRequestDto.getCategory()
        );

        return ProductResponseDto.from(product);*/
        return null;
    }
}
