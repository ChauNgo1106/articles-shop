package com.tecalliance.articles_shop.services;

import com.tecalliance.articles_shop.dto.request.DiscountRequest;
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

    public Discount createDiscount(DiscountRequest discountRequest){
        Optional<Article> article = articleRepository.findById(discountRequest.getArticleId());
        if (article.isEmpty()) {
            throw new IllegalArgumentException("Article (id: " + discountRequest.getArticleId() + ")" + " Not Found");
        }
        Discount discount = Discount.builder()
                .discountRate(discountRequest.getDiscountRate())
                .startDate(discountRequest.getStartDate())
                .endDate(discountRequest.getEndDate())
                .article(article.get())
                .build();
        return discountRepository.save(discount);
    }
}
