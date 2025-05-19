package co.za.carrental.factory;
import co.za.carrental.domain.Admin;

public class AdminFactory {
    public static Admin createAdmin(String adminId, String password, String email){
        return new Admin.Builder()
                .setAdminId(adminId)
                .setEmail(email)
                .setPassword(password)
                .buiild();
    }
}
