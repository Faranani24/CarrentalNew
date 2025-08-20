/*
 * Promotion.java
 * Author: Milani Ncana (216269369)
 * Date: 11 May 2025
 */

package co.za.carrental.domain;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "promotion")
public class Promotion {

    @Id
    private String promoId;

    private String code;
    private Float discount;

    @Temporal(TemporalType.DATE) // Ensures only the date part is stored
    private Date expiryDate;

    // Default constructor required by JPA
    public Promotion() {
    }

    // Builder-based constructor
    private Promotion(Builder builder) {
        this.promoId = builder.promoId;
        this.code = builder.code;
        this.discount = builder.discount;
        this.expiryDate = builder.expiryDate;
    }

    // --- Setters ---
    public void setPromoId(String promoId) {
        this.promoId = promoId;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    // --- Getters ---
    public String getPromoId() {
        return promoId;
    }

    public String getCode() {
        return code;
    }

    public Float getDiscount() {
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

    // --- Builder ---
    public static class Builder {
        private String promoId;
        private String code;
        private Float discount;
        private Date expiryDate;

        public Builder setPromoId(String promoId) {
            this.promoId = promoId;
            return this;
        }

        public Builder setCode(String code) {
            this.code = code;
            return this;
        }

        public Builder setDiscount(Float discount) {
            this.discount = discount;
            return this;
        }

        public Builder setExpiryDate(Date expiryDate) {
            this.expiryDate = expiryDate;
            return this;
        }

        public Promotion build() {
            return new Promotion(this);
        }
    }
}
