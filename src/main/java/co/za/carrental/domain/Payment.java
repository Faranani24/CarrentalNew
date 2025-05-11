/*  Review.java
    Author:Inam Jim (222086939)
    Date: 11 May 2025
 */
package co.za.carrental.domain;

import java.util.Date;

public class Payment extends Booking{

    //String paymentId  Float amount    Date paymentDate    Enum paymentMethod
    private String paymentId;
    private float amount;
    private Date paymentDate;
    private paymentMethod paymentMethod;

    public enum paymentMethod {Cash,Card,EFT}

    private Payment() {}

    private Payment(Builder builder) {
        this.paymentId = builder.paymentId;
        this.amount = builder.amount;
        this.paymentDate = builder.paymentDate;

    }

    public String getPaymentId() {
        return paymentId;
    }

    public float getAmount() {
        return amount;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public paymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId='" + paymentId + '\'' +
                ", amount=" + amount +
                ", paymentDate=" + paymentDate +
                '}';
    }

    public static class Builder {
        private String paymentId;
        private float amount;
        private Date paymentDate;
        private paymentMethod paymentMethod;

        public Builder setpaymentID(String paymentId) {
            this.paymentId = paymentId;
            return this;
        }

        public Builder setAmount(float amount) {
            this.amount = amount;
            return this;
        }

        public Builder setPaymentDate(Date paymentDate) {
            this.paymentDate = paymentDate;
            return this;
        }
        public Builder setPaymentMethod(paymentMethod paymentMethod) {
            this.paymentMethod = paymentMethod;
            return this;
        }

        public Payment build() {return new Payment(this);}

        public Builder copy(Payment payment) {

            if (payment != null) {
                this.paymentId = payment.paymentId;
                this.amount = payment.amount;
                this.paymentDate = payment.paymentDate;
                this.paymentMethod = payment.paymentMethod;
            }
            return this;
        }


    }
}
