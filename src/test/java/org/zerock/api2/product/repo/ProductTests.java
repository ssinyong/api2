package org.zerock.api2.product.repo;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.api2.common.dto.PageRequestDTO;
import org.zerock.api2.common.dto.PageResponseDTO;
import org.zerock.api2.product.domain.Product;
import org.zerock.api2.product.dto.ProductListDTO;
import org.zerock.api2.product.repository.ProductRepository;

import java.util.HashSet;
import java.util.Optional;

@DataJpaTest
@Log4j2
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductTests {

    @Autowired
    private ProductRepository productRepository;

//    업데이트 할 때 문제가 되는 코드
    @Test
    @Transactional
    @Commit
    public void testInsert() {

        HashSet<String> tagSet = new HashSet<>();
        tagSet.add("test");
        tagSet.add("test2");
        tagSet.add("test3");

        Product product = Product.builder()
                .pname("Test Product")
                .price(3000)
                .tags(tagSet)
                .build();
//        product.addTag("AAA");
//        product.addTag("BBB");
//        product.addTag("CCC");

        productRepository.save(product);
    }

    @Test
    public void testList1() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("pno").descending());

        productRepository.list1(pageable);

    }

    @Test
    public void testListWithReplyCount() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("pno").descending());

        productRepository.listWithReplyCount(pageable);
    }

    @Test
    public void testRead() {

//        log.info(productRepository.read2(21L));
        Optional<Product> result = productRepository.read2(21L);
        Product product = result.get();

        log.info(product);
        log.info(product.getTags());

    }

    @Test
    public void testReadTag() {

        Optional<Product> result = productRepository.findById(21L);
        Product product = result.get();
        log.info(product);
//        log.info(productRepository.read(11L));

    }

    @Test
    public void testDTOList() {
        PageRequestDTO requestDTO = PageRequestDTO.builder().build();

        PageResponseDTO<ProductListDTO> result
                = productRepository.list(requestDTO);

        log.info(result);
    }

}
