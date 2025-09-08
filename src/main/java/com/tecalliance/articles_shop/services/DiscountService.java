package com.tecalliance.articles_shop.services;

import com.tecalliance.articles_shop.dto.DiscountDTO;
import com.tecalliance.articles_shop.model.Article;
import com.tecalliance.articles_shop.model.Discount;
import com.tecalliance.articles_shop.repositories.ArticleRepository;
import com.tecalliance.articles_shop.repositories.DiscountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiscountService {

    private final DiscountRepository discountRepository;
    private final ArticleRepository articleRepository;

    public Discount createDiscount(DiscountDTO discountDTO){
        Optional<Article> article = articleRepository.findById(discountDTO.getArticleId());
        if (article.isEmpty()) {
            throw new IllegalArgumentException("Article (id: " + discountDTO.getArticleId() + ")" + " Not Found");
        }
        Discount discount = Discount.builder()
                .discountRate(discountDTO.getDiscountRate())
                .startDate(discountDTO.getStartDate())
                .endDate(discountDTO.getEndDate())
                .article(article.get())
                .build();
        return discountRepository.save(discount);
    }
}
