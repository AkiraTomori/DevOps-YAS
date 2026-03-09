package com.yas.order.viewmodel.order;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class OrderItemGetVmTest {

    @Test
    void testOrderItemGetVm_constructor() {
        // Act
        OrderItemGetVm vm = new OrderItemGetVm(
                1L,
                100L,
                "Test Product",
                5,
                BigDecimal.valueOf(50.00),
                BigDecimal.valueOf(5.00),
                BigDecimal.valueOf(2.50)
        );

        // Assert
        assertNotNull(vm);
        assertEquals(1L, vm.id());
        assertEquals(100L, vm.productId());
        assertEquals("Test Product", vm.productName());
        assertEquals(5, vm.quantity());
        assertEquals(BigDecimal.valueOf(50.00), vm.productPrice());
        assertEquals(BigDecimal.valueOf(5.00), vm.discountAmount());
        assertEquals(BigDecimal.valueOf(2.50), vm.taxAmount());
    }

    @Test
    void testOrderItemGetVm_getters() {
        // Arrange
        OrderItemGetVm vm = new OrderItemGetVm(
                5L,
                200L,
                "Product Name",
                10,
                BigDecimal.valueOf(99.99),
                BigDecimal.valueOf(10.00),
                BigDecimal.valueOf(5.00)
        );

        // Assert
        assertEquals(5L, vm.id());
        assertEquals(200L, vm.productId());
        assertEquals("Product Name", vm.productName());
        assertEquals(10, vm.quantity());
        assertEquals(BigDecimal.valueOf(99.99), vm.productPrice());
        assertEquals(BigDecimal.valueOf(10.00), vm.discountAmount());
        assertEquals(BigDecimal.valueOf(5.00), vm.taxAmount());
    }

    @Test
    void testOrderItemGetVm_equalsAndHashCode() {
        // Arrange
        OrderItemGetVm vm1 = new OrderItemGetVm(
                1L,
                100L,
                "Product",
                5,
                BigDecimal.valueOf(50.00),
                BigDecimal.valueOf(5.00),
                BigDecimal.valueOf(2.50)
        );

        OrderItemGetVm vm2 = new OrderItemGetVm(
                1L,
                100L,
                "Product",
                5,
                BigDecimal.valueOf(50.00),
                BigDecimal.valueOf(5.00),
                BigDecimal.valueOf(2.50)
        );

        // Assert
        assertEquals(vm1, vm2);
        assertEquals(vm1.hashCode(), vm2.hashCode());
    }

    @Test
    void testOrderItemGetVm_toString() {
        // Arrange
        OrderItemGetVm vm = new OrderItemGetVm(
                1L,
                100L,
                "Product",
                5,
                BigDecimal.valueOf(50.00),
                null,
                null
        );

        // Act
        String result = vm.toString();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("1"));
        assertTrue(result.contains("100"));
        assertTrue(result.contains("Product"));
    }

    @Test
    void testOrderItemGetVm_withNullValues() {
        // Act
        OrderItemGetVm vm = new OrderItemGetVm(
                1L,
                null,
                null,
                0,
                null,
                null,
                null
        );

        // Assert
        assertNotNull(vm);
        assertEquals(1L, vm.id());
        assertNull(vm.productId());
        assertNull(vm.productName());
        assertEquals(0, vm.quantity());
    }
}
