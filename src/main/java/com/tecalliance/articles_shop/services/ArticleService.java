package com.tecalliance.articles_shop.services;

import com.tecalliance.articles_shop.dto.request.ArticleCreateRequest;
import com.tecalliance.articles_shop.model.Article;
import com.tecalliance.articles_shop.repositories.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
