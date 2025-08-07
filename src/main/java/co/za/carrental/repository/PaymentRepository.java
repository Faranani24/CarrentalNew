/*
 * PaymentRepository.java
 * Author: Inam Jim (222086939)
 * Date: 11 May 2025
 *
 */
//
package co.za.carrental.repository;

import co.za.carrental.domain.Payment;
import co.za.carrental.domain.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    List<Payment> findByCustomerId(UUID customerId);

    List<Payment> findByBookingId(UUID bookingId);

    // FIX: Method name must match the field in the domain class, which is 'status'
    List<Payment> findByStatus(PaymentStatus status);
}