package net.booone.razersdk.color;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StaticColorTest {
    @Test
    void testColorCreation() {
        //given
        final StaticColor color = new StaticColor(0, 123, 255);

        //then
        assertEquals(0, color.red());
        assertEquals(123, color.green());
        assertEquals(255, color.blue());
        assertEquals("StaticColor[red=0, green=123, blue=255]", color.toString());
    }

    @Test
    void testInvalidColors() {
        assertThrows(IllegalArgumentException.class, () -> new StaticColor(-1, 1, 1));
        assertThrows(IllegalArgumentException.class, () -> new StaticColor(256, 1, 1));
        assertThrows(IllegalArgumentException.class, () -> new StaticColor(1, -112345, 1));
        assertThrows(IllegalArgumentException.class, () -> new StaticColor(1, 500, 1));
        assertThrows(IllegalArgumentException.class, () -> new StaticColor(1, 1, -100));
        assertThrows(IllegalArgumentException.class, () -> new StaticColor(1, 1, 4689));
    }
}