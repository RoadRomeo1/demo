package com.example.demo.util;

public class MainWorker {
    public static void main(String[] args) {
        try (ServiceWorker serviceWorker = new ServiceWorker()) {
            // Use the service worker
            System.out.println("Using the service worker.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
