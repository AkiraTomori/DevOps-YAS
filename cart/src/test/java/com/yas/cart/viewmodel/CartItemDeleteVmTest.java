package com.yas.cart.viewmodel;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CartItemDeleteVmTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testCartItemDeleteVm_builder() {
        // Arrange & Act
        CartItemDeleteVm cartItemDeleteVm = CartItemDeleteVm.builder()
            .productId(1L)
            .quantity(5)
            .build();

        // Assert
        assertNotNull(cartItemDeleteVm);
        assertEquals(1L, cartItemDeleteVm.productId());
        assertEquals(5, cartItemDeleteVm.quantity());
    }

    @Test
    void testCartItemDeleteVm_constructor() {
        // Arrange & Act
        CartItemDeleteVm cartItemDeleteVm = new CartItemDeleteVm(1L, 5);

        // Assert
        assertNotNull(cartItemDeleteVm);
        assertEquals(1L, cartItemDeleteVm.productId());
        assertEquals(5, cartItemDeleteVm.quantity());
    }

    @Test
    void testCartItemDeleteVm_validation_withValidData() {
        // Arrange
        CartItemDeleteVm cartItemDeleteVm = CartItemDeleteVm.builder()
            .productId(1L)
            .quantity(5)
            .build();

        // Act
        Set<ConstraintViolation<CartItemDeleteVm>> violations = validator.validate(cartItemDeleteVm);

        // Assert
        assertTrue(violations.isEmpty(), "Valid CartItemDeleteVm should have no violations");
    }

    @Test
    void testCartItemDeleteVm_validation_withNullProductId() {
        // Arrange
        CartItemDeleteVm cartItemDeleteVm = CartItemDeleteVm.builder()
            .productId(null)
            .quantity(5)
            .build();

        // Act
        Set<ConstraintViolation<CartItemDeleteVm>> violations = validator.validate(cartItemDeleteVm);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
            .anyMatch(v -> v.getPropertyPath().toString().equals("productId")));
    }

    @Test
    void testCartItemDeleteVm_validation_withNullQuantity() {
        // Arrange
        CartItemDeleteVm cartItemDeleteVm = CartItemDeleteVm.builder()
            .productId(1L)
            .quantity(null)
            .build();

        // Act
        Set<ConstraintViolation<CartItemDeleteVm>> violations = validator.validate(cartItemDeleteVm);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
            .anyMatch(v -> v.getPropertyPath().toString().equals("quantity")));
    }

    @Test
    void testCartItemDeleteVm_validation_withQuantityLessThanOne() {
        // Arrange
        CartItemDeleteVm cartItemDeleteVm = CartItemDeleteVm.builder()
            .productId(1L)
            .quantity(0)
            .build();

        // Act
        Set<ConstraintViolation<CartItemDeleteVm>> violations = validator.validate(cartItemDeleteVm);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
            .anyMatch(v -> v.getPropertyPath().toString().equals("quantity")));
    }

    @Test
    void testCartItemDeleteVm_validation_withMinimumValidQuantity() {
        // Arrange
        CartItemDeleteVm cartItemDeleteVm = CartItemDeleteVm.builder()
            .productId(1L)
            .quantity(1)
            .build();

        // Act
        Set<ConstraintViolation<CartItemDeleteVm>> violations = validator.validate(cartItemDeleteVm);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    void testCartItemDeleteVm_validation_withLargeQuantity() {
        // Arrange
        CartItemDeleteVm cartItemDeleteVm = CartItemDeleteVm.builder()
            .productId(1L)
            .quantity(1000)
            .build();

        // Act
        Set<ConstraintViolation<CartItemDeleteVm>> violations = validator.validate(cartItemDeleteVm);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    void testCartItemDeleteVm_equals_withSameValues() {
        // Arrange
        CartItemDeleteVm vm1 = new CartItemDeleteVm(1L, 5);
        CartItemDeleteVm vm2 = new CartItemDeleteVm(1L, 5);

        // Act & Assert
        assertEquals(vm1, vm2);
    }

    @Test
    void testCartItemDeleteVm_equals_withDifferentValues() {
        // Arrange
        CartItemDeleteVm vm1 = new CartItemDeleteVm(1L, 5);
        CartItemDeleteVm vm2 = new CartItemDeleteVm(2L, 10);

        // Act & Assert
        assertNotEquals(vm1, vm2);
    }

    @Test
    void testCartItemDeleteVm_hashCode_consistency() {
        // Arrange
        CartItemDeleteVm vm1 = new CartItemDeleteVm(1L, 5);
        CartItemDeleteVm vm2 = new CartItemDeleteVm(1L, 5);

        // Act & Assert
        assertEquals(vm1.hashCode(), vm2.hashCode());
    }

    @Test
    void testCartItemDeleteVm_toString_containsFields() {
        // Arrange
        CartItemDeleteVm cartItemDeleteVm = new CartItemDeleteVm(1L, 5);

        // Act
        String toString = cartItemDeleteVm.toString();

        // Assert
        assertNotNull(toString);
        assertTrue(toString.contains("1"));
        assertTrue(toString.contains("5"));
    }
}
