/*
 * Booking.java
 * Author: Lance Anthony Franks (230803865)
 * Date: 11 May 2025
 */

package co.za.carrental.domain;

import java.util.Date;

public class Booking {

    private String bookingId;
    private Date startDate;
    private Date endDate;
    private Float totalCost;
    private Enum status;

    private Booking(Builder builder) {
        this.bookingId = builder.bookingId;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.totalCost = builder.totalCost;
        this.status = builder.status;
    }
    public static class Builder {
        private String bookingId;
        private Date startDate;
        private Date endDate;
        private Float totalCost;
        private Enum status;

        public Builder setBookingId(String bookingId) {
            this.bookingId = bookingId;
            return this;
        }

        public Builder setStartDate(Date startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder setEndDate(Date endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder setTotalCost(Float totalCost) {
            this.totalCost = totalCost;
            return this;
        }

        public Builder setStatus(Enum status) {
            this.status = status;
            return this;
        }

        public Booking build() {
            return new Booking(this);
        }
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId='" + bookingId + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", totalCost=" + totalCost +
                ", status=" + status +
                '}';
    }
}
