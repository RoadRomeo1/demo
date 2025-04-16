package com.example.demo.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Product;

import jakarta.annotation.PostConstruct;

@Service
public class PaginationService {

    private final List<Product> productList = new ArrayList<>();

    @PostConstruct
    public  void init() {
        // Initialize the in-memory product list with 100 sample products
        for (int i = 1; i <= 100; i++) {
            Product product = new Product();
            product.setId((long) i);
            product.setName("Product " + i);
            product.setPrice(10.0 + i);
            productList.add(product);
        }
    }

    public Page<Product> getProducts(int page, int size) {
        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("Page number and size must be greater than zero.");
        }

        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, productList.size());

        // Handle out-of-bounds requests
        if (fromIndex >= productList.size()) {
            return new PageImpl<>(Collections.emptyList(), PageRequest.of(page, size), productList.size());
        }

        List<Product> subList = productList.subList(fromIndex, toIndex);
        return new PageImpl<>(subList, PageRequest.of(page, size), productList.size());
    }
}
