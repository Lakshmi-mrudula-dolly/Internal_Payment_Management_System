package tech.zeta.menu;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class AdminMenuTest {


    @Test
    void testTrueIsValidEmail() {
        assertTrue(new AdminMenu().isValidEmail("mrudula@gmail.com"));
    }

    @Test
    void testFalseIsValidEmail() {
        assertFalse(new AdminMenu().isValidEmail("mrudula@gmailcom"));
    }

    @Test
    void testTrueIsValidRole() {
        assertTrue(new AdminMenu().isValidRole("admin"));
    }

    @Test
    void testFalseIsValidRole() {
        assertFalse(new AdminMenu().isValidRole("Admin"));
    }
}