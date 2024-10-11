package org.zerock.api2.product.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
//toString 메서드 자동으로 생성하되 순환 참조 방지하여 product 필드는 제외
@ToString(exclude = "product")
@Table(name = "tbl_review", indexes = {
        @Index(name = "idx_review_product", columnList = "product_pno")
})
@Builder
@AllArgsConstructor
@NoArgsConstructor //매번 같은 어노테이션은 super 클래스로 뺄수있음
public class Review {

    @Id //댓글 번호가 pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    private String reviewer;

    private int score;

    //다대일 관계: 댓글은 하나의 제품에 속한다
    //FetchType.LAZY: product 필드는 필요할 때만 로드됨(성능 최적화를 위해)
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @ElementCollection
    @CollectionTable(name = "tbl_review_img")
    private Set<ContentImage> images = new HashSet<>();

    //0번이 겹치지 않게
    public void addFile(String filename) {
        ContentImage image = new ContentImage(images.size(),filename);
        images.add(image);
    }

    public void changeImages(Set<ContentImage> images) {
        this.images = images;
    }

}
