/*
 *
 * Author: Milani Ncana (216269369)
 * Date: 11 May 2025
 */

package co.za.carrental.factory;

import co.za.carrental.domain.Branch;

import java.util.UUID;

/*
 * Factory for creating Branch domain objects. Validates required fields and
 * generates a UUID-based branchId.
 */
public class BranchFactory {

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


    public static Branch buildBranch(String address) {
        throw new UnsupportedOperationException(
                "This method is deprecated for persistence. Use buildBranch(address, phone) instead.");
    }

}
