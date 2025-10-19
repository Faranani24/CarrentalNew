package co.za.carrental.api.dto;

public class BookingResponse {

    private String bookingId;
    private String startDate;
    private String endDate;
    private Float totalCost;
    private String status;


    public BookingResponse() {}


    public BookingResponse(String bookingId, String startDate, String endDate, Float totalCost, String status) {
        this.bookingId = bookingId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalCost = totalCost;
        this.status = status;
    }


    public String getBookingId() { return bookingId; }
    public void setBookingId(String bookingId) { this.bookingId = bookingId; }

    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }

    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }

    public Float getTotalCost() { return totalCost; }
    public void setTotalCost(Float totalCost) { this.totalCost = totalCost; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
