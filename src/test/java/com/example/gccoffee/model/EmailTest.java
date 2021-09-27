package com.example.gccoffee.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class EmailTest {
    @Test
    public void testInvalidEmail() throws Exception {
        assertThrows(IllegalArgumentException.class, () -> {
            Email email = new Email("acccc");
        });
    }

    @Test
    public void testValidEmail() throws Exception {
        Email email = new Email("hello@gmail.com");
        assertEquals("hello@gmail.com", email.getAddress());
    }

    @Test
    public void testEqualEmail() throws Exception {
        Email email1 = new Email("hello@gmail.com");
        Email email2 = new Email("hello@gmail.com");
        assertEquals(email1, email2);
    }
}