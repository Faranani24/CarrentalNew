package co.za.carrental.domain;

public enum CarStatus {
    AVAILABLE,
    RENTED,
    UNDER_MAINTENANCE,
    RESERVED,
    OUT_OF_SERVICE;

    public static CarStatus fromString(String status) {
        for (CarStatus carStatus : CarStatus.values()) {
            if (carStatus.name().equalsIgnoreCase(status)) {
                return carStatus;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + status);
    }
}
