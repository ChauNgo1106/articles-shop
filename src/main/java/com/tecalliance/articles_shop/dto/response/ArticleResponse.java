package com.tecalliance.articles_shop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ArticleResponse {
    private Long id;
    private String name;
    private String slogan;
    private BigDecimal netPrice;
    private BigDecimal salesPrice;
    private BigDecimal vatRatio;
    private List<BigDecimal> discountRateList;
}
