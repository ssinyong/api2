package org.zerock.api2.product.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
//엔티티가 아니라서 PK 필요없음, Builder 안씀
public class ContentImage {

    private int ord;
    private String filename;


}


