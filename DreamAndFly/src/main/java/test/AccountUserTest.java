package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import storage.AccountUser;

class AccountUserTest2 {

    private AccountUser user;

    @BeforeEach
    void setUp() {
        user = new AccountUser("Mario", "Rossi", "mario@example.com", "pwd123", "3331234567", 1);
    }

    @Test
    void testAccountUserDefaultConstructor() {
        AccountUser emptyUser = new AccountUser();
        assertNotNull(emptyUser);
    }

    @Test
    void testAccountUserParameterizedConstructor() {
        assertEquals("Mario", user.getName());
        assertEquals("Rossi", user.getSurname());
        assertEquals("mario@example.com", user.getEmail());
        assertEquals("pwd123", user.getPassword());
        assertEquals("3331234567", user.getNumber());
        assertEquals(1, user.getRuolo());
    }

    @Test
    void testGetName() {
        assertEquals("Mario", user.getName());
    }

    @Test
    void testSetName() {
        user.setName("Luca");
        assertEquals("Luca", user.getName());
    }

    @Test
    void testGetSurname() {
        assertEquals("Rossi", user.getSurname());
    }

    @Test
    void testSetSurname() {
        user.setSurname("Verdi");
        assertEquals("Verdi", user.getSurname());
    }

    @Test
    void testGetEmail() {
        assertEquals("mario@example.com", user.getEmail());
    }

    @Test
    void testSetEmail() {
        user.setEmail("new@example.com");
        assertEquals("new@example.com", user.getEmail());
    }

    @Test
    void testGetPassword() {
        assertEquals("pwd123", user.getPassword());
    }

    @Test
    void testSetPassword() {
        user.setPassword("newpass");
        assertEquals("newpass", user.getPassword());
    }

    @Test
    void testGetNumber() {
        assertEquals("3331234567", user.getNumber());
    }

    @Test
    void testSetNumber() {
        user.setNumber("3209876543");
        assertEquals("3209876543", user.getNumber());
    }

    @Test
    void testGetRuolo() {
        assertEquals(1, user.getRuolo());
    }

    @Test
    void testSetRuolo() {
        user.setRuolo(2);
        assertEquals(2, user.getRuolo());
    }
}
