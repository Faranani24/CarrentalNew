/*CustomerFactoryTest.java
CustomerFactoryTest
Lance Anthony Franks
17 May 2025
 */

package co.za.carrental.factory;

import co.za.carrental.domain.Customer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerFactoryTest {

    @Test
    void createCustomer() {

        Customer customer = CustomerFactory.buildCustomer(
                "John", "Adams", "adamsjohn@gmail.com", "password123",
                "0823456789", "CF54321"
        );

        assertNotNull(customer);
        assertEquals("John", customer.getFirstName());
        assertEquals("Adams", customer.getLastName());
        assertEquals("CF54321", customer.getLicense());
    }
}