package com.example.productservice.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.naming.spi.ResolveResult;
import java.util.ArrayList;
import java.util.List;
@Service
public class FakeStoreCategoryService implements CategoryService{
    RestTemplate restTemplate;
    public FakeStoreCategoryService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    @Override
    public List<String> getCategory() {
        String[] response = restTemplate.getForObject(
                "https://fakestoreapi.com/products/categories",
                String[].class);
        List<String> categoryList = new ArrayList<>();
        for(String str : response)
            categoryList.add(str);
        return categoryList;
    }
}
