package com.tecalliance.articles_shop.controllers;

import com.tecalliance.articles_shop.dto.ArticlePriceDTO;
import com.tecalliance.articles_shop.services.PriceCalculatorService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleController {
    private final PriceCalculatorService priceCalculatorService;

    @GetMapping("/prices")
    public List<ArticlePriceDTO> getPrices(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return priceCalculatorService.getPricesForDate(date);
    }

}
