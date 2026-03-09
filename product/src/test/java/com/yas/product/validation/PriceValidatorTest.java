package com.yas.product.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PriceValidatorTest {

    private PriceValidator priceValidator;
    private ConstraintValidatorContext context;

    @BeforeEach
    void setUp() {
        priceValidator = new PriceValidator();
        context = mock(ConstraintValidatorContext.class);
    }

    @Test
    void testIsValid_WithPositivePrice() {
        // When
        boolean result = priceValidator.isValid(99.99, context);

        // Then
        assertTrue(result);
    }

    @Test
    void testIsValid_WithZeroPrice() {
        // When
        boolean result = priceValidator.isValid(0.0, context);

        // Then
        assertTrue(result);
    }

    @Test
    void testIsValid_WithNegativePrice() {
        // When
        boolean result = priceValidator.isValid(-10.0, context);

        // Then
        assertFalse(result);
    }

    @Test
    void testIsValid_WithLargePrice() {
        // When
        boolean result = priceValidator.isValid(999999.99, context);

        // Then
        assertTrue(result);
    }

    @Test
    void testIsValid_WithVerySmallPrice() {
        // When
        boolean result = priceValidator.isValid(0.01, context);

        // Then
        assertTrue(result);
    }

    @Test
    void testIsValid_WithNegativeDecimal() {
        // When
        boolean result = priceValidator.isValid(-0.01, context);

        // Then
        assertFalse(result);
    }
}
