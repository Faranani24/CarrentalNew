package co.za.carrental.domain;

import jakarta.persistence.Entity; // JPA annotation to mark this class as a persistent entity
import jakarta.persistence.Table; // JPA annotation to optionally set the DB table name
import jakarta.persistence.Id; // JPA annotation to mark the primary key field
import jakarta.persistence.Column; // JPA annotation to configure column properties
import jakarta.persistence.GenerationType; // enum for ID generation strategies (not used here; factory will set IDs)
import java.util.Objects; // Utility for equals/hashCode implementations

@Entity
@Table(name = "admin_login")
public class AdminLogin {

    @Id
    @Column(name = "admin_id", nullable = false, updatable = false)
    private String adminId;

    @Column(name = "username", nullable = false, unique = true, length = 100)
    private String username;

    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(name = "role", nullable = false, length = 50)
    private String role;


    protected AdminLogin() {
    }


    public AdminLogin(String adminId,
                      String username,
                      String email,
                      String passwordHash,
                      String role) {
        this.adminId = adminId;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
    }


    public String getAdminId() {
        return adminId;

    }
    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }


    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username; // Update username
    }


    public String getEmail() {
        return email; // Return email
    }


    public void setEmail(String email) {
        this.email = email; // Update email
    }


    public String getPasswordHash() {
        return passwordHash;
    }


    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }


    public String getRole() {
        return role;
    }


    public void setRole(String role) {
        this.role = role;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdminLogin that = (AdminLogin) o;
        return Objects.equals(adminId, that.adminId);
    }


    @Override
    public int hashCode() {
        return Objects.hash(adminId);
    }


    @Override
    public String toString() {
        return "AdminLogin{" +
                "adminId='" + adminId + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
