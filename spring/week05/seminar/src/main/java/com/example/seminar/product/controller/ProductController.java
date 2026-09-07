package com.example.seminar.product.controller;

import com.example.seminar.product.dto.ProductResponse;
import com.example.seminar.product.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/jpa")
    public List<ProductResponse> findTop10ByPrice() {
        return productService.findTop10ByPrice();
    }

    @GetMapping("/jpql")
    public List<ProductResponse> findCheapTop5() {
        return productService.findCheapTop5();
    }
}