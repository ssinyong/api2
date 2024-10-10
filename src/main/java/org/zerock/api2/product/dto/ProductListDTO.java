package org.zerock.api2.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
// @AllArgsConstructor // 조심
@NoArgsConstructor
public class ProductListDTO {

    private Long pno;
    private String pname;
    private int price;
    private long reviewCnt;
    private double avgScore;

    public ProductListDTO(Long pno, String pname, int price, long reviewCnt, double avgScore) {
        this.pno = pno;
        this.pname = pname;
        this.price = price;
        this.reviewCnt = reviewCnt;
        this.avgScore = avgScore;
    }

}
