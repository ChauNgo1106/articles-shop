package com.tecalliance.articles_shop.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountCreateRequest {
    private BigDecimal discountRate;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long articleId;
}
