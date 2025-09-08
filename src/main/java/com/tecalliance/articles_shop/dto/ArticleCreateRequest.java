package com.tecalliance.articles_shop.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ArticleCreateRequest {
    private String name;
    private String slogan;
    private BigDecimal netPrice;
    private BigDecimal salesPrice;
    private BigDecimal vatRatio;
}
