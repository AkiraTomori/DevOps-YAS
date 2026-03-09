package com.yas.order.viewmodel.checkout;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckoutStatusPutVmTest {

    @Test
    void testCheckoutStatusPutVm_constructor() {
        // Act
        CheckoutStatusPutVm vm = new CheckoutStatusPutVm("checkout-123", "COMPLETED");

        // Assert
        assertNotNull(vm);
        assertEquals("checkout-123", vm.checkoutId());
        assertEquals("COMPLETED", vm.checkoutStatus());
    }

    @Test
    void testCheckoutStatusPutVm_getters() {
        // Arrange
        CheckoutStatusPutVm vm = new CheckoutStatusPutVm("checkout-456", "PENDING");

        // Assert
        assertEquals("checkout-456", vm.checkoutId());
        assertEquals("PENDING", vm.checkoutStatus());
    }

    @Test
    void testCheckoutStatusPutVm_equalsAndHashCode() {
        // Arrange
        CheckoutStatusPutVm vm1 = new CheckoutStatusPutVm("checkout-123", "COMPLETED");
        CheckoutStatusPutVm vm2 = new CheckoutStatusPutVm("checkout-123", "COMPLETED");

        // Assert
        assertEquals(vm1, vm2);
        assertEquals(vm1.hashCode(), vm2.hashCode());
    }

    @Test
    void testCheckoutStatusPutVm_notEquals() {
        // Arrange
        CheckoutStatusPutVm vm1 = new CheckoutStatusPutVm("checkout-123", "COMPLETED");
        CheckoutStatusPutVm vm2 = new CheckoutStatusPutVm("checkout-456", "PENDING");

        // Assert
        assertNotEquals(vm1, vm2);
    }

    @Test
    void testCheckoutStatusPutVm_toString() {
        // Arrange
        CheckoutStatusPutVm vm = new CheckoutStatusPutVm("checkout-789", "PROCESSING");

        // Act
        String result = vm.toString();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("checkout-789"));
        assertTrue(result.contains("PROCESSING"));
    }

    @Test
    void testCheckoutStatusPutVm_withNullValues() {
        // Act
        CheckoutStatusPutVm vm = new CheckoutStatusPutVm(null, null);

        // Assert
        assertNotNull(vm);
        assertNull(vm.checkoutId());
        assertNull(vm.checkoutStatus());
    }
}
