package com.yas.cart.viewmodel;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CartItemPostVmTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testCartItemPostVm_builder() {
        // Arrange & Act
        CartItemPostVm cartItemPostVm = CartItemPostVm.builder()
            .productId(1L)
            .quantity(5)
            .build();

        // Assert
        assertNotNull(cartItemPostVm);
        assertEquals(1L, cartItemPostVm.productId());
        assertEquals(5, cartItemPostVm.quantity());
    }

    @Test
    void testCartItemPostVm_constructor() {
        // Arrange & Act
        CartItemPostVm cartItemPostVm = new CartItemPostVm(2L, 10);

        // Assert
        assertNotNull(cartItemPostVm);
        assertEquals(2L, cartItemPostVm.productId());
        assertEquals(10, cartItemPostVm.quantity());
    }

    @Test
    void testCartItemPostVm_validation_withValidData() {
        // Arrange
        CartItemPostVm cartItemPostVm = CartItemPostVm.builder()
            .productId(1L)
            .quantity(5)
            .build();

        // Act
        Set<ConstraintViolation<CartItemPostVm>> violations = validator.validate(cartItemPostVm);

        // Assert
        assertTrue(violations.isEmpty(), "Valid CartItemPostVm should have no violations");
    }

    @Test
    void testCartItemPostVm_validation_withNullProductId() {
        // Arrange
        CartItemPostVm cartItemPostVm = CartItemPostVm.builder()
            .productId(null)
            .quantity(5)
            .build();

        // Act
        Set<ConstraintViolation<CartItemPostVm>> violations = validator.validate(cartItemPostVm);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
            .anyMatch(v -> v.getPropertyPath().toString().equals("productId")));
    }

    @Test
    void testCartItemPostVm_validation_withNullQuantity() {
        // Arrange
        CartItemPostVm cartItemPostVm = CartItemPostVm.builder()
            .productId(1L)
            .quantity(null)
            .build();

        // Act
        Set<ConstraintViolation<CartItemPostVm>> violations = validator.validate(cartItemPostVm);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
            .anyMatch(v -> v.getPropertyPath().toString().equals("quantity")));
    }

    @Test
    void testCartItemPostVm_validation_withQuantityLessThanOne() {
        // Arrange
        CartItemPostVm cartItemPostVm = CartItemPostVm.builder()
            .productId(1L)
            .quantity(0)
            .build();

        // Act
        Set<ConstraintViolation<CartItemPostVm>> violations = validator.validate(cartItemPostVm);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
            .anyMatch(v -> v.getPropertyPath().toString().equals("quantity")));
    }

    @Test
    void testCartItemPostVm_validation_withNegativeQuantity() {
        // Arrange
        CartItemPostVm cartItemPostVm = CartItemPostVm.builder()
            .productId(1L)
            .quantity(-5)
            .build();

        // Act
        Set<ConstraintViolation<CartItemPostVm>> violations = validator.validate(cartItemPostVm);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
            .anyMatch(v -> v.getPropertyPath().toString().equals("quantity")));
    }

    @Test
    void testCartItemPostVm_validation_withMinimumValidQuantity() {
        // Arrange
        CartItemPostVm cartItemPostVm = CartItemPostVm.builder()
            .productId(1L)
            .quantity(1)
            .build();

        // Act
        Set<ConstraintViolation<CartItemPostVm>> violations = validator.validate(cartItemPostVm);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    void testCartItemPostVm_validation_withLargeQuantity() {
        // Arrange
        CartItemPostVm cartItemPostVm = CartItemPostVm.builder()
            .productId(1L)
            .quantity(1000)
            .build();

        // Act
        Set<ConstraintViolation<CartItemPostVm>> violations = validator.validate(cartItemPostVm);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    void testCartItemPostVm_equals_withSameValues() {
        // Arrange
        CartItemPostVm vm1 = new CartItemPostVm(1L, 5);
        CartItemPostVm vm2 = new CartItemPostVm(1L, 5);

        // Act & Assert
        assertEquals(vm1, vm2);
    }

    @Test
    void testCartItemPostVm_equals_withDifferentProductId() {
        // Arrange
        CartItemPostVm vm1 = new CartItemPostVm(1L, 5);
        CartItemPostVm vm2 = new CartItemPostVm(2L, 5);

        // Act & Assert
        assertNotEquals(vm1, vm2);
    }

    @Test
    void testCartItemPostVm_equals_withDifferentQuantity() {
        // Arrange
        CartItemPostVm vm1 = new CartItemPostVm(1L, 5);
        CartItemPostVm vm2 = new CartItemPostVm(1L, 10);

        // Act & Assert
        assertNotEquals(vm1, vm2);
    }

    @Test
    void testCartItemPostVm_hashCode_consistency() {
        // Arrange
        CartItemPostVm vm1 = new CartItemPostVm(1L, 5);
        CartItemPostVm vm2 = new CartItemPostVm(1L, 5);

        // Act & Assert
        assertEquals(vm1.hashCode(), vm2.hashCode());
    }

    @Test
    void testCartItemPostVm_toString_containsFields() {
        // Arrange
        CartItemPostVm cartItemPostVm = new CartItemPostVm(1L, 5);

        // Act
        String toString = cartItemPostVm.toString();

        // Assert
        assertNotNull(toString);
        assertTrue(toString.contains("1"));
        assertTrue(toString.contains("5"));
    }
}
