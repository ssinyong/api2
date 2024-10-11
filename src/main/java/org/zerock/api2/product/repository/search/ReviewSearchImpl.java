package org.zerock.api2.product.repository.search;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.http.ResponseEntity;
import org.zerock.api2.product.domain.QContentImage;
import org.zerock.api2.product.domain.QReview;
import org.zerock.api2.product.domain.Review;

import java.util.List;

@Log4j2
public class ReviewSearchImpl extends QuerydslRepositorySupport implements ReviewSearch {

    public ReviewSearchImpl() {
        super(Review.class);
    }

    @Override
    public Page<Review> listByProduct(Long pno, Pageable pageable) {

        // 안나오면 compile.java 해줘야 함
        QReview review = QReview.review;
        QContentImage image = QContentImage.contentImage;

        JPQLQuery<Review> query = from(review);
        query.leftJoin(review.images, image);
        query.where(review.product.pno.eq(pno));
        query.where(image.ord.eq(0)); //null 고려 안함

        // 페이징 처리
        this.getQuerydsl().applyPagination(pageable, query);

        // review.images.any() -> image
        JPQLQuery<Tuple> tupleQuery =
                query.select(review.rno, review.score, image);

        List<Tuple> tupleList = tupleQuery.fetch();

        tupleList.forEach(tuple -> {
            log.info(tuple);
        });

        return null;

//        List<Review> list = query.fetch();
//
//        long total = query.fetchCount();
//
//        return new PageImpl<>(list, pageable, total);
    }
}
