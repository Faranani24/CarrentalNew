package co.za.carrental.factory;

import java.util.UUID;

import co.za.carrental.domain.Branch;

/**
 * BranchFactory.java
 * Branch entity class for car rental system
 * Author: Milani Ncana (216269369)
 *
 */

public class BranchFactory {

    public static Branch createBranch(String address, String phone) {
        // Generate a unique branch ID using UUID or a custom strategy
        String branchId = "BR-" + UUID.randomUUID();

        // Optional basic validation
        if (address == null || address.trim().isEmpty())
            throw new IllegalArgumentException("Address is required.");
        if (phone == null || phone.trim().isEmpty())

            throw new IllegalArgumentException("Phone number is required.");

        return new Branch.Builder()
                .branchId(branchId)
                .address(address)
                .phone(phone)
                .build();
    }

}
