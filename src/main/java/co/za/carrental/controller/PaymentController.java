/*
 * PaymentController.java
 * Author: Inam Jim (222086939)
 * Date: 11 May 2025
 */
package co.za.carrental.controller;

import co.za.carrental.domain.Payment;
import co.za.carrental.service.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final IPaymentService paymentService;

    @Autowired
    public PaymentController(IPaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create")
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {
        Payment newPayment = paymentService.createPayment(payment);
        return new ResponseEntity<>(newPayment, HttpStatus.CREATED);
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