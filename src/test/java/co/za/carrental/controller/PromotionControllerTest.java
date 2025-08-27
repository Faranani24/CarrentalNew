/*
 *
 * Author: Milani Ncana (216269369)
 * Date: 11 May 2025
 *
 */

package co.za.carrental.controller;

import co.za.carrental.domain.Promotion;
import co.za.carrental.factory.PromotionFactory;
import co.za.carrental.service.IPromotionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PromotionControllerTest {

    @Autowired
    private PromotionController promotionController;

    @Autowired
    private IPromotionService promotionService;

    private Promotion testPromotion;


//    @BeforeEach
//    void setUp() {
//        testPromotion = PromotionFactory.createPromotion(
//                "P001", "WinterSpecialPromo", 20.0,
//                LocalDate.of(2025, 6, 1),
//                LocalDate.of(2025, 6, 30)
//        );
//        testPromotion = promotionService.create(testPromotion);
//    }
//
//    @Test
//    void create_shouldCreatePromotion() {
//        Promotion newPromotion = PromotionFactory.createPromotion(
//                "P002", "SpringDealPromo", 15.0,
//                LocalDate.of(2025, 9, 1),
//                LocalDate.of(2025, 9, 30)
//        );
//        ResponseEntity<Promotion> response = promotionController.create(newPromotion);
//        assertNotNull(response.getBody());
//        assertEquals("P002", response.getBody().getPromoId());
//    }

    @Test
    void read_shouldReturnPromotion() {
        ResponseEntity<Promotion> response = promotionController.read(testPromotion.getPromoId());
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals("WinterSpecialPromo", response.getBody().getCode());
    }

    @Test
    void update_shouldUpdatePromotion() {
        testPromotion.setCode("NewWinterSpecialPromo");
        ResponseEntity<Promotion> response = promotionController.update(testPromotion.getPromoId(), testPromotion);
        assertNotNull(response.getBody());
        assertEquals("NewWinterSpecialPromo", response.getBody().getCode());
    }

    @Test
    @Disabled
    void delete_shouldRemovePromotion() {
        promotionController.delete(testPromotion.getPromoId());
        Optional<Promotion> found = promotionService.read(testPromotion.getPromoId());
        assertFalse(found.isPresent());
    }

    @Test
    void getAll_shouldReturnAllPromotions() {
        ResponseEntity<List<Promotion>> response = promotionController.getAll();
        assertFalse(response.getBody().isEmpty());
    }
}
