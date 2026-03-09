package com.yas.tax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TaxClassTest {

    @Test
    void testBuilder_whenValidInputs_shouldCreateTaxClass() {
        // Act
        TaxClass taxClass = TaxClass.builder()
            .id(1L)
            .name("Standard Tax")
            .build();

        // Assert
        assertNotNull(taxClass);
        assertEquals(1L, taxClass.getId());
        assertEquals("Standard Tax", taxClass.getName());
    }

    @Test
    void testBuilder_whenMinimalInputs_shouldCreateTaxClass() {
        // Act
        TaxClass taxClass = TaxClass.builder()
            .name("Reduced Tax")
            .build();

        // Assert
        assertNotNull(taxClass);
        assertNull(taxClass.getId());
        assertEquals("Reduced Tax", taxClass.getName());
    }

    @Test
    void testNoArgsConstructor_shouldCreateEmptyTaxClass() {
        // Act
        TaxClass taxClass = new TaxClass();

        // Assert
        assertNotNull(taxClass);
        assertNull(taxClass.getId());
        assertNull(taxClass.getName());
    }

    @Test
    void testSetters_whenCalledWithValidValues_shouldSetFields() {
        // Arrange
        TaxClass taxClass = new TaxClass();

        // Act
        taxClass.setId(5L);
        taxClass.setName("Zero Tax");

        // Assert
        assertEquals(5L, taxClass.getId());
        assertEquals("Zero Tax", taxClass.getName());
    }

    @Test
    void testGetters_shouldReturnCorrectValues() {
        // Arrange
        TaxClass taxClass = TaxClass.builder()
            .id(10L)
            .name("Luxury Tax")
            .build();

        // Act & Assert
        assertEquals(10L, taxClass.getId());
        assertEquals("Luxury Tax", taxClass.getName());
    }

    @Test
    void testToString_shouldContainFields() {
        // Arrange
        TaxClass taxClass = TaxClass.builder()
            .id(1L)
            .name("Standard Tax")
            .build();

        // Act
        String result = taxClass.toString();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("TaxClass"));
    }
}
