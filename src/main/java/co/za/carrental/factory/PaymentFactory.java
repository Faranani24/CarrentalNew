/*
 * PaymentFactory.java
 * Author: Inam Jim (222086939)
 * Date: 11 May 2025
 * ??
 */
//
package co.za.carrental.factory;

import co.za.carrental.domain.Payment;
import co.za.carrental.domain.PaymentMethod;
import co.za.carrental.domain.PaymentStatus;

import java.math.BigDecimal;
import java.util.UUID;

public class PaymentFactory {

    public static Payment buildPayment(
            UUID bookingId, // Parameter order fixed for clarity
            UUID customerId,
            BigDecimal amount,
            PaymentMethod paymentMethod,
            PaymentStatus status // Parameter name fixed for consistency
    ) {
        return Payment.builder()
                .paymentId(UUID.randomUUID())
                .bookingId(bookingId) // FIX: Using the passed-in bookingId
                .customerId(customerId)
                .amount(amount)
                .paymentMethod(paymentMethod)
                .status(status) // FIX: Using the consistent parameter name
                .build();
    }
}