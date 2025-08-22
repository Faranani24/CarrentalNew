package co.za.carrental.factory;

import co.za.carrental.domain.Branch;

import java.util.UUID;

/**
 * BranchFactory
 * Author: Milani Ncana (216269369)
 * Date: 11 May 2025
 *
 * Factory for creating Branch domain objects. Validates required fields and
 * generates a UUID-based branchId.
 */
public class BranchFactory {

    /**
     * Build a fully populated Branch object.
     *
     * @param address Branch address (required, non-empty).
     * @param phone   Branch phone number (required, non-empty).
     * @return a Branch instance.
     * @throws IllegalArgumentException if validation fails.
     */
    public static Branch buildBranch(String address, String phone) {
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Address is required.");
        }
        if (phone == null || phone.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number is required.");
        }

        String branchId = "BR-" + UUID.randomUUID().toString();

        return new Branch.Builder()
                .setBranchId(branchId)
                .setAddress(address.trim())
                .setPhone(phone.trim())
                .build();
    }

    /**
     * Deprecated variant that builds a branch without a phone number.
     * Throws to prevent accidental creation of invalid persisted entities.
     *
     * Use {@link #buildBranch(String, String)} instead.
     */
    public static Branch buildBranch(String address) {
        throw new UnsupportedOperationException(
                "This method is deprecated for persistence. Use buildBranch(address, phone) instead.");
    }

}
