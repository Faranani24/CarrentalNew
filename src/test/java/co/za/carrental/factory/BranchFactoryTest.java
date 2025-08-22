/*
 *
 * Author: Milani Ncana (216269369)
 * Date: 11 May 2025
 */

package co.za.carrental.factory;

import co.za.carrental.domain.Branch;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BranchFactoryTest {

    @Test
    void buildBranch_shouldCreateBranchWithGivenValues() {
        String address = "77A Jolobe Street, Luzuko Park";
        String phone = "074065671";

        Branch branch = BranchFactory.buildBranch(address, phone);

        assertNotNull(branch);
        assertNotNull(branch.getBranchId());
        assertTrue(branch.getBranchId().startsWith("BR-"));
        assertEquals(address, branch.getAddress());
        assertEquals(phone, branch.getPhone());
    }

    @Test
    void buildBranch_shouldThrowExceptionForNullAddress() {
        String phone = "074065671";
        assertThrows(IllegalArgumentException.class, () ->
                BranchFactory.buildBranch(null, phone)
        );
    }

    @Test
    void buildBranch_shouldThrowExceptionForEmptyAddress() {
        String phone = "074065671";
        assertThrows(IllegalArgumentException.class, () ->
                BranchFactory.buildBranch("   ", phone)
        );
    }

    @Test
    void buildBranch_shouldThrowExceptionForNullOrEmptyPhone() {
        String address = "77A Jolobe Street, Luzuko Park";

        assertThrows(IllegalArgumentException.class, () ->
                BranchFactory.buildBranch(address, null)
        );

        assertThrows(IllegalArgumentException.class, () ->
                BranchFactory.buildBranch(address, "   ")
        );
    }
}
