package com.yas.order.viewmodel.order;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class OrderItemVmTest {

    @Test
    void testOrderItemVm_builder() {
        // Act
        OrderItemVm vm = OrderItemVm.builder()
                .id(1L)
                .productId(100L)
                .productName("Test Product")
                .quantity(5)
                .productPrice(BigDecimal.valueOf(50.00))
                .note("Item note")
                .discountAmount(BigDecimal.valueOf(5.00))
                .taxAmount(BigDecimal.valueOf(2.50))
                .taxPercent(BigDecimal.valueOf(5.00))
                .orderId(10L)
                .build();

        // Assert
        assertNotNull(vm);
        assertEquals(1L, vm.id());
        assertEquals(100L, vm.productId());
        assertEquals("Test Product", vm.productName());
        assertEquals(5, vm.quantity());
        assertEquals(BigDecimal.valueOf(50.00), vm.productPrice());
        assertEquals("Item note", vm.note());
        assertEquals(BigDecimal.valueOf(5.00), vm.discountAmount());
        assertEquals(BigDecimal.valueOf(2.50), vm.taxAmount());
        assertEquals(BigDecimal.valueOf(5.00), vm.taxPercent());
        assertEquals(10L, vm.orderId());
    }

    @Test
    void testOrderItemVm_getters() {
        // Arrange
        OrderItemVm vm = OrderItemVm.builder()
                .id(5L)
                .productId(200L)
                .productName("Product Name")
                .quantity(10)
                .productPrice(BigDecimal.valueOf(99.99))
                .note("Note")
                .discountAmount(BigDecimal.valueOf(10.00))
                .taxAmount(BigDecimal.valueOf(5.00))
                .taxPercent(BigDecimal.valueOf(5.00))
                .orderId(20L)
                .build();

        // Assert
        assertEquals(5L, vm.id());
        assertEquals(200L, vm.productId());
        assertEquals("Product Name", vm.productName());
        assertEquals(10, vm.quantity());
        assertEquals(BigDecimal.valueOf(99.99), vm.productPrice());
        assertEquals("Note", vm.note());
        assertEquals(BigDecimal.valueOf(10.00), vm.discountAmount());
        assertEquals(BigDecimal.valueOf(5.00), vm.taxAmount());
        assertEquals(BigDecimal.valueOf(5.00), vm.taxPercent());
        assertEquals(20L, vm.orderId());
    }

    @Test
    void testOrderItemVm_equalsAndHashCode() {
        // Arrange
        OrderItemVm vm1 = OrderItemVm.builder()
                .id(1L)
                .productId(100L)
                .productName("Product")
                .quantity(5)
                .build();

        OrderItemVm vm2 = OrderItemVm.builder()
                .id(1L)
                .productId(100L)
                .productName("Product")
                .quantity(5)
                .build();

        // Assert
        assertEquals(vm1, vm2);
        assertEquals(vm1.hashCode(), vm2.hashCode());
    }

    @Test
    void testOrderItemVm_toString() {
        // Arrange
        OrderItemVm vm = OrderItemVm.builder()
                .id(1L)
                .productId(100L)
                .productName("Product")
                .quantity(5)
                .build();

        // Act
        String result = vm.toString();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("1"));
        assertTrue(result.contains("100"));
        assertTrue(result.contains("Product"));
        assertTrue(result.contains("5"));
    }

    @Test
    void testOrderItemVm_withNullValues() {
        // Act
        OrderItemVm vm = OrderItemVm.builder()
                .id(1L)
                .quantity(0)
                .build();

        // Assert
        assertNotNull(vm);
        assertEquals(1L, vm.id());
        assertNull(vm.productId());
        assertNull(vm.productName());
        assertEquals(0, vm.quantity());
    }
}
