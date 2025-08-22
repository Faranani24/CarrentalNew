package co.za.carrental.service;

import co.za.carrental.domain.Branch;
import co.za.carrental.factory.BranchFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BranchServiceTest {

    @Autowired
    private IBranchService branchService;

    @Test
    void saveAndFindById_shouldPersistAndRetrieveBranch() {
        String address = "77A Jolobe Street, Luzuko Park";
        String phone = "074065671";

        Branch branch = BranchFactory.buildBranch(address, phone);
        Branch saved = branchService.save(branch);

        assertNotNull(saved);
        assertNotNull(saved.getBranchId());

        Optional<Branch> found = branchService.findById(saved.getBranchId());
        assertTrue(found.isPresent());
        assertEquals(saved.getBranchId(), found.get().getBranchId());
        assertEquals(address, found.get().getAddress());
        assertEquals(phone, found.get().getPhone());
    }

    @Test
    void update_shouldModifyBranch() {
        String address = "77A Jolobe Street, Luzuko Park";
        String phone = "074065671";

        Branch branch = BranchFactory.buildBranch(address, phone);
        Branch saved = branchService.save(branch);

        assertNotNull(saved);
        assertNotNull(saved.getBranchId());

        // Update address and phone
        saved.setAddress("99 Updated Road, Cape Town");
        saved.setPhone("0219998888");

        Branch updated = branchService.update(saved);
        assertNotNull(updated);
        assertEquals(saved.getBranchId(), updated.getBranchId());
        assertEquals("99 Updated Road, Cape Town", updated.getAddress());
        assertEquals("0219998888", updated.getPhone());
    }

    @Test
    void deleteById_shouldRemoveBranch() {
        String address = "77A Jolobe Street, Luzuko Park";
        String phone = "074065671";

        Branch branch = BranchFactory.buildBranch(address, phone);
        Branch saved = branchService.save(branch);

        assertNotNull(saved);
        assertNotNull(saved.getBranchId());

        branchService.deleteById(saved.getBranchId());

        Optional<Branch> found = branchService.findById(saved.getBranchId());
        assertFalse(found.isPresent());
    }
}