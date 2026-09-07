package com.example.seminar.product.domain;

import jakarta.persistence.*;

@Entity
@Table(name="product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int price;
    private int stock;

    protected Product() {}

    private Product(String name, int price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public static Product create(
            String name,
            int price,
            int stock
    ) {
        return new Product(name, price, stock);
    }

    public int getStock() {
        return stock;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }
}
