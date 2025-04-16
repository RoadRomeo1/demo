package com.example.demo.service;

import com.example.demo.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

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

    public Page<Product> getProducts(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), productList.size());
        List<Product> paginatedList = productList.subList(start, end);
        return new PageImpl<>(paginatedList, pageable, productList.size());
    }
}
