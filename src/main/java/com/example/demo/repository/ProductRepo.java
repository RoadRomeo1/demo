package com.example.demo.repository;

public interface ProductRepo extends org.springframework.data.jpa.repository.JpaRepository<com.example.demo.entity.Product, Long> {
    // This interface will automatically provide CRUD operations for the Product entity
    // No additional methods are needed unless you want to define custom queries
}
