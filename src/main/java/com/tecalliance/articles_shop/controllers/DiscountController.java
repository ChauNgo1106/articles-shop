package com.tecalliance.articles_shop.controllers;

import com.tecalliance.articles_shop.dto.request.DiscountCreateRequest;
import com.tecalliance.articles_shop.dto.response.DiscountResponse;
import com.tecalliance.articles_shop.model.Discount;
import com.tecalliance.articles_shop.services.DiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/discount")
@RequiredArgsConstructor
public class DiscountController {

    private final DiscountService discountService;

    @PostMapping("/addDiscount")
    public ResponseEntity<DiscountResponse> addDiscount(@RequestBody DiscountCreateRequest discountCreateRequest){
        Discount discount = discountService.createDiscount(discountCreateRequest);
        DiscountResponse response = DiscountResponse.builder()
                .id(discount.getId())
                .discountRate(discount.getDiscountRate())
                .startDate(discount.getStartDate())
                .endDate(discount.getEndDate())
                .articlesName(discount.getArticle().getName())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
