package com.tecalliance.articles_shop.services;

import com.tecalliance.articles_shop.dto.request.ArticleCreateRequest;
import com.tecalliance.articles_shop.dto.response.ArticleResponse;
import com.tecalliance.articles_shop.model.Article;
import com.tecalliance.articles_shop.model.Discount;
import com.tecalliance.articles_shop.repositories.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public Article createArticle(ArticleCreateRequest articleCreateRequest){
        Article article = new Article();
        article.setName(articleCreateRequest.getName());
        article.setSlogan(articleCreateRequest.getSlogan());
        article.setNetPrice(articleCreateRequest.getNetPrice());
        article.setSalesPrice(articleCreateRequest.getSalesPrice());
        article.setVatRatio(articleCreateRequest.getVatRatio());
        return articleRepository.save(article);
    }

    public ArticleResponse getArticleById(Long id){
        Article article = articleRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Article (id: " + id + ")" + " Not Found")
        );

        return ArticleResponse.builder()
                .id(article.getId())
                .name(article.getName())
                .slogan(article.getSlogan())
                .netPrice(article.getNetPrice())
                .salesPrice(article.getSalesPrice())
                .vatRatio(article.getVatRatio())
                .discountRateList(article.getDiscounts()
                        .stream()
                        .map(Discount::getDiscountRate)
                        .sorted(Comparator.reverseOrder())
                        .toList()
                )
                .build();
    }
}
