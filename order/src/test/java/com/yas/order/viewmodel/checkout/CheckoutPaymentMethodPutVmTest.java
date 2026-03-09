package com.yas.order.viewmodel.checkout;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckoutPaymentMethodPutVmTest {

    @Test
    void testCheckoutPaymentMethodPutVm_constructor() {
        // Act
        CheckoutPaymentMethodPutVm vm = new CheckoutPaymentMethodPutVm("payment-method-123");

        // Assert
        assertNotNull(vm);
        assertEquals("payment-method-123", vm.paymentMethodId());
    }

    @Test
    void testCheckoutPaymentMethodPutVm_getter() {
        // Arrange
        CheckoutPaymentMethodPutVm vm = new CheckoutPaymentMethodPutVm("payment-456");

        // Assert
        assertEquals("payment-456", vm.paymentMethodId());
    }

    @Test
    void testCheckoutPaymentMethodPutVm_equalsAndHashCode() {
        // Arrange
        CheckoutPaymentMethodPutVm vm1 = new CheckoutPaymentMethodPutVm("payment-123");
        CheckoutPaymentMethodPutVm vm2 = new CheckoutPaymentMethodPutVm("payment-123");

        // Assert
        assertEquals(vm1, vm2);
        assertEquals(vm1.hashCode(), vm2.hashCode());
    }

    @Test
    void testCheckoutPaymentMethodPutVm_notEquals() {
        // Arrange
        CheckoutPaymentMethodPutVm vm1 = new CheckoutPaymentMethodPutVm("payment-123");
        CheckoutPaymentMethodPutVm vm2 = new CheckoutPaymentMethodPutVm("payment-456");

        // Assert
        assertNotEquals(vm1, vm2);
    }

    @Test
    void testCheckoutPaymentMethodPutVm_toString() {
        // Arrange
        CheckoutPaymentMethodPutVm vm = new CheckoutPaymentMethodPutVm("payment-method-789");

        // Act
        String result = vm.toString();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("payment-method-789"));
    }

    @Test
    void testCheckoutPaymentMethodPutVm_withNullValue() {
        // Act
        CheckoutPaymentMethodPutVm vm = new CheckoutPaymentMethodPutVm(null);

        // Assert
        assertNotNull(vm);
        assertNull(vm.paymentMethodId());
    }
}
