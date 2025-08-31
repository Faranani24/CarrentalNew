package co.za.carrental.factory;

import co.za.carrental.domain.Admin;

public class AdminFactory {
    public static Admin createAdmin(String adminId, String email, String password) {
        return new Admin.Builder()
                .setAdminId(adminId)
                .setEmail(email)
                .setPassword(password)
                .build();
    }
}
