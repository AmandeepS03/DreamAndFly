package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import storage.Capsula;

class CapsulaTest {

    private Capsula capsula;

    @BeforeEach
    void setUp() {
        capsula = new Capsula(1, 9.99f, "Studio");
    }

    @Test
    void testDefaultConstructor() {
        Capsula defaultCapsula = new Capsula();
        assertNotNull(defaultCapsula);
    }

    @Test
    void testParameterizedConstructor() {
        assertEquals(1, capsula.getId());
        assertEquals(9.99f, capsula.getPrezzo_orario());
        assertEquals("Studio", capsula.getTipologia());
    }

    @Test
    void testGetId() {
        assertEquals(1, capsula.getId());
    }

    @Test
    void testSetId() {
        capsula.setId(2);
        assertEquals(2, capsula.getId());
    }

    @Test
    void testGetPrezzo_orario() {
        assertEquals(9.99f, capsula.getPrezzo_orario());
    }

    @Test
    void testSetPrezzo_orario() {
        capsula.setPrezzo_orario(15.50f);
        assertEquals(15.50f, capsula.getPrezzo_orario());
    }

    @Test
    void testGetTipologia() {
        assertEquals("Studio", capsula.getTipologia());
    }

    @Test
    void testSetTipologia() {
        capsula.setTipologia("Ufficio");
        assertEquals("Ufficio", capsula.getTipologia());
    }
}
