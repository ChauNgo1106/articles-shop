package com.tecalliance.articles_shop.controllers;

import com.tecalliance.articles_shop.dto.ArticlePriceResponse;
import com.tecalliance.articles_shop.services.PriceCalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleController {
    private final PriceCalculatorService priceCalculatorService;

    @GetMapping("/prices")
    public ResponseEntity<ArticlePriceResponse> getPrices(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                                          @RequestParam(defaultValue = "0") int offset,
                                                          @RequestParam(defaultValue = "5") int limit){
        Pageable pageable = PageRequest.of(offset, limit, Sort.by("id").ascending());
        return ResponseEntity.status(HttpStatus.OK).body(priceCalculatorService.getPricesForDate(date,pageable, limit, offset));
    }

}
