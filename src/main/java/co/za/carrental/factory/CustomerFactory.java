package co.za.carrental.factory;

import co.za.carrental.domain.Customer;

import java.util.List;

public class CustomerFactory {

    public static Customer createCustomer(String customerId, String firstName, String lastName, String email, String password, String phone, String licenseNumber, List<String> paymentMethods) {

        return new Customer.Builder()
                .setCustomerId(customerId)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .setPassword(password)
                .setPhone(phone)
                .setLicenseNumber(licenseNumber)
                .setPaymentMethods(paymentMethods)
                .build();
    }


}
