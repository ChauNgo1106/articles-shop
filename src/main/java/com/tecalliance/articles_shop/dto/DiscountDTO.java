package com.tecalliance.articles_shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class DiscountDTO {
    private BigDecimal discountRate;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long articleId;
}
