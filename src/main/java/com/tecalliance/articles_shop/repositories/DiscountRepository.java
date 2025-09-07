package com.tecalliance.articles_shop.repositories;

import com.tecalliance.articles_shop.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
}
