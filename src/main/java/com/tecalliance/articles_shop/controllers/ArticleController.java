package com.tecalliance.articles_shop.controllers;

import com.tecalliance.articles_shop.dto.request.ArticleCreateRequest;
import com.tecalliance.articles_shop.dto.response.ArticlePriceResponse;
import com.tecalliance.articles_shop.dto.response.ArticleResponse;
import com.tecalliance.articles_shop.model.Article;
import com.tecalliance.articles_shop.services.ArticleService;
import com.tecalliance.articles_shop.services.PriceCalculatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Tag(name = "Article", description = "Article APIs")
@RestController
@RequestMapping("/api/v1/article")
@RequiredArgsConstructor
public class ArticleController {
    private final PriceCalculatorService priceCalculatorService;
    private final ArticleService articleService;

    @Operation(description = "Get the discounted value on each article (paginated), sorted by id")
    @GetMapping("/prices")
    public ResponseEntity<ArticlePriceResponse> getPrices(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                                          @RequestParam(defaultValue = "0") int offset,
                                                          @RequestParam(defaultValue = "5") int limit){
        Pageable pageable = PageRequest.of(offset, limit, Sort.by("id").ascending());
        return ResponseEntity.status(HttpStatus.OK).body(priceCalculatorService.getPricesForDate(date,pageable, limit, offset));
    }

    @Operation(description = "Create a new article")
    @PostMapping("/addArticle")
    public ResponseEntity<Article> postArticle(@RequestBody ArticleCreateRequest articleCreateRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(articleService.createArticle(articleCreateRequest));
    }

    @Operation(description = "Search an article by id (discount sorted descendingly)")
    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponse> getArticleById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(articleService.getArticleById(id));
    }

}
