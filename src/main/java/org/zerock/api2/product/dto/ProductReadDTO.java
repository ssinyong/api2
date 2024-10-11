package org.zerock.api2.product.dto;

import lombok.Data;
import org.zerock.api2.product.domain.ProductStatus;

@Data
public class ProductReadDTO {

    private Long pno;
    private String pname;
    private int price;
    private ProductStatus status; //enum 문제 될 수 있으셈

    public ProductReadDTO(Long pno, String pname, int price, ProductStatus status) {
        this.pno = pno;
        this.pname = pname;
        this.price = price;
        this.status = status;
    }
}
