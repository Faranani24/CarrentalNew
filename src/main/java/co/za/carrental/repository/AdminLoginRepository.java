package co.za.carrental.repository; // package for repositories — handles DB access

import co.za.carrental.domain.AdminLogin; // import the entity
import org.springframework.data.jpa.repository.JpaRepository; // Spring Data JPA repository base
import org.springframework.stereotype.Repository; // marks it as a repository bean

import java.util.Optional; // wrapper type for handling nullable DB results

@Repository
public interface AdminLoginRepository extends JpaRepository<AdminLogin, String> {

    Optional<AdminLogin> findByUsername(String username);


    Optional<AdminLogin> findByEmail(String email);
}