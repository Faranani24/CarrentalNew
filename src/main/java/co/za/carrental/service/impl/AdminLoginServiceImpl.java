package co.za.carrental.service.impl;

import co.za.carrental.domain.AdminLogin; // import entity
import co.za.carrental.repository.AdminLoginRepository; // import repository
import co.za.carrental.service.IAdminLoginService; // import service interface
import org.springframework.beans.factory.annotation.Autowired; // dependency injection
import org.springframework.stereotype.Service; // marks this as service bean

import java.security.MessageDigest; // for password hashing
import java.security.NoSuchAlgorithmException; // hashing exception
import java.nio.charset.StandardCharsets; // encoding
import java.util.List; // return list of admins
import java.util.Optional; // optional wrapper

@Service
public class AdminLoginServiceImpl implements IAdminLoginService {

    private final AdminLoginRepository repository;

    @Autowired
    public AdminLoginServiceImpl(AdminLoginRepository repository) {
        this.repository = repository;
    }

    @Override
    public AdminLogin create(AdminLogin adminLogin) {
        return repository.save(adminLogin);
    }

    @Override
    public Optional<AdminLogin> read(String adminId) {
        return repository.findById(adminId);
    }

    @Override
    public AdminLogin update(AdminLogin adminLogin) {

        return repository.save(adminLogin);
    }

    @Override
    public void delete(String adminId) {
        repository.deleteById(adminId);
    }

    @Override
    public List<AdminLogin> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<AdminLogin> authenticate(String username, String rawPassword) {

        Optional<AdminLogin> adminOpt = repository.findByUsername(username);


        return adminOpt.filter(admin ->
                admin.getPasswordHash().equals(hashPassword(rawPassword)));
    }


    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedHash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error: Unable to hash password", e);
        }
    }

    private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}