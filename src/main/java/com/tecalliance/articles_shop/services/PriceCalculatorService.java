package com.tecalliance.articles_shop.services;

import com.tecalliance.articles_shop.dto.response.ArticlePriceDTO;
import com.tecalliance.articles_shop.dto.response.ArticlePriceResponse;
import com.tecalliance.articles_shop.model.Article;
import com.tecalliance.articles_shop.model.Discount;
import com.tecalliance.articles_shop.repositories.ArticleRepository;
import com.tecalliance.articles_shop.repositories.DiscountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PriceCalculatorService {
    private final DiscountRepository discountRepository;
    private final ArticleRepository articleRepository;

    public ArticlePriceResponse getPricesForDate(LocalDate date, Pageable pageable, int limit, int offset) {

        Page<Article> articlesPage = articleRepository.findAll(pageable);
        List<ArticlePriceDTO> articleList = articlesPage
                .stream()
                .map(article -> {
                    BigDecimal finalPrice = calculatedPrice(article, date);
                    return new ArticlePriceDTO(article.getName(), finalPrice);
                })
                .collect(Collectors.toList());

        return ArticlePriceResponse.builder()
                .limit(limit)
                .offset(offset)
                .totalElements((int)articlesPage.getTotalElements())
                .totalPages(articlesPage.getTotalPages())
                .numberOfElements(articlesPage.getNumberOfElements())
                .articles(articleList).build();
    }

    public BigDecimal calculatedPrice(Article article, LocalDate date){
        List<Discount> validDiscounts = discountRepository.findByArticleIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(article.getId(), date, date);

        //no discount available
        if(validDiscounts.isEmpty()){
            return article.getSalesPrice();
        }
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
