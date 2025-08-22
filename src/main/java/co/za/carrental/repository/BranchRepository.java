/*
 *
 * Author: Milani Ncana (216269369)
 * Date: 11 May 2025
 */

package co.za.carrental.repository;

import co.za.carrental.domain.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchRepository extends JpaRepository<Branch, String> {

    // Find branches whose address contains the given keyword (case-insensitive).
    List<Branch> findByAddressContainingIgnoreCase(String keyword);

    // Find branches by exact phone number
    List<Branch> findByPhone(String phone);

    // Find branches with addresses starting with the given prefix (case-insensitive)
    List<Branch> findByAddressStartingWithIgnoreCase(String prefix);

    // Return all branches ordered by address ascending
    List<Branch> findAllByOrderByAddressAsc();
}
