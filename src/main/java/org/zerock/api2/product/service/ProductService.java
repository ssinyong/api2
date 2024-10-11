package org.zerock.api2.product.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.api2.common.dto.PageRequestDTO;
import org.zerock.api2.common.dto.PageResponseDTO;
import org.zerock.api2.product.dto.ProductListDTO;
import org.zerock.api2.product.repository.ProductRepository;

@Service //Spring이 이 클래스를 서비스 계층의 빈으로 관리하도록 함
@Transactional
@Log4j2
@RequiredArgsConstructor //주로 final로 선언된 필드를 초기화하는 데 사용됨
public class ProductService {

    //@RequiredArgsConstructor로 인해 생성자 주입 방식으로 초기화됨
    private final ProductRepository productRepository;

    // PageRequestDTO를 받아 제품 목록을 페이지로 반환하는 메서드
    public PageResponseDTO<ProductListDTO> getList(PageRequestDTO pageRequestDTO) {
        //repository에서 list 메서드를 호출하여 PageResponseDTO를 반환함
        return productRepository.list(pageRequestDTO);
    }
}
