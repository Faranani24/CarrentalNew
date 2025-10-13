package co.za.carrental.factory; // package for factories — they create domain objects in a controlled way

import co.za.carrental.domain.AdminLogin; // import the entity we're building
import java.util.UUID; // used to generate unique IDs
import java.security.MessageDigest; // for hashing passwords (simple example; consider BCrypt in real apps)
import java.security.NoSuchAlgorithmException; // exception if hashing algorithm not available
import java.nio.charset.StandardCharsets; // character encoding for hashing

public class AdminLoginFactory { // Factory class for creating AdminLogin instances

    // Private constructor to prevent instantiation of this factory (static methods only)
    private AdminLoginFactory() {
    }

    /**
     * Create an AdminLogin object with a new UUID as ID.
     * This ensures IDs are unique and consistent.
     *
     * @param username raw username input
     * @param email raw email input
     * @param rawPassword raw password (will be hashed before storing)
     * @param role the role string (e.g., ADMIN, SUPER_ADMIN)
     * @return AdminLogin instance with UUID and hashed password
     */
    public static AdminLogin createAdminLogin(String username, String email, String rawPassword, String role) {
        String adminId = UUID.randomUUID().toString(); // generate unique ID

        String passwordHash = hashPassword(rawPassword); // hash raw password before storing

        // return a new AdminLogin entity with populated fields
        return new AdminLogin(adminId, username, email, passwordHash, role);
    }

    /**
     * Hashes the password using SHA-256 for demonstration.
     * ⚠️ In real-world apps, use BCrypt/Argon2 with salt for stronger security.
     *
     * @param password raw password
     * @return SHA-256 hashed string in hex
     */
    private static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256"); // initialize hashing algorithm
            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8)); // hash password
            return bytesToHex(encodedHash); // convert to hex string for DB storage
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error: Unable to hash password", e); // wrap checked exception
        }
    }

    /**
     * Converts a byte array to a hex string.
     *
     * @param hash byte array from hashing
     * @return hex string
     */
    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(); // builder for efficiency
        for (byte b : hash) { // loop through bytes
            String hex = Integer.toHexString(0xff & b); // convert to hex
            if (hex.length() == 1) hexString.append('0'); // pad if needed
            hexString.append(hex); // append to result
        }
        return hexString.toString(); // return final string
    }
}