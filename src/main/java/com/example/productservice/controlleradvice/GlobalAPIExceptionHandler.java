package com.example.productservice.controlleradvice;

import com.example.productservice.exceptions.InvalidToken;
import com.example.productservice.exceptions.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Controller
public class GlobalAPIExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> nullPointerExceptionHandler(NullPointerException e) {
        return ResponseEntity
                .status(HttpStatusCode.valueOf(404))
                .body("Product not found");
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> productNotFoundExceptionHandler(ProductNotFoundException e) {
        return ResponseEntity
                .status(HttpStatusCode.valueOf(404))
                .body(e.getLocalizedMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> genericExceptionHandler(Exception e) {
        return ResponseEntity
                .status(HttpStatusCode.valueOf(404))
                .body("Generic exception occured");
    }

    @ExceptionHandler(InvalidToken.class)
    public ResponseEntity<String> InvalidExceptionHandler(Exception e) {
        return ResponseEntity
                .status(HttpStatusCode.valueOf(400))
                .body(e.getLocalizedMessage());
    }
}
