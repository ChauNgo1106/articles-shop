package com.tecalliance.articles_shop.repositories;

import com.tecalliance.articles_shop.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
    List<Discount> findByArticleIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(Long articleId, LocalDate startDate, LocalDate endDate);
}
