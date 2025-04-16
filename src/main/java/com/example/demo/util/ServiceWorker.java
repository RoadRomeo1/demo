package com.example.demo.util;

public class ServiceWorker implements ResourceProMax{
    @Override
    public void close() throws Exception {
        // Implement the logic to release resources here
        System.out.println("Resources pro max is released.");
    }
}
