package com.tecalliance.articles_shop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@AllArgsConstructor
@Data
public class ArticlePriceResponse {
    private int totalPages;
    private int totalElements;
    private int numberOfElements;
    private int offset;
    private int limit;
    private List<ArticlePriceDTO> articles;
}
