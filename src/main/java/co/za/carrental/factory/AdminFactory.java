package co.za.carrental.factory;

public class AdminFactory {
    public static Admin createAdmin(String adminId, String password, String email){
        return new Admin.Builder()
                .setAdminId(adminId)
                .setEmail(email)
                .setPassword(password)
                .build();
    }
}
/*
AdminFactory.java
Admin Factory
Thabiso Kama
18 May 2025
*/
