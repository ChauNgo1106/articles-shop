package com.tecalliance.articles_shop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class DiscountResponse {
    private int id;
    private BigDecimal discountRate;
    private LocalDate startDate;
    private LocalDate endDate;
    private String articlesName;
}
