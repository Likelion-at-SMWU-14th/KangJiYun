package com.likelion.seminar.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long number;

    private String name;
    private Integer price;
    private Integer stock;

    public Product(String name, Integer price){
        this.name = name;
        this.price = price;
    }

    @OneToOne(mappedBy = "product")
    @ToString.Exclude
    private ProductDetail productDetail;

    @ManyToOne
    @JoinColumn(name="provide_id")
    private Provider provider;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;
}
