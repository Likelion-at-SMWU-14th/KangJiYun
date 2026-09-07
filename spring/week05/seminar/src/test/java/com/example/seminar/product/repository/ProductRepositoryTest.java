package com.example.seminar.product.repository;

import com.example.seminar.product.domain.Product;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;

import static com.example.seminar.product.domain.QProduct.product;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private ProductRepository productRepository;

    private JPAQueryFactory queryFactory;

    @BeforeEach
    void setUp() {
        queryFactory = new JPAQueryFactory(em);

        productRepository.saveAll(List.of(
                Product.create("사인펜", 1000, 100),
                Product.create("볼펜", 1200, 500),
                Product.create("네임펜", 1300, 380),
                Product.create("형광펜", 1800, 320),
                Product.create("샤프펜슬", 1900, 180),
                Product.create("화이트보드펜", 2500, 140),
                Product.create("붓펜", 3000, 50),
                Product.create("트윈펜", 4000, 70),
                Product.create("컬러펜", 5000, 80),
                Product.create("젤펜", 6000, 90),
                Product.create("마카펜 세트", 8500, 60),
                Product.create("만년필 고급형", 89000, 6),
                Product.create("지우개", 800, 640),
                Product.create("무선 마우스", 39000, 55),
                Product.create("4K 모니터", 450000, 12)
        ));
    }

    @Test
    void 이름에_펜이_들어가고_가격이_저렴한_상품을_조회한다() {
        List<Product> result = queryFactory
                .selectFrom(product)
                .where(product.name.contains("펜"))
                .orderBy(product.price.asc())
                .limit(10)
                .fetch();

        assertThat(result).hasSize(10);

        assertThat(result)
                .extracting(Product::getName)
                .containsExactly(
                        "사인펜",
                        "볼펜",
                        "네임펜",
                        "형광펜",
                        "샤프펜슬",
                        "화이트보드펜",
                        "붓펜",
                        "트윈펜",
                        "컬러펜",
                        "젤펜"
                );

        assertThat(result)
                .extracting(Product::getPrice)
                .containsExactly(
                        1000,
                        1200,
                        1300,
                        1800,
                        1900,
                        2500,
                        3000,
                        4000,
                        5000,
                        6000
                );
    }
}