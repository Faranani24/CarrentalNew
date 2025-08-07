//
package co.za.carrental.service.impl;

import co.za.carrental.domain.Payment;
import co.za.carrental.domain.PaymentMethod;
import co.za.carrental.domain.PaymentStatus;
import co.za.carrental.factory.PaymentFactory;
import co.za.carrental.repository.PaymentRepository;
import co.za.carrental.service.IPaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PaymentServiceImplTest {

    @Autowired
    private IPaymentService paymentService;

    @Autowired
    private PaymentRepository paymentRepository;

    private Payment payment1;
    private Payment payment2;
    private UUID customerId1;
    private UUID customerId2;
    private UUID bookingId1;
    private UUID bookingId2;

    @BeforeEach
    void setUp() {
        paymentRepository.deleteAll();

        customerId1 = UUID.randomUUID();
        customerId2 = UUID.randomUUID();
        bookingId1 = UUID.randomUUID();
        bookingId2 = UUID.randomUUID();

        // Capture the object returned by the service
        payment1 = paymentService.createPayment(PaymentFactory.buildPayment(
                bookingId1,
                customerId1,
                new BigDecimal("1000.00"),
                PaymentMethod.CREDIT_CARD,
                PaymentStatus.COMPLETED
        ));

        // Capture the object returned by the service
        payment2 = paymentService.createPayment(PaymentFactory.buildPayment(
                bookingId2,
                customerId2,
                new BigDecimal("500.00"),
                PaymentMethod.EFT,
                PaymentStatus.PENDING
        ));
    }

    @Test
    void testCreatePayment() {
        // Corrected variables to be unique for this test
        UUID newBookingId = UUID.randomUUID();
        UUID newCustomerId = UUID.randomUUID();
        BigDecimal newAmount = new BigDecimal("1500.00");

        // FIX: Corrected parameter order and usage of unique IDs
        Payment newPayment = PaymentFactory.buildPayment(
                newBookingId,
                newCustomerId,
                newAmount,
                PaymentMethod.DEBIT_CARD,
                PaymentStatus.COMPLETED
        );

        Payment createdPayment = paymentService.createPayment(newPayment);

        assertNotNull(createdPayment);
        assertNotNull(createdPayment.getPaymentId());

        // Assert on all the properties of the created object
        assertEquals(newBookingId, createdPayment.getBookingId());
        assertEquals(newCustomerId, createdPayment.getCustomerId());
        assertEquals(newAmount, createdPayment.getAmount());
        assertEquals(PaymentMethod.DEBIT_CARD, createdPayment.getPaymentMethod());
        assertEquals(PaymentStatus.COMPLETED, createdPayment.getStatus());
    }

    @Test
    void testReadPaymentById() {
        Optional<Payment> foundPayment = paymentService.getPaymentById(payment1.getPaymentId());
        assertTrue(foundPayment.isPresent());
        assertEquals(payment1.getAmount(), foundPayment.get().getAmount());
    }

    @Test
    void testUpdatePayment() {
        // Manually build a new object to update the amount
        Payment updatedPayment = Payment.builder()
                .paymentId(payment1.getPaymentId())
                .bookingId(payment1.getBookingId())
                .customerId(payment1.getCustomerId())
                .paymentMethod(payment1.getPaymentMethod())
                .status(payment1.getStatus())
                .paymentDate(payment1.getPaymentDate())
                .amount(new BigDecimal("1200.00"))
                .build();

        Payment result = paymentService.updatePayment(updatedPayment);

        assertNotNull(result);
        assertEquals(new BigDecimal("1200.00"), result.getAmount());

        Optional<Payment> found = paymentService.getPaymentById(payment1.getPaymentId());
        assertTrue(found.isPresent());
        assertEquals(new BigDecimal("1200.00"), found.get().getAmount());
    }

    @Test
    void testDeletePayment() {
        UUID paymentIdToDelete = payment2.getPaymentId();
        paymentService.deletePayment(paymentIdToDelete);

        Optional<Payment> found = paymentService.getPaymentById(paymentIdToDelete);
        assertFalse(found.isPresent(), "Payment should be deleted and not found");
    }

    @Test
    void testGetAllPayments() {
        List<Payment> payments = paymentService.getAllPayments();
        assertEquals(2, payments.size());
        assertTrue(payments.stream().anyMatch(p -> p.getPaymentId().equals(payment1.getPaymentId())));
    }

    @Test
    void testFindByCustomerId() {
        List<Payment> foundPayments = paymentService.findByCustomerId(customerId1);
        assertEquals(1, foundPayments.size());
        assertEquals(payment1.getPaymentId(), foundPayments.get(0).getPaymentId());
    }

    @Test
    void testFindByBookingId() {
        List<Payment> foundPayments = paymentService.findByBookingId(bookingId2);
        assertEquals(1, foundPayments.size());
        assertEquals(payment2.getPaymentId(), foundPayments.get(0).getPaymentId());
    }

    @Test
    void testFindByStatus() {
        List<Payment> pendingPayments = paymentService.findByStatus(PaymentStatus.PENDING);
        assertEquals(1, pendingPayments.size());
        assertEquals(payment2.getPaymentId(), pendingPayments.get(0).getPaymentId());
    }
}