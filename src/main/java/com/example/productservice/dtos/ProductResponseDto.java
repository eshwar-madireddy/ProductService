package com.example.productservice.dtos;

import com.example.productservice.models.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseDto {
    private Long id;
    private String title;
    private String description;
    private Double price;
    private String imageUrl;


    public static ProductResponseDto from(Product product) {

        ProductResponseDto responseDto = new ProductResponseDto();
        responseDto.id = product.getId();
        responseDto.description = product.getDescription();;
        responseDto.imageUrl = product.getImageUrl();;
        responseDto.price = product.getPrice();;
        responseDto.title = product.getTitle();

        return responseDto;
    }
}
