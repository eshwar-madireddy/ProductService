package com.example.productservice.commons;

import com.example.productservice.dtos.UserDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpHeaders;
@Component
public class AuthenticationCommons {
    private RestTemplate restTemplate;
    public AuthenticationCommons(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UserDto validateToken(String tokenValue) {
        if(tokenValue == null)
            return null;

        HttpHeaders header = new HttpHeaders();
        header.set("Authentication",tokenValue);

        HttpEntity<String> entity = new HttpEntity<>(header);
        ResponseEntity<UserDto> response;

        try {
            response = restTemplate.exchange(
                    "http://localhost:9000/users/validate",
                    HttpMethod.POST,
                    entity,
                    UserDto.class);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
        return response.getBody();
    }
}
