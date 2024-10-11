package org.zerock.api2.product.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.api2.product.domain.Product;
import org.zerock.api2.product.dto.ProductReadDTO;
import org.zerock.api2.product.repository.search.ProductSearch;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductSearch {

    // 특정 제품 번호(pno)를 기준으로 제품 정보를 조회하는 메서드
    @Query("select " +
            // 새로운 ProductReadDTO 객체를 생성하여 필요한 필드만 선택
            "new org.zerock.api2.product.dto.ProductReadDTO(p.pno, p.pname, p.price,p.status) " +
            "from Product p where p.pno = :pno")
    // 조회 결과가 존재할 수도 있고 존재하지 않을 수도 있으므로 Optional로 감싸 반환
    Optional<ProductReadDTO> read(@Param("pno") Long pno);

    // 추가함
    @EntityGraph(attributePaths = {"tags"})
    @Query("select p from Product p where p.pno = :pno")
    Optional<Product> read2(@Param("pno") Long pno);

    @EntityGraph(attributePaths = {"tags"})
    @Query("select p from Product p")
    Page<Product> listOld(Pageable pageable);


}


