package org.zerock.api2.product.domain;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.HashSet;
import java.util.Set;

@Entity
@Builder //Product 객체를 쉽게 생성할 수 있게 해줌
@AllArgsConstructor //모든 필드를 매개변수로 받는 생성자를 자동으로 생성
@NoArgsConstructor //매개변수가 없는 기본 생성자를 자동으로 생성
@Getter //모든 필드에 대한 getter 메서드를 자동으로 생성
@ToString(exclude = "tags") //toString 메서드를 자동으로 생성하여 객체의 문자열 표현을 정의
@Table(name = "tbl_product") //DB에서 'tbl_product' 테이블과 매핑됨
public class Product {

    @Id //pno 필드가 pk임을 나타냄
    //pno가 DB에서 자동 생성, Autoincrement와 동일한 코드
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pno;

    private String pname;

    private int price;

    private ProductStatus status;

    @ElementCollection
    @CollectionTable(name = "tbl_product_tag")
    @Builder.Default //NullPointException 에러 때문에 추가함
    @BatchSize(size = 100)
    private Set<String> tags = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "tbl_product_img")
    private Set<ContentImage> images = new HashSet<>();

    public void addTag(String tag) {
        tags.add(tag);
    }
    public void removeTag(String tag) {
        tags.remove(tag);
    }
    public void clearTags() {
        tags.clear();
    }
}
