package com.yas.order.viewmodel.checkout;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CheckoutItemPostVmTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void testCheckoutItemPostVm_withValidData_shouldPass() {
        // Arrange
        CheckoutItemPostVm vm = new CheckoutItemPostVm(1L, "Description", 5);

        // Act
        Set<ConstraintViolation<CheckoutItemPostVm>> violations = validator.validate(vm);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    void testCheckoutItemPostVm_whenQuantityIsZero_shouldFail() {
        // Arrange
        CheckoutItemPostVm vm = new CheckoutItemPostVm(1L, "Description", 0);

        // Act
        Set<ConstraintViolation<CheckoutItemPostVm>> violations = validator.validate(vm);

        // Assert
        assertFalse(violations.isEmpty());
    }

    @Test
    void testCheckoutItemPostVm_whenQuantityIsNegative_shouldFail() {
        // Arrange
        CheckoutItemPostVm vm = new CheckoutItemPostVm(1L, "Description", -5);

        // Act
        Set<ConstraintViolation<CheckoutItemPostVm>> violations = validator.validate(vm);

        // Assert
        assertFalse(violations.isEmpty());
    }

    @Test
    void testCheckoutItemPostVm_getters() {
        // Arrange
        CheckoutItemPostVm vm = new CheckoutItemPostVm(100L, "Test Description", 10);

        // Assert
        assertEquals(100L, vm.productId());
        assertEquals("Test Description", vm.description());
        assertEquals(10, vm.quantity());
    }

    @Test
    void testCheckoutItemPostVm_equalsAndHashCode() {
        // Arrange
        CheckoutItemPostVm vm1 = new CheckoutItemPostVm(1L, "Desc", 5);
        CheckoutItemPostVm vm2 = new CheckoutItemPostVm(1L, "Desc", 5);

        // Assert
        assertEquals(vm1, vm2);
        assertEquals(vm1.hashCode(), vm2.hashCode());
    }

    @Test
    void testCheckoutItemPostVm_toString() {
        // Arrange
        CheckoutItemPostVm vm = new CheckoutItemPostVm(1L, "Description", 5);

        // Act
        String result = vm.toString();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("1"));
        assertTrue(result.contains("Description"));
        assertTrue(result.contains("5"));
    }

    @Test
    void testCheckoutItemPostVm_withNullProductId() {
        // Act
        CheckoutItemPostVm vm = new CheckoutItemPostVm(null, "Description", 5);

        // Act
        Set<ConstraintViolation<CheckoutItemPostVm>> violations = validator.validate(vm);

        // Assert - productId is not marked as required
        assertTrue(violations.isEmpty());
    }

    @Test
    void testCheckoutItemPostVm_withPositiveQuantity() {
        // Arrange
        CheckoutItemPostVm vm = new CheckoutItemPostVm(1L, "Description", 1);

        // Act
        Set<ConstraintViolation<CheckoutItemPostVm>> violations = validator.validate(vm);

        // Assert
        assertTrue(violations.isEmpty());
    }
}
