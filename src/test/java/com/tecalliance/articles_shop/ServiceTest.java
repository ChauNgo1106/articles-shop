package com.tecalliance.articles_shop;

import com.tecalliance.articles_shop.dto.request.ArticleCreateRequest;
import com.tecalliance.articles_shop.dto.response.ArticleResponse;
import com.tecalliance.articles_shop.model.Article;
import com.tecalliance.articles_shop.model.Discount;
import com.tecalliance.articles_shop.repositories.ArticleRepository;
import com.tecalliance.articles_shop.repositories.DiscountRepository;
import com.tecalliance.articles_shop.services.ArticleService;
import com.tecalliance.articles_shop.services.PriceCalculatorService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class ServiceTest {

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private DiscountRepository discountRepository;

    @InjectMocks
    private ArticleService articleService;

    @InjectMocks
    private PriceCalculatorService priceCalculatorService;

    /*****************Article Service******************/
    @Test
    void testCreateArticle() {
        ArticleCreateRequest request = new ArticleCreateRequest();
        request.setName("Laptop");
        request.setSlogan("Power meets portability");
        request.setNetPrice(BigDecimal.valueOf(800));
        request.setSalesPrice(BigDecimal.valueOf(1000));
        request.setVatRatio(BigDecimal.valueOf(0.2));

        Article savedArticle = new Article();
        savedArticle.setId(1L);
        savedArticle.setName("Laptop");

        when(articleRepository.save(any(Article.class))).thenReturn(savedArticle);

        Article result = articleService.createArticle(request);

        assertEquals("Laptop", result.getName());
        verify(articleRepository).save(any(Article.class));
    }

    @Test
    void testGetArticleById_Success() {
        Article article = new Article();
        article.setId(1L);
        article.setName("Laptop");

        when(articleRepository.findById(1L)).thenReturn(Optional.of(article));

        ArticleResponse result = articleService.getArticleById(1L);

        assertEquals("Laptop", result.getName());
    }

    @Test
    void testGetArticleById_NotFound() {
        when(articleRepository.findById(999L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            articleService.getArticleById(999L);
        });

        assertEquals("Article (id: 999) Not Found", exception.getMessage());
    }

    /*****************Price Calculator Service******************/
    @Test
    void shouldApplyHighestValidDiscountAboveNetPrice() {
        // Given
        Article article = new Article();
        article.setId(1L);
        article.setNetPrice(new BigDecimal("85.00"));
        article.setSalesPrice(new BigDecimal("100.00"));

        LocalDate date = LocalDate.now();

        Discount discount1 = Discount.builder()
                .discountRate(new BigDecimal("0.05")) // 5%
                .startDate(date.minusDays(1))
                .endDate(date.plusDays(1))
                .build();

        Discount discount2 = Discount.builder()
                .discountRate(new BigDecimal("0.10")) // 10%
                .startDate(date.minusDays(1))
                .endDate(date.plusDays(1))
                .build();

        when(discountRepository.findByArticleIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(1L, date, date))
                .thenReturn(List.of(discount1, discount2));

        // When
        BigDecimal result = priceCalculatorService.calculatedPrice(article, date);

        // Then
        BigDecimal expected = new BigDecimal("90.00"); // 100 - 100 * 10% = 90
        assertEquals(result, expected);
    }

    @Test
    void shouldReturnSalesPriceWhenNoDiscountsAvailable() {
        // Given
        Article article = new Article();
        article.setId(2L);
        article.setNetPrice(new BigDecimal("80.00"));
        article.setSalesPrice(new BigDecimal("100.00"));

        LocalDate date = LocalDate.now();

        when(discountRepository.findByArticleIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(2L, date, date))
                .thenReturn(List.of());

        // When
        BigDecimal result = priceCalculatorService.calculatedPrice(article, date);

        // Then
        assertEquals(new BigDecimal("100.00"), result);
    }

    @Test
    void shouldApplyDiscountOnlyIfResultIsAboveNetPrice() {
        // Given
        Article article = new Article();
        article.setId(3L);
        article.setNetPrice(new BigDecimal("95.00"));
        article.setSalesPrice(new BigDecimal("100.00"));

        LocalDate date = LocalDate.now();

        Discount discount = Discount.builder()
                .discountRate(new BigDecimal("0.20")) // 20%
                .startDate(date.minusDays(1))
                .endDate(date.plusDays(1))
                .build();

        when(discountRepository.findByArticleIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(3L, date, date))
                .thenReturn(List.of(discount));

        // When
        BigDecimal result = priceCalculatorService.calculatedPrice(article, date);

        // Then
        assertEquals((new BigDecimal("95.00")), result);
    }
}
