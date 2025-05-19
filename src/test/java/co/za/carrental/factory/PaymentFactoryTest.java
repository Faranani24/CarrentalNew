package co.za.carrental.factory;

import co.za.carrental.domain.Payment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PaymentFactoryTest {

    // Test method to create a Payment object
    @Test
    void createPayment() {
        Payment payment = PaymentFactory.createPayment(
                "P123", "CREDIT_CARD", 1500.00
        );

        // Assert that the payment object is not null);
        assertNotNull(payment);

        // Assert that the payment object has the expected values
        assertEquals("P123", payment.getPaymentId());
        assertEquals(Payment.paymentMethod.DebitCard, payment.getPaymentMethod());
        assertEquals(1500.00, payment.getAmount());
    }
}

