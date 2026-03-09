package com.yas.order.viewmodel.order;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaymentOrderStatusVmTest {

    @Test
    void testPaymentOrderStatusVm_builder() {
        // Act
        PaymentOrderStatusVm vm = PaymentOrderStatusVm.builder()
                .orderId(1L)
                .orderStatus("COMPLETED")
                .paymentId(100L)
                .paymentStatus("PAID")
                .build();

        // Assert
        assertNotNull(vm);
        assertEquals(1L, vm.orderId());
        assertEquals("COMPLETED", vm.orderStatus());
        assertEquals(100L, vm.paymentId());
        assertEquals("PAID", vm.paymentStatus());
    }

    @Test
    void testPaymentOrderStatusVm_getters() {
        // Arrange
        PaymentOrderStatusVm vm = PaymentOrderStatusVm.builder()
                .orderId(50L)
                .orderStatus("PENDING")
                .paymentId(200L)
                .paymentStatus("PROCESSING")
                .build();

        // Assert
        assertEquals(50L, vm.orderId());
        assertEquals("PENDING", vm.orderStatus());
        assertEquals(200L, vm.paymentId());
        assertEquals("PROCESSING", vm.paymentStatus());
    }

    @Test
    void testPaymentOrderStatusVm_equalsAndHashCode() {
        // Arrange
        PaymentOrderStatusVm vm1 = PaymentOrderStatusVm.builder()
                .orderId(1L)
                .orderStatus("COMPLETED")
                .paymentId(100L)
                .paymentStatus("PAID")
                .build();

        PaymentOrderStatusVm vm2 = PaymentOrderStatusVm.builder()
                .orderId(1L)
                .orderStatus("COMPLETED")
                .paymentId(100L)
                .paymentStatus("PAID")
                .build();

        // Assert
        assertEquals(vm1, vm2);
        assertEquals(vm1.hashCode(), vm2.hashCode());
    }

    @Test
    void testPaymentOrderStatusVm_toString() {
        // Arrange
        PaymentOrderStatusVm vm = PaymentOrderStatusVm.builder()
                .orderId(1L)
                .orderStatus("COMPLETED")
                .build();

        // Act
        String result = vm.toString();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("1"));
        assertTrue(result.contains("COMPLETED"));
    }

    @Test
    void testPaymentOrderStatusVm_withNullValues() {
        // Act
        PaymentOrderStatusVm vm = PaymentOrderStatusVm.builder().build();

        // Assert
        assertNotNull(vm);
        assertNull(vm.orderId());
        assertNull(vm.orderStatus());
        assertNull(vm.paymentId());
        assertNull(vm.paymentStatus());
    }
}
