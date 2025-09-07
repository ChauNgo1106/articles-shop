package com.tecalliance.articles_shop.services;

import com.tecalliance.articles_shop.dto.ArticlePriceDTO;
import com.tecalliance.articles_shop.model.Article;
import com.tecalliance.articles_shop.model.Discount;
import com.tecalliance.articles_shop.repositories.ArticleRepository;
import com.tecalliance.articles_shop.repositories.DiscountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PriceCalculatorService {
    private final DiscountRepository discountRepository;
    private final ArticleRepository articleRepository;

    public List<ArticlePriceDTO> getPricesForDate(LocalDate date){
        return articleRepository.findAll()
                .stream()
                .map(article -> {
                    BigDecimal finalPrice = calculatedPrice(article, date);
                    return new ArticlePriceDTO(article.getName(), finalPrice);
                })
                .collect(Collectors.toList());
    }

    public BigDecimal calculatedPrice(Article article, LocalDate date){
        List<Discount> validDiscounts = discountRepository.findByArticleIdAndExpiryDate(article.getId(), date, date);

        return validDiscounts.stream()
                .sorted(Comparator.comparing(Discount::getDiscountRate).reversed())
                .map(discount -> article.getSalesPrice()
                        .multiply(BigDecimal.ONE.subtract(discount.getDiscountRate()))
                        .setScale(2, RoundingMode.HALF_UP))
                .filter(discounted -> discounted.compareTo(article.getNetPrice()) > 0)
                .findFirst()
                .orElse(article.getNetPrice());
    }

}
