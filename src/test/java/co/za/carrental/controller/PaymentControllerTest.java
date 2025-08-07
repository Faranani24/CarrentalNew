package co.za.carrental.controller;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PaymentControllerTest {

    @Autowired
    private PaymentController paymentController;

    @Autowired
    private IPaymentService paymentService;

    @Autowired
    private PaymentRepository paymentRepository;

    private Payment testPayment;

    @BeforeEach
    void setUp() {
        paymentRepository.deleteAll();

        testPayment = PaymentFactory.buildPayment(
                UUID.randomUUID(),
                UUID.randomUUID(),
                new BigDecimal("1000.00"),
                PaymentMethod.CREDIT_CARD,
                PaymentStatus.COMPLETED
        );

        // Save the test data using the service to ensure the whole flow is tested
        paymentService.createPayment(testPayment);
    }

    @Test
    void create_shouldCreatePayment() {
        Payment newPayment = PaymentFactory.buildPayment(
                UUID.randomUUID(),
                UUID.randomUUID(),
                new BigDecimal("500.00"),
                PaymentMethod.EFT,
                PaymentStatus.PENDING
        );
        ResponseEntity<Payment> response = paymentController.createPayment(newPayment);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(newPayment.getAmount(), response.getBody().getAmount());
        assertEquals(2, paymentRepository.count());
    }

    @Test
    void read_shouldReturnPaymentById() {
        ResponseEntity<Payment> response = paymentController.readPayment(testPayment.getPaymentId());

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testPayment.getAmount(), response.getBody().getAmount());
    }

    @Test
    void update_shouldUpdatePayment() {
        // Create a new object with the same ID but updated details
        Payment updatedPayment = Payment.builder()
                .paymentId(testPayment.getPaymentId())
                .bookingId(testPayment.getBookingId())
                .customerId(testPayment.getCustomerId())
                .paymentMethod(testPayment.getPaymentMethod())
                .status(PaymentStatus.PENDING)
                .amount(new BigDecimal("1200.00"))
                .build();

        ResponseEntity<Payment> response = paymentController.updatePayment(updatedPayment);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(new BigDecimal("1200.00"), response.getBody().getAmount());

        // Verify the change in the database
        assertEquals(new BigDecimal("1200.00"), paymentRepository.findById(testPayment.getPaymentId()).get().getAmount());
    }

    @Test
    void delete_shouldDeletePayment() {
        paymentController.deletePayment(testPayment.getPaymentId());

        // Verify the payment is deleted from the database
        assertEquals(0, paymentRepository.count());
    }

    @Test
    void getAll_shouldReturnAllPayments() {
        ResponseEntity<List<Payment>> response = paymentController.getAllPayments();

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }
}