package co.za.carrental.factory;

import co.za.carrental.domain.Branch;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * BranchFactoryTest.java
 * Branch entity class for car rental system
 * Author: Milani Ncana (216269369)
 *
 */

public class BranchFactoryTest {

    @Test
    public void testCreateBranch_success() {
        // Given
        String address = "77A Jolobe Street, Luzuko Park";
        String phone = "074065671";

        // When
        Branch branch = BranchFactory.createBranch(address, phone);

        // Then
        assertNotNull(branch);
        assertNotNull(branch.getBranchId());
        assertEquals(address, branch.getAddress());
        assertEquals(phone, branch.getPhone());

        System.out.println("✅ Branch created: " + branch);
    }

    @Test
    public void testCreateBranch_withEmptyAddress_shouldThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                BranchFactory.createBranch("", "074065671"));

        assertEquals("Address is required.", exception.getMessage());
    }

    @Test
    public void testCreateBranch_withNullPhone_shouldThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                BranchFactory.createBranch("77A Jolobe Street", null));

        assertEquals("Phone number is required.", exception.getMessage());
    }
}
