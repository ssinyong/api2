package org.zerock.api2.product.repository.search;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.api2.common.dto.PageRequestDTO;
import org.zerock.api2.common.dto.PageResponseDTO;
import org.zerock.api2.product.domain.Product;
import org.zerock.api2.product.domain.ProductStatus;
import org.zerock.api2.product.domain.QProduct;
import org.zerock.api2.product.domain.QReview;
import org.zerock.api2.product.dto.ProductListDTO;

import java.util.List;

@Log4j2
// QuerydslRepositorySupport를 상속하여 QueryDSL을 사용한 동적 쿼리 생성을 지원하는 클래스
public class ProductSearchImpl extends QuerydslRepositorySupport implements ProductSearch {


    public ProductSearchImpl(){
        super(Product.class);
    }



    @Override
    public Page<Product> list1(Pageable pageable) {

        QProduct product = QProduct.product;
        JPQLQuery<Product> query = from(product);
        //query.fetchJoin();
        query.where(product.status.eq(ProductStatus.SALE));
        query.where(product.pno.gt(0L));

        // 페이징 처리
        this.getQuerydsl().applyPagination(pageable, query);

        List<Product> list = query.fetch();
        long total = query.fetchCount();

        return new PageImpl<>(list, pageable, total);

    }

    @Override
    public Page<Product> list2(String keyword, ProductStatus status, Pageable pageable) {

        QProduct product = QProduct.product;
        JPQLQuery<Product> query = from(product);
        query.where(product.status.eq(ProductStatus.SALE));
        query.where(product.pno.gt(0L));

        if(keyword != null){
            query.where(product.pname.like("%"+keyword+"%"));
        }

        if(status != null) {
            query.where(product.status.eq(status));
        }

        // 페이징 처리
        this.getQuerydsl().applyPagination(pageable, query);

        List<Product> list = query.fetch();
        long total = query.fetchCount();

        return new PageImpl<>(list, pageable, total);

    }

    @Override
    public Page<Product> listWithReplyCount(Pageable pageable) {

        //Product와 Review 엔티티
        QProduct product = QProduct.product;
        QReview review = QReview.review;

        //JPQLQuery 객체를 생성하여 Product 엔티티를 기본으로 하는 쿼리를 만듦
        JPQLQuery<Product> query = from(product);
        // Review와 Product를 LEFT JOIN하여 연관된 리뷰를 가져옴
        query.leftJoin(review).on(review.product.eq(product));
        query.groupBy(product); // Product를 기준으로 그룹화

        this.getQuerydsl().applyPagination(pageable, query); //페이징처리 끝!

        // Review의 개수와 평균 점수를 계산하여 ProductListDTO에 매핑
        JPQLQuery<ProductListDTO> dtoJPQLQuery = query.select(
                Projections.bean(ProductListDTO.class,
                    product.pno,
                    product.pname,
                    product.price,
                    review.count().as("reviewCnt"),
                    review.score.avg().coalesce(0.0).as("avgScore")
                ));

        // 쿼리를 로그에 출력
        log.info(dtoJPQLQuery);

        // 쿼리를 실행하여 결과 리스트를 가져옴
        java.util.List<ProductListDTO> dtoList = dtoJPQLQuery.fetch();

        // dtoList.forEach(dto -> log.info(dto));
        // 축약
        dtoList.forEach(log::info); // 각 DTO를 로그에 출력

//        List<ProductListDTO> tupleList = dtoJPQLQuery.fetch();
//
//        long total = dtoJPQLQuery.fetchCount();

        return null;

    }

    @Override
    public PageResponseDTO<ProductListDTO> list(PageRequestDTO pageRequestDTO) {

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage()-1,
                pageRequestDTO.getSize(),
                Sort.by("pno").descending());

        QProduct product = QProduct.product;
        QReview review = QReview.review;

        JPQLQuery<Product> query = from(product);
        query.leftJoin(review).on(review.product.eq(product)); //JOIN
        //검색 조건은 여기에..
        query.groupBy(product);

        this.getQuerydsl().applyPagination(pageable, query); //페이징처리 끝!

        // reviewCnt, avgScore
        JPQLQuery<ProductListDTO> dtoJPQLQuery = query.select(
                Projections.bean(ProductListDTO.class,
                        product.pno,
                        product.pname,
                        product.price,
                        review.count().as("reviewCnt"),
                        review.score.avg().coalesce(0.0).as("avgScore")
                ));

        log.info(dtoJPQLQuery);

        java.util.List<ProductListDTO> dtoList = dtoJPQLQuery.fetch();

        // dtoList.forEach(dto -> log.info(dto));
        // 축약
        dtoList.forEach(log::info);

        long total = dtoJPQLQuery.fetchCount();

        return PageResponseDTO.<ProductListDTO>withAll()
                .dtoList(dtoList)
                .pageRequestDTO(pageRequestDTO)
                .totalCount(total)
                .build();
    }
}

