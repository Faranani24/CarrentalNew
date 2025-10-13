package co.za.carrental.repository; // package for repositories — handles DB access

import co.za.carrental.domain.AdminLogin; // import the entity
import org.springframework.data.jpa.repository.JpaRepository; // Spring Data JPA repository base
import org.springframework.stereotype.Repository; // marks it as a repository bean

import java.util.Optional; // wrapper type for handling nullable DB results

@Repository // tells Spring this is a Repository for dependency injection
public interface AdminLoginRepository extends JpaRepository<AdminLogin, String> {
    // JpaRepository<AdminLogin, String> means:
    // Entity = AdminLogin, Primary Key = String (adminId)

    // Custom finder method — Spring Data auto-generates SQL for this
    Optional<AdminLogin> findByUsername(String username);

    // Custom finder method — look up admin by email
    Optional<AdminLogin> findByEmail(String email);
}