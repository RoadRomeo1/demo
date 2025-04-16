package com.example.demo.controller;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.entity.Product;
import com.example.demo.service.PaginationService;

@WebMvcTest(controllers = ProductController.class) // Explicitly specify the controller
@ContextConfiguration(classes = {ProductController.class, ProductControllerTest.TestConfig.class}) // Load ProductController and TestConfig
@AutoConfigureMockMvc(addFilters = false) // Disable security filters for the test
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PaginationService paginationService;

    @Configuration
    static class TestConfig {
        @Bean
        public PaginationService paginationService() {
            return Mockito.mock(PaginationService.class); // Provide a mock PaginationService
        }
    }

    @Test
    @WithMockUser // Mock an authenticated user for the test
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

    @Test
    @WithMockUser // Mock an authenticated user for the test
    void testGetProducts_InvalidPageParameters() throws Exception {
        // Simulate invalid page parameters
        Mockito.when(paginationService.getProducts(200, 100))
                .thenThrow(new IllegalArgumentException("fromIndex(200) > toIndex(100)"));

        mockMvc.perform(get("/api/products")
                .param("pageNo", "200")
                .param("pageSize", "100")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("fromIndex(200) > toIndex(100)"));
    }
}
