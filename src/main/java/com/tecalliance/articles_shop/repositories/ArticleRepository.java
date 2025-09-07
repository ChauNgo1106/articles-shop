package com.tecalliance.articles_shop.repositories;

import com.tecalliance.articles_shop.model.Article;
import com.tecalliance.articles_shop.model.Discount;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article,Long> {
    List<Discount> findByArticleIdAndExpiryDate(Long articleId, LocalDate startDate, LocalDate endDate);
}
