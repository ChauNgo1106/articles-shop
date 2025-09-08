package com.tecalliance.articles_shop.services;

import com.tecalliance.articles_shop.dto.request.DiscountCreateRequest;
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

    public Discount createDiscount(DiscountCreateRequest discountCreateRequest){
        Optional<Article> article = articleRepository.findById(discountCreateRequest.getArticleId());
        if (article.isEmpty()) {
            throw new IllegalArgumentException("Article (id: " + discountCreateRequest.getArticleId() + ")" + " Not Found");
        }
        Discount discount = Discount.builder()
                .discountRate(discountCreateRequest.getDiscountRate())
                .startDate(discountCreateRequest.getStartDate())
                .endDate(discountCreateRequest.getEndDate())
                .article(article.get())
                .build();
        return discountRepository.save(discount);
    }
}
