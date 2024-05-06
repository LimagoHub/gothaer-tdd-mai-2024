package de.gothaer.collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StapelTest {

    private Stapel objectUnderTest;
    @BeforeEach
    void setUp() {
        objectUnderTest = new Stapel();
    }

    @Test
    @DisplayName("should return true when isEmpty called on empty Stapel")
    void isEmpty_emptyStack_returnsTrue(){
        // Arrange


        // Act
        boolean result = objectUnderTest.isEmpty();

        // Assert
        assertTrue(result);
    }

    @Test
    void isEmpty_notEmptyStack_returnsFalse() throws StapelException{
        // Arrange

        objectUnderTest.push(10);

        // Act
        boolean result = objectUnderTest.isEmpty();

        // Assert
        assertFalse(result);
    }
    @Test
    void isEmpty_fillUpToLimit_noExceptionIsThrown() throws StapelException{
        for (int i = 0; i < 10; i++) {
            assertDoesNotThrow(()->objectUnderTest.push(10));
        }
    }

    @Test
    void isEmpty_Overflow_throwsStapelException() throws StapelException{
        // Arrange
        for (int i = 0; i < 10; i++) {
            assertDoesNotThrow(()->objectUnderTest.push(10));
        }
        StapelException ex = assertThrows(StapelException.class, ()->objectUnderTest.push(1));
        assertEquals("Overflow", ex.getMessage());
    }

}