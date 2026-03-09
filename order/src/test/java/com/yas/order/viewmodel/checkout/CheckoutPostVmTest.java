package com.yas.order.viewmodel.checkout;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CheckoutPostVmTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void testCheckoutPostVm_withValidData_shouldPass() {
        // Arrange
        CheckoutPostVm vm = new CheckoutPostVm(
                "test@example.com",
                "Note",
                "PROMO",
                "shipment-1",
                "payment-1",
                "address-1",
                List.of(createCheckoutItemPostVm())
        );

        // Act
        Set<ConstraintViolation<CheckoutPostVm>> violations = validator.validate(vm);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    void testCheckoutPostVm_whenCheckoutItemsIsEmpty_shouldFail() {
        // Arrange
        CheckoutPostVm vm = new CheckoutPostVm(
                "test@example.com",
                "Note",
                "PROMO",
                "shipment-1",
                "payment-1",
                "address-1",
                new ArrayList<>()
        );

        // Act
        Set<ConstraintViolation<CheckoutPostVm>> violations = validator.validate(vm);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().contains("must not be empty")));
    }

    @Test
    void testCheckoutPostVm_getters() {
        // Arrange
        CheckoutPostVm vm = new CheckoutPostVm(
                "test@example.com",
                "Test note",
                "SAVE20",
                "shipment-123",
                "payment-456",
                "address-789",
                List.of(createCheckoutItemPostVm())
        );

        // Assert
        assertEquals("test@example.com", vm.email());
        assertEquals("Test note", vm.note());
        assertEquals("SAVE20", vm.promotionCode());
        assertEquals("shipment-123", vm.shipmentMethodId());
        assertEquals("payment-456", vm.paymentMethodId());
        assertEquals("address-789", vm.shippingAddressId());
        assertEquals(1, vm.checkoutItemPostVms().size());
    }

    @Test
    void testCheckoutPostVm_equalsAndHashCode() {
        // Arrange
        List<CheckoutItemPostVm> items = List.of(createCheckoutItemPostVm());
        CheckoutPostVm vm1 = new CheckoutPostVm(
                "test@example.com",
                "Note",
                "PROMO",
                "shipment-1",
                "payment-1",
                "address-1",
                items
        );
        CheckoutPostVm vm2 = new CheckoutPostVm(
                "test@example.com",
                "Note",
                "PROMO",
                "shipment-1",
                "payment-1",
                "address-1",
                items
        );

        // Assert
        assertEquals(vm1, vm2);
        assertEquals(vm1.hashCode(), vm2.hashCode());
    }

    @Test
    void testCheckoutPostVm_toString() {
        // Arrange
        CheckoutPostVm vm = new CheckoutPostVm(
                "test@example.com",
                "Note",
                "PROMO",
                "shipment-1",
                "payment-1",
                "address-1",
                List.of(createCheckoutItemPostVm())
        );

        // Act
        String result = vm.toString();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("test@example.com"));
    }

    @Test
    void testCheckoutPostVm_withNullOptionalFields() {
        // Act
        CheckoutPostVm vm = new CheckoutPostVm(
                null,
                null,
                null,
                null,
                null,
                null,
                List.of(createCheckoutItemPostVm())
        );

        // Act
        Set<ConstraintViolation<CheckoutPostVm>> violations = validator.validate(vm);

        // Assert - only checkoutItemPostVms is required
        assertTrue(violations.isEmpty());
    }

    private CheckoutItemPostVm createCheckoutItemPostVm() {
        return new CheckoutItemPostVm(1L, "Description", 5);
    }
}
