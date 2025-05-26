package co.za.carrental.service;

import co.za.carrental.domain.Promotion;
import co.za.carrental.repository.PromotionsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PromotionsServiceTest {

    private PromotionsRepository repository;
    private PromotionsService service;

    @BeforeEach
    public void setUp() {
        repository = Mockito.mock(PromotionsRepository.class);
        service = new PromotionsService(repository);
        Promotion promo = new Promotion.Builder()
                .promoId("PROMO1")
                .code("SAVE10")
                .discount(10.0f)
                .expiryDate(new Date())
                .build();


    }

    @Test
    public void testSavePromotion() {
        Promotion promo = new Promotion.Builder()
                .promoId("PROMO1")
                .code("SAVE10")
                .discount(10.0f)
                .expiryDate(new Date())
                .build();

        when(repository.save(promo)).thenReturn(promo);

        Promotion saved = service.save(promo);
        assertEquals(promo, saved);
        verify(repository).save(promo);
    }

    @Test
    public void testReadPromotion() {
        Promotion promo = new Promotion.Builder()
                .promoId("PROMO1")
                .code("SAVE10")
                .discount(10.0f)
                .expiryDate(new Date())
                .build();

        when(repository.findById("PROMO1")).thenReturn(Optional.of(promo));

        Optional<Promotion> result = service.read("PROMO1");
        assertTrue(result.isPresent());
        assertEquals("SAVE10", result.get().getCode());
    }

    @Test
    public void testDeletePromotion() {
        service.delete("PROMO1");
        verify(repository).deleteById("PROMO1");
    }

    @Test
    public void testFindAllPromotions() {
        List<Promotion> promotions = List.of(
                new Promotion.Builder()
                        .promoId("PROMO1")
                        .code("SAVE10")
                        .discount(10.0f)
                        .expiryDate(new Date())
                        .build(),
                new Promotion.Builder()
                        .promoId("PROMO2")
                        .code("SAVE20")
                        .discount(20.0f)
                        .expiryDate(new Date())
                        .build());
        when(repository.findAll()).thenReturn(promotions);

        List<Promotion> result = service.findAll();
        assertEquals(2, result.size());
    }

    @Test
    public void testFindByCode() {
        Promotion promo = new Promotion.Builder()
                .promoId("PROMO1")
                .code("SAVE10")
                .discount(10.0f)
                .expiryDate(new Date())
                .build();
        when(repository.findByCode("SAVE10")).thenReturn(promo);

        Optional<Promotion> result = service.findByCode("SAVE10");
        assertTrue(result.isPresent());
        assertEquals("SAVE10", result.get().getCode());
    }
}
