package com.fashionstore.util;

import java.util.List;

import com.fashionstore.dao.ProductDAO;
import com.fashionstore.dao.impl.ProductDAOImpl;
import com.fashionstore.model.Product;

public class TestProductDAO {

    public static void main(String[] args) {

        ProductDAO dao = new ProductDAOImpl();

        // 1. Get all products
        List<Product> products = dao.getAllProducts();

        System.out.println("ALL PRODUCTS:");
        for (Product p : products) {
            System.out.println(
                p.getProductId() + " | " +
                p.getProductName() + " | " +
                p.getPrice()
            );
        }

        // 2. Get by ID
        Product p = dao.getProductById(1);
        System.out.println("\nProduct ID 1: " + p.getProductName());

        // 3. Search
        List<Product> search = dao.searchProducts("shirt");
        System.out.println("\nSearch Results:");
        for (Product sp : search) {
            System.out.println(sp.getProductName());
        }
    }
}
