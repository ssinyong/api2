package org.zerock.api2.product.repo;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.api2.product.domain.ContentImage;
import org.zerock.api2.product.domain.Product;
import org.zerock.api2.product.domain.Review;
import org.zerock.api2.product.repository.ReviewRepository;

import java.util.HashSet;
import java.util.Set;

@DataJpaTest
@Log4j2
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ReviewTests {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    @Transactional
    @Commit
    public void testInsert() {

        Product product = Product.builder().pno(11L).build();

//        Set<ContentImage> images = new HashSet<>();
//        images.add(new ContentImage(0,"aaa.jpg"));
//        images.add(new ContentImage(1,"bbb.jpg"));
//        images.add(new ContentImage(2,"ccc.jpg"));


        Review review = Review.builder()
                .reviewer("r1")
                .score(5)
                .product(product)
//                .images(images)
                .build();

        review.addFile("AAA.jpg");
        review.addFile("BBB.jpg");
        review.addFile("CCC.jpg");

        reviewRepository.save(review);
    }

    @Test
    public void testListOneImage() {

        Pageable pageable =
                PageRequest.of(0, 10,
                        Sort.by("rno").descending());

        Page<Review> result = reviewRepository.listByProduct(11L, pageable);

        result.getContent().forEach(review -> {
            log.info(review);
            log.info(review.getImages());
            log.info("-----------------------");
        });

    }


}
