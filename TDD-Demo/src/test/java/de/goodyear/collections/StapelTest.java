package de.goodyear.collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StapelTest {

    private Stapel objectUnderTest;

    @BeforeEach
    void setUp() {
        objectUnderTest = new Stapel();
    }

    @Test
    void bla1() {
        assertTrue(objectUnderTest.isEmpty());

    }

    @Test
    void bla2() {
        objectUnderTest.push(10);
        assertFalse(objectUnderTest.isEmpty());

    }

}