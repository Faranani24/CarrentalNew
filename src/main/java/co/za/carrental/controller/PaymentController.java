/*
 * PaymentController.java
 * Author: Inam Jim (222086939)
 * Date: 11 May 2025
 *
 */

package co.za.carrental.controller;

import co.za.carrental.domain.Payment;
import co.za.carrental.domain.PaymentMethod;
import co.za.carrental.domain.PaymentStatus;
import co.za.carrental.service.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*") // Configure this properly for production
public class PaymentController {

    private final IPaymentService paymentService;

    @Autowired
    public PaymentController(IPaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> processPayment(@RequestBody Map<String, Object> paymentData) {
        try {
            // Extract data from request
            String bookingIdStr = (String) paymentData.get("bookingId");
            Double totalAmount = ((Number) paymentData.get("totalAmount")).doubleValue();
            String paymentMethodStr = (String) paymentData.get("paymentMethod");
            String cardNumber = (String) paymentData.get("cardNumber");

            // Basic validation
            if (bookingIdStr == null || totalAmount <= 0 || cardNumber == null || cardNumber.length() < 13) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("message", "Invalid payment details provided");
                return ResponseEntity.badRequest().body(errorResponse);
            }

            // Convert payment method string to enum
            PaymentMethod paymentMethod;
            try {
                paymentMethod = PaymentMethod.valueOf(paymentMethodStr.toUpperCase());
            } catch (Exception e) {
                paymentMethod = PaymentMethod.CREDIT_CARD; // Default fallback
            }

            // Simulate payment processing (90% success rate for demo)
            boolean paymentSuccessful = Math.random() > 0.1;

            PaymentStatus status = paymentSuccessful ? PaymentStatus.COMPLETED : PaymentStatus.FAILED;

            // Create payment using builder pattern
            Payment payment = Payment.builder()
                    .paymentId(UUID.randomUUID())
                    .bookingId(UUID.fromString(bookingIdStr))
                    .amount(BigDecimal.valueOf(totalAmount))
                    .paymentMethod(paymentMethod)
                    .status(status)
                    .build();

            if (paymentSuccessful) {
                Payment savedPayment = paymentService.createPayment(payment);

                // Return success response
                Map<String, Object> successResponse = new HashMap<>();
                successResponse.put("status", "success");
                successResponse.put("paymentId", savedPayment.getPaymentId());
                successResponse.put("transactionId", savedPayment.getPaymentId().toString());
                successResponse.put("bookingId", bookingIdStr);
                successResponse.put("amount", totalAmount);
                successResponse.put("message", "Payment processed successfully");

                return ResponseEntity.ok(successResponse);
            } else {
                paymentService.createPayment(payment);

                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("message", "Payment was declined. Please check your card details and try again.");
                return ResponseEntity.badRequest().body(errorResponse);
            }

        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Payment processing failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<Payment> readPayment(@PathVariable UUID id) {
        Optional<Payment> foundPayment = paymentService.getPaymentById(id);
        return foundPayment.map(payment -> new ResponseEntity<>(payment, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/update")
    public ResponseEntity<Payment> updatePayment(@RequestBody Payment payment) {
        if (payment.getPaymentId() == null || !paymentService.getPaymentById(payment.getPaymentId()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Payment updatedPayment = paymentService.updatePayment(payment);
        return new ResponseEntity<>(updatedPayment, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable UUID id) {
        if (!paymentService.getPaymentById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        paymentService.deletePayment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = paymentService.getAllPayments();
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

}