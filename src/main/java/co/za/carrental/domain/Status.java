package co.za.carrental.domain;
// Enum representing the status of a car in the rental system
public enum Status {
    AVAILABLE,
    RENTED,
    UNDER_MAINTENANCE,
    RESERVED,
    OUT_OF_SERVICE;

    public static Status fromString(String status) {
        for (Status carStatus : Status.values()) {
            if (carStatus.name().equalsIgnoreCase(status)) {
                return carStatus;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + status);
    }
}
