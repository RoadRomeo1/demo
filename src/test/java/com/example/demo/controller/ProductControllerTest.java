package com.example.demo.controller;

import com.example.demo.entity.Product;
import com.example.demo.service.PaginationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private PaginationService paginationService;

    @BeforeEach
    void setUp() {
        paginationService = Mockito.mock(PaginationService.class);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    void testGetProducts() throws Exception {
        List<Product> products = List.of(new Product());
        products.get(0).setId(1L);
        products.get(0).setName("Product 1");
        products.get(0).setPrice(11.0);

        Page<Product> page = new PageImpl<>(products);
        Mockito.when(paginationService.getProducts(0, 10)).thenReturn(page);

        mockMvc.perform(get("/api/products")
                .param("pageNo", "0")
                .param("pageSize", "10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Product 1"))
                .andExpect(jsonPath("$.content[0].price").value(11.0));
    }
}
