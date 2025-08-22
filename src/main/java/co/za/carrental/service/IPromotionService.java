package co.za.carrental.service;

import co.za.carrental.domain.Promotion;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IPromotionService extends IService<Promotion, String> {

    // Custom business logic placeholder
    void customPromotionLogic();

    // CRUD and useful queries
    Promotion save(Promotion promotion);

    Optional<Promotion> findById(String promoId);

    List<Promotion> findAll();

    void deleteById(String promoId);

    Optional<Promotion> findByCode(String code);

    List<Promotion> findByDiscountGreaterThan(float discount);

    List<Promotion> findValidPromotions(Date currentDate);
}
