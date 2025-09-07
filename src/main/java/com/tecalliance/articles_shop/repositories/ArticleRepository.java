package com.tecalliance.articles_shop.repositories;

import com.tecalliance.articles_shop.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ArticleRepository extends JpaRepository<Article,Long> {
}
