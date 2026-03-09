package com.yas.tax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TaxRateTest {

    @Test
    void testBuilder_whenValidInputs_shouldCreateTaxRate() {
        // Arrange
        TaxClass taxClass = TaxClass.builder()
            .id(1L)
            .name("Standard Tax")
            .build();

        // Act
        TaxRate taxRate = TaxRate.builder()
            .id(1L)
            .rate(10.5)
            .zipCode("12345")
            .stateOrProvinceId(1L)
            .countryId(100L)
            .taxClass(taxClass)
            .build();

        // Assert
        assertNotNull(taxRate);
        assertEquals(1L, taxRate.getId());
        assertEquals(10.5, taxRate.getRate());
        assertEquals("12345", taxRate.getZipCode());
        assertEquals(1L, taxRate.getStateOrProvinceId());
        assertEquals(100L, taxRate.getCountryId());
        assertEquals(taxClass, taxRate.getTaxClass());
    }

    @Test
    void testBuilder_whenMinimalInputs_shouldCreateTaxRate() {
        // Act
        TaxRate taxRate = TaxRate.builder()
            .rate(5.0)
            .build();

        // Assert
        assertNotNull(taxRate);
        assertNull(taxRate.getId());
        assertEquals(5.0, taxRate.getRate());
        assertNull(taxRate.getZipCode());
        assertNull(taxRate.getStateOrProvinceId());
        assertNull(taxRate.getCountryId());
        assertNull(taxRate.getTaxClass());
    }

    @Test
    void testNoArgsConstructor_shouldCreateEmptyTaxRate() {
        // Act
        TaxRate taxRate = new TaxRate();

        // Assert
        assertNotNull(taxRate);
        assertNull(taxRate.getId());
        assertNull(taxRate.getRate());
        assertNull(taxRate.getZipCode());
        assertNull(taxRate.getStateOrProvinceId());
        assertNull(taxRate.getCountryId());
        assertNull(taxRate.getTaxClass());
    }

    @Test
    void testSetters_whenCalledWithValidValues_shouldSetFields() {
        // Arrange
        TaxRate taxRate = new TaxRate();
        TaxClass taxClass = TaxClass.builder()
            .id(2L)
            .name("Reduced Tax")
            .build();

        // Act
        taxRate.setId(3L);
        taxRate.setRate(15.0);
        taxRate.setZipCode("67890");
        taxRate.setStateOrProvinceId(5L);
        taxRate.setCountryId(200L);
        taxRate.setTaxClass(taxClass);

        // Assert
        assertEquals(3L, taxRate.getId());
        assertEquals(15.0, taxRate.getRate());
        assertEquals("67890", taxRate.getZipCode());
        assertEquals(5L, taxRate.getStateOrProvinceId());
        assertEquals(200L, taxRate.getCountryId());
        assertEquals(taxClass, taxRate.getTaxClass());
    }

    @Test
    void testGetters_shouldReturnCorrectValues() {
        // Arrange
        TaxClass taxClass = TaxClass.builder()
            .id(1L)
            .name("Standard Tax")
            .build();

        TaxRate taxRate = TaxRate.builder()
            .id(10L)
            .rate(20.0)
            .zipCode("11111")
            .stateOrProvinceId(10L)
            .countryId(300L)
            .taxClass(taxClass)
            .build();

        // Act & Assert
        assertEquals(10L, taxRate.getId());
        assertEquals(20.0, taxRate.getRate());
        assertEquals("11111", taxRate.getZipCode());
        assertEquals(10L, taxRate.getStateOrProvinceId());
        assertEquals(300L, taxRate.getCountryId());
        assertEquals(taxClass, taxRate.getTaxClass());
    }

    @Test
    void testTaxClassRelationship_shouldMaintainReference() {
        // Arrange
        TaxClass taxClass = TaxClass.builder()
            .id(1L)
            .name("Standard Tax")
            .build();

        // Act
        TaxRate taxRate = TaxRate.builder()
            .id(1L)
            .rate(10.0)
            .taxClass(taxClass)
            .build();

        // Assert
        assertNotNull(taxRate.getTaxClass());
        assertEquals("Standard Tax", taxRate.getTaxClass().getName());
        assertEquals(1L, taxRate.getTaxClass().getId());
    }

    @Test
    void testToString_shouldContainFields() {
        // Arrange
        TaxClass taxClass = TaxClass.builder()
            .id(1L)
            .name("Standard Tax")
            .build();

        TaxRate taxRate = TaxRate.builder()
            .id(1L)
            .rate(10.5)
            .zipCode("12345")
            .taxClass(taxClass)
            .build();

        // Act
        String result = taxRate.toString();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("TaxRate"));
    }

    @Test
    void testRateUpdate_shouldAllowNewRate() {
        // Arrange
        TaxRate taxRate = TaxRate.builder()
            .id(1L)
            .rate(10.0)
            .build();

        // Act
        taxRate.setRate(25.5);

        // Assert
        assertEquals(25.5, taxRate.getRate());
    }

    @Test
    void testZipCodeUpdate_shouldAllowNewZipCode() {
        // Arrange
        TaxRate taxRate = TaxRate.builder()
            .id(1L)
            .zipCode("11111")
            .build();

        // Act
        taxRate.setZipCode("99999");

        // Assert
        assertEquals("99999", taxRate.getZipCode());
    }
}
