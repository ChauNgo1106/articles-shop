package com.tecalliance.articles_shop.controllers;

import com.tecalliance.articles_shop.dto.ArticleCreateRequest;
import com.tecalliance.articles_shop.dto.ArticlePriceResponse;
import com.tecalliance.articles_shop.model.Article;
import com.tecalliance.articles_shop.services.ArticleService;
import com.tecalliance.articles_shop.services.PriceCalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/article")
@RequiredArgsConstructor
public class ArticleController {
    private final PriceCalculatorService priceCalculatorService;
    private final ArticleService articleService;

    @GetMapping("/prices")
    public ResponseEntity<ArticlePriceResponse> getPrices(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                                          @RequestParam(defaultValue = "0") int offset,
                                                          @RequestParam(defaultValue = "5") int limit){
        Pageable pageable = PageRequest.of(offset, limit, Sort.by("id").ascending());
        return ResponseEntity.status(HttpStatus.OK).body(priceCalculatorService.getPricesForDate(date,pageable, limit, offset));
    }

    @PostMapping("/addArticle")
    public ResponseEntity<Article> postArticle(@RequestBody ArticleCreateRequest articleCreateRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(articleService.createArticle(articleCreateRequest));
    }

}
