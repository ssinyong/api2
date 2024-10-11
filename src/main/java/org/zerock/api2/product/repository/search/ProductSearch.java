package org.zerock.api2.product.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.api2.common.dto.PageRequestDTO;
import org.zerock.api2.common.dto.PageResponseDTO;
import org.zerock.api2.product.domain.Product;
import org.zerock.api2.product.domain.ProductStatus;
import org.zerock.api2.product.dto.ProductListDTO;

public interface ProductSearch {

    //단순 페이지 번호로 Product 목록 반환, 매개변수로 Pageable 객체를 받아서 페이징 처리된 결과를 반환
    Page<Product> list1(Pageable pageable);

    //검색 키워드와 제품 상태에 맞는 Product 목록을 페이징처리하여 반환
    Page<Product> list2(String keyword, ProductStatus status, Pageable pageable);

    Page<Product> listWithReplyCount(Pageable pageable);

    PageResponseDTO<ProductListDTO> list(PageRequestDTO pageRequestDTO);

}

