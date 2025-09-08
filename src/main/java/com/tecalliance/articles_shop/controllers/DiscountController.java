package com.tecalliance.articles_shop.controllers;

import com.tecalliance.articles_shop.dto.DiscountDTO;
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
    public ResponseEntity<Discount> addDiscount(@RequestBody DiscountDTO discountDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(discountService.createDiscount(discountDTO));
    }
}
