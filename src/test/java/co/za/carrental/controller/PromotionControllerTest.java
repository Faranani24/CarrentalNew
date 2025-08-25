// src/test/java/co/za/carrental/controller/PromotionControllerTest.java
package co.za.carrental.controller;

import co.za.carrental.domain.Promotion;
import co.za.carrental.factory.PromotionFactory;
import co.za.carrental.repository.PromotionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PromotionControllerTest {

    @Autowired
    private PromotionController promotionController;

    @Autowired
    private PromotionRepository promotionRepository;

    private Promotion testPromotion;

    private Date futureDate(int daysAhead) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, daysAhead);
        return cal.getTime();
    }

    @BeforeEach
    void setUp() {
        promotionRepository.deleteAll();
        testPromotion = PromotionFactory.buildPromotion("WELCOME10", 10.0f, futureDate(10));
        promotionRepository.save(testPromotion);
    }

    @Test
    void create_shouldCreatePromotion() {
        Promotion newPromo = PromotionFactory.buildPromotion("SUMMER20", 20.0f, futureDate(20));
        ResponseEntity<Promotion> response = promotionController.create(newPromo);

        assertNotNull(response.getBody());
        assertEquals("SUMMER20", response.getBody().getCode());
        assertEquals(2, promotionRepository.count());
    }

    @Test
    void getAll_shouldReturnAllPromotions() {
        ResponseEntity<List<Promotion>> response = promotionController.getAll();

        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void read_shouldReturnPromotionById() {
        ResponseEntity<Promotion> response = promotionController.read(testPromotion.getPromoId());

        assertNotNull(response.getBody());
        assertEquals("WELCOME10", response.getBody().getCode());
    }

    @Test
    void update_shouldUpdatePromotion() {
        testPromotion.setDiscount(15.0f);
        ResponseEntity<Promotion> response = promotionController.update(testPromotion.getPromoId(), testPromotion);

        assertNotNull(response.getBody());
        assertEquals(15.0f, response.getBody().getDiscount());
    }

    @Test
    void delete_shouldDeletePromotion() {
        promotionController.delete(testPromotion.getPromoId());
        assertEquals(0, promotionRepository.count());
    }

    @Test
    void search_byCode_shouldReturnPromotion() {
        ResponseEntity<?> response = promotionController.search("WELCOME10", null, null);

        assertTrue(response.getBody() instanceof Promotion);
        Promotion promo = (Promotion) response.getBody();
        assertEquals("WELCOME10", promo.getCode());
    }

    @Test
    void search_byMinDiscount_shouldReturnPromotions() {
        Promotion promo2 = PromotionFactory.buildPromotion("BIGSALE", 25.0f, futureDate(15));
        promotionRepository.save(promo2);

        ResponseEntity<?> response = promotionController.search(null, 15.0f, null);

        assertTrue(response.getBody() instanceof List);
        List<?> promos = (List<?>) response.getBody();
        assertEquals(1, promos.size());
    }

    @Test
    void search_byValid_shouldReturnValidPromotions() {
        ResponseEntity<?> response = promotionController.search(null, null, true);

        assertTrue(response.getBody() instanceof List);
        List<?> promos = (List<?>) response.getBody();
        assertFalse(promos.isEmpty());
    }
}