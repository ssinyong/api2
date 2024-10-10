package org.zerock.api2.product.domain;

import jakarta.persistence.*;
import lombok.ToString;

@Entity
@ToString(exclude = "product")
@Table(name = "tbl_review", indexes = {
        @Index(name = "idx_review_product", columnList = "product_pno")
})
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    private String reviewer;

    private int score;

    @ManyToOne(fetch = FetchType.LAZY) //product가 필요하기 전까진 안가져올꺼야..
    private Product product;

}
