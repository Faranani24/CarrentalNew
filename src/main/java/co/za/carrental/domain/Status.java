package co.za.carrental.domain;

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
