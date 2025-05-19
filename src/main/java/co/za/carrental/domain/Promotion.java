package co.za.carrental.domain;

import java.util.Date;

/**
 * Promotion.java
 * Branch entity class for car rental system
 * Author: Milani Ncana (216269369)
 * Date: [11/05/2025]
 */
public class Promotion {
    private final String promoId;
    private final String code;
    private final float discount;
    private final Date expiryDate;

    private Promotion(Builder builder) {
        this.promoId = builder.promoId;
        this.code = builder.code;
        this.discount = builder.discount;
        this.expiryDate = builder.expiryDate;
    }

    // Builder Class
    public static class Builder {
        private String promoId;
        private String code;
        private float discount;
        private Date expiryDate;

        public Builder promoId(String promoId) {
            this.promoId = promoId;
            return this;
        }

        public Builder code(String code) {
            this.code = code;
            return this;
        }

        public Builder discount(float discount) {
            this.discount = discount;
            return this;
        }

        public Builder expiryDate(Date expiryDate) {
            this.expiryDate = expiryDate;
            return this;
        }

        public Promotion build() {
            return new Promotion(this);
        }
    }

    // Getters
    public String getPromoId() {
        return promoId;
    }

    public String getCode() {
        return code;
    }

    public float getDiscount() {
        return discount;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    @Override
    public String toString() {
        return "Promotion{" +
                "promoId='" + promoId + '\'' +
                ", code='" + code + '\'' +
                ", discount=" + discount +
                ", expiryDate=" + expiryDate +
                '}';
    }
}
