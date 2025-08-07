/*
 * IPaymentService.java
 * Author: Inam Jim (222086939)
 * Date: 11 May 2025
 */
package co.za.carrental.service;

import co.za.carrental.domain.Payment;
import co.za.carrental.domain.PaymentStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IPaymentService {

    Payment createPayment(Payment payment);

    Optional<Payment> getPaymentById(UUID paymentId);

    Payment updatePayment(Payment payment);

    void deletePayment(UUID paymentId);

    List<Payment> getAllPayments();

    List<Payment> findByCustomerId(UUID customerId);

    List<Payment> findByBookingId(UUID bookingId);

    List<Payment> findByStatus(PaymentStatus status);
}

