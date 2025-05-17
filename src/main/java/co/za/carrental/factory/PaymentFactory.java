package co.za.carrental.factory;

import co.za.carrental.domain.Payment;

public class PaymentFactory {

    public static Payment createPayment(String paymentId, String paymentMethod, double amount) {
        return new Payment.Builder()
                .setpaymentID(paymentId)
                .setPaymentMethod(Payment.paymentMethod.valueOf(paymentMethod))
                .setAmount((float) amount)
                .build();
    }

}
