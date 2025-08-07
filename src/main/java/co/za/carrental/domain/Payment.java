/*
 * Payment.java
 * Author: Inam Jim (222086939)
 * Date: 11 May 2025
 * ??
 */
//
package co.za.carrental.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "payments")
public class Payment {
    @Id
    private UUID paymentId;
    private UUID bookingId;
    private UUID customerId;
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @CreationTimestamp
    private LocalDateTime paymentDate;

    protected Payment() {
    }

    private Payment(Builder builder) {
        this.paymentId = builder.paymentId;
        this.bookingId = builder.bookingId;
        this.customerId = builder.customerId;
        this.amount = builder.amount;
        this.paymentMethod = builder.paymentMethod;
        this.status = builder.status;
        this.paymentDate = builder.paymentDate;
    }

    public UUID getPaymentId() {
        return paymentId;
    }

    public UUID getBookingId() {
        return bookingId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UUID paymentId;
        private UUID bookingId;
        private UUID customerId;
        private BigDecimal amount;
        private PaymentMethod paymentMethod;
        private PaymentStatus status;
        private LocalDateTime paymentDate;

        public Builder paymentId(UUID paymentId) {
            this.paymentId = paymentId;
            return this;
        }

        public Builder bookingId(UUID bookingId) {
            this.bookingId = bookingId;
            return this;
        }

        public Builder customerId(UUID customerId) {
            this.customerId = customerId;
            return this;
        }

        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder paymentMethod(PaymentMethod paymentMethod) {
            this.paymentMethod = paymentMethod;
            return this;
        }

        public Builder status(PaymentStatus status) {
            this.status = status;
            return this;
        }

        public Builder paymentDate(LocalDateTime paymentDate) {
            this.paymentDate = paymentDate;
            return this;
        }

        public Payment build() {
            return new Payment(this);
        }
    }
}