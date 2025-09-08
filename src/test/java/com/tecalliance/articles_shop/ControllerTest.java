package com.tecalliance.articles_shop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecalliance.articles_shop.dto.request.ArticleCreateRequest;
import com.tecalliance.articles_shop.dto.request.DiscountCreateRequest;
import com.tecalliance.articles_shop.dto.response.ArticlePriceResponse;
import com.tecalliance.articles_shop.dto.response.ArticleResponse;
import com.tecalliance.articles_shop.model.Article;
import com.tecalliance.articles_shop.model.Discount;
import com.tecalliance.articles_shop.services.ArticleService;
import com.tecalliance.articles_shop.services.DiscountService;
import com.tecalliance.articles_shop.services.PriceCalculatorService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PriceCalculatorService priceCalculatorService;

    @Mock
    private ArticleService articleService;

    @Mock
    private DiscountService discountService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetPrices() throws Exception {
        LocalDate date = LocalDate.of(2025, 9, 8);
        ArticlePriceResponse mockResponse = new ArticlePriceResponse(); // populate as needed

        when(priceCalculatorService.getPricesForDate(eq(date), any(Pageable.class), eq(5), eq(0)))
                .thenReturn(mockResponse);

        mockMvc.perform(get("/api/v1/article/prices")
                        .param("date", date.toString())
                        .param("offset", "0")
                        .param("limit", "5"))
                .andExpect(status().isOk());
    }

    @Test
    void testPostArticle() throws Exception {
        ArticleCreateRequest request = new ArticleCreateRequest();
        request.setName("Smartwatch");
        request.setSlogan("Time meets tech");
        request.setNetPrice(BigDecimal.valueOf(150));
        request.setSalesPrice(BigDecimal.valueOf(220));
        request.setVatRatio(BigDecimal.valueOf(0.15));

        Article mockArticle = new Article();
        mockArticle.setId(1L);
        mockArticle.setName("Smartwatch");

        when(articleService.createArticle(any(ArticleCreateRequest.class))).thenReturn(mockArticle);

        mockMvc.perform(post("/api/v1/article/addArticle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Smartwatch"));
    }

    @Test
    void testGetArticleById() throws Exception {
        Long articleId = 1L;
        ArticleResponse response = new ArticleResponse();
        response.setId(articleId);
        response.setName("Laptop");

        when(articleService.getArticleById(articleId)).thenReturn(response);

        mockMvc.perform(get("/api/v1/article/{id}", articleId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Laptop"));
    }

    @Test
    void testGetPrices_MissingDateParam() throws Exception {
        mockMvc.perform(get("/api/v1/article/prices")
                        .param("offset", "0")
                        .param("limit", "5"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testAddDiscount_Success() throws Exception {
        DiscountCreateRequest request = new DiscountCreateRequest();
        request.setDiscountRate(BigDecimal.valueOf(0.2));
        request.setStartDate(LocalDate.of(2025, 9, 10));
        request.setEndDate(LocalDate.of(2025, 9, 20));
        request.setArticleId(1L);

        Discount mockDiscount = new Discount();
        mockDiscount.setId(100L);
        mockDiscount.setDiscountRate(request.getDiscountRate());
        mockDiscount.setStartDate(request.getStartDate());
        mockDiscount.setEndDate(request.getEndDate());

        Article mockArticle = new Article();
        mockArticle.setName("Laptop");
        mockDiscount.setArticle(mockArticle);

        when(discountService.createDiscount(any(DiscountCreateRequest.class))).thenReturn(mockDiscount);

        mockMvc.perform(post("/api/v1/discount/addDiscount")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.discountRate").value(0.2))
                .andExpect(jsonPath("$.articlesName").value("Laptop"));
    }

    @Test
    void testAddDiscount_MalformedJson() throws Exception {
        String malformedJson = "{ \"discountRate\": 0.2, \"startDate\": \"2025-09-10\" "; // missing closing brace

        mockMvc.perform(post("/api/v1/discount/addDiscount")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(malformedJson))
                .andExpect(status().isBadRequest());
    }

}