package com.yas.cart.viewmodel;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CartItemPutVmTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testCartItemPutVm_constructor() {
        // Arrange & Act
        CartItemPutVm cartItemPutVm = new CartItemPutVm(10);

        // Assert
        assertNotNull(cartItemPutVm);
        assertEquals(10, cartItemPutVm.quantity());
    }

    @Test
    void testCartItemPutVm_validation_withValidQuantity() {
        // Arrange
        CartItemPutVm cartItemPutVm = new CartItemPutVm(5);

        // Act
        Set<ConstraintViolation<CartItemPutVm>> violations = validator.validate(cartItemPutVm);

        // Assert
        assertTrue(violations.isEmpty(), "Valid CartItemPutVm should have no violations");
    }

    @Test
    void testCartItemPutVm_validation_withNullQuantity() {
        // Arrange
        CartItemPutVm cartItemPutVm = new CartItemPutVm(null);

        // Act
        Set<ConstraintViolation<CartItemPutVm>> violations = validator.validate(cartItemPutVm);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
            .anyMatch(v -> v.getPropertyPath().toString().equals("quantity")));
    }

    @Test
    void testCartItemPutVm_validation_withQuantityLessThanOne() {
        // Arrange
        CartItemPutVm cartItemPutVm = new CartItemPutVm(0);

        // Act
        Set<ConstraintViolation<CartItemPutVm>> violations = validator.validate(cartItemPutVm);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
            .anyMatch(v -> v.getPropertyPath().toString().equals("quantity")));
    }

    @Test
    void testCartItemPutVm_validation_withNegativeQuantity() {
        // Arrange
        CartItemPutVm cartItemPutVm = new CartItemPutVm(-5);

        // Act
        Set<ConstraintViolation<CartItemPutVm>> violations = validator.validate(cartItemPutVm);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
            .anyMatch(v -> v.getPropertyPath().toString().equals("quantity")));
    }

    @Test
    void testCartItemPutVm_validation_withMinimumValidQuantity() {
        // Arrange
        CartItemPutVm cartItemPutVm = new CartItemPutVm(1);

        // Act
        Set<ConstraintViolation<CartItemPutVm>> violations = validator.validate(cartItemPutVm);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    void testCartItemPutVm_validation_withLargeQuantity() {
        // Arrange
        CartItemPutVm cartItemPutVm = new CartItemPutVm(1000);

        // Act
        Set<ConstraintViolation<CartItemPutVm>> violations = validator.validate(cartItemPutVm);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    void testCartItemPutVm_equals_withSameValue() {
        // Arrange
        CartItemPutVm vm1 = new CartItemPutVm(5);
        CartItemPutVm vm2 = new CartItemPutVm(5);

        // Act & Assert
        assertEquals(vm1, vm2);
    }

    @Test
    void testCartItemPutVm_equals_withDifferentValue() {
        // Arrange
        CartItemPutVm vm1 = new CartItemPutVm(5);
        CartItemPutVm vm2 = new CartItemPutVm(10);

        // Act & Assert
        assertNotEquals(vm1, vm2);
    }

    @Test
    void testCartItemPutVm_equals_withNull() {
        // Arrange
        CartItemPutVm vm = new CartItemPutVm(5);

        // Act & Assert
        assertNotEquals(null, vm);
    }

    @Test
    void testCartItemPutVm_equals_withDifferentClass() {
        // Arrange
        CartItemPutVm vm = new CartItemPutVm(5);
        String notAVm = "notAVm";

        // Act & Assert
        assertNotEquals(vm, notAVm);
    }

    @Test
    void testCartItemPutVm_hashCode_consistency() {
        // Arrange
        CartItemPutVm vm1 = new CartItemPutVm(5);
        CartItemPutVm vm2 = new CartItemPutVm(5);

        // Act & Assert
        assertEquals(vm1.hashCode(), vm2.hashCode());
    }

    @Test
    void testCartItemPutVm_hashCode_withDifferentValues() {
        // Arrange
        CartItemPutVm vm1 = new CartItemPutVm(5);
        CartItemPutVm vm2 = new CartItemPutVm(10);

        // Act & Assert
        assertNotEquals(vm1.hashCode(), vm2.hashCode());
    }

    @Test
    void testCartItemPutVm_toString_containsQuantity() {
        // Arrange
        CartItemPutVm cartItemPutVm = new CartItemPutVm(5);

        // Act
        String toString = cartItemPutVm.toString();

        // Assert
        assertNotNull(toString);
        assertTrue(toString.contains("5"));
    }
}
