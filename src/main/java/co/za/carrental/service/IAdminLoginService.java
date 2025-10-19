package co.za.carrental.service; // package for services — business logic layer

import co.za.carrental.domain.AdminLogin; // import entity
import java.util.List; // for returning multiple AdminLogins
import java.util.Optional; // for safe nullable handling

public interface IAdminLoginService {

    AdminLogin create(AdminLogin adminLogin);


    Optional<AdminLogin> read(String adminId);


    AdminLogin update(AdminLogin adminLogin);


    void delete(String adminId);


    List<AdminLogin> getAll();


    Optional<AdminLogin> authenticate(String username, String rawPassword);
}