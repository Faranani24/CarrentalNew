package co.za.carrental.service;

import co.za.carrental.domain.Promotion;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IPromotionsService {

    Promotion save(Promotion promotion);

    Optional<Promotion> read(String promoId);

    List<Promotion> findAll();

    void delete(String promoId);

    Optional<Promotion> findByCode(String code);

    List<Promotion> findByDiscountGreaterThan(float discount);

    List<Promotion> findValidPromotions(Date currentDate);

    List<Promotion> findExpiringPromotions(Date endDate);

    List<Promotion> findByCodePattern(String pattern);
}