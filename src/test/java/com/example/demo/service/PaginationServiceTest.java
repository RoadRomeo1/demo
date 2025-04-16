package com.example.demo.service;

import com.example.demo.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;

import static org.junit.jupiter.api.Assertions.*;

public class PaginationServiceTest {

    private PaginationService paginationService;

    @BeforeEach
    void setUp() {
        paginationService = new PaginationService();
        paginationService.init(); // Initialize the product list
    }

    @Test
    void testGetProducts_FirstPage() {
        Page<Product> page = paginationService.getProducts(0, 10);
        assertEquals(10, page.getContent().size());
        assertEquals(100, page.getTotalElements());
        assertEquals("Product 1", page.getContent().get(0).getName());
    }

    @Test
    void testGetProducts_LastPage() {
        Page<Product> page = paginationService.getProducts(9, 10);
        assertEquals(10, page.getContent().size());
        assertEquals("Product 91", page.getContent().get(0).getName());
    }

    @Test
    void testGetProducts_OutOfBoundsPage() {
        Page<Product> page = paginationService.getProducts(20, 10);
        assertTrue(page.getContent().isEmpty());
    }
}
