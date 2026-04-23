package org.example;

import org.example.Exception.InvalidPeselException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @Test
    void shouldCreateCustomerWithValidPesel() {
        Customer customer = new Customer("Jan", "Kowalski", "12345678901");

        assertEquals("Jan", customer.getFirstName());
        assertEquals("Kowalski", customer.getLastName());
        assertEquals("12345678901", customer.getPesel());
    }

    @Test
    void shouldThrowExceptionForTooShortPesel() {
        assertThrows(InvalidPeselException.class, () -> {
            new Customer("Jan", "Kowalski", "12345");
        });
    }
}