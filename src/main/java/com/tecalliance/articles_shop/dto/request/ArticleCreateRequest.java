package com.tecalliance.articles_shop.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleCreateRequest {
    private String name;
    private String slogan;
    private BigDecimal netPrice;
    private BigDecimal salesPrice;
    private BigDecimal vatRatio;
}
