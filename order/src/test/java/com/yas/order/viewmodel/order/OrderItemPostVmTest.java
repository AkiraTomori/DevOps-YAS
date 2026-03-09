package com.yas.order.viewmodel.order;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class OrderItemPostVmTest {

    @Test
    void testOrderItemPostVm_builder() {
        // Arrange & Act
        OrderItemPostVm vm = OrderItemPostVm.builder()
                .productId(1L)
                .productName("Test Product")
                .quantity(5)
                .productPrice(BigDecimal.valueOf(50.00))
                .note("Product note")
                .discountAmount(BigDecimal.valueOf(5.00))
                .taxAmount(BigDecimal.valueOf(2.50))
                .taxPercent(BigDecimal.valueOf(5.00))
                .build();

        // Assert
        assertNotNull(vm);
        assertEquals(1L, vm.productId());
        assertEquals("Test Product", vm.productName());
        assertEquals(5, vm.quantity());
        assertEquals(BigDecimal.valueOf(50.00), vm.productPrice());
        assertEquals("Product note", vm.note());
        assertEquals(BigDecimal.valueOf(5.00), vm.discountAmount());
        assertEquals(BigDecimal.valueOf(2.50), vm.taxAmount());
        assertEquals(BigDecimal.valueOf(5.00), vm.taxPercent());
    }

    @Test
    void testOrderItemPostVm_getters() {
        // Arrange
        OrderItemPostVm vm = new OrderItemPostVm(
                100L,
                "Product Name",
                10,
                BigDecimal.valueOf(99.99),
                "Note",
                BigDecimal.valueOf(10.00),
                BigDecimal.valueOf(5.00),
                BigDecimal.valueOf(5.00)
        );

        // Assert
        assertEquals(100L, vm.productId());
        assertEquals("Product Name", vm.productName());
        assertEquals(10, vm.quantity());
        assertEquals(BigDecimal.valueOf(99.99), vm.productPrice());
        assertEquals("Note", vm.note());
        assertEquals(BigDecimal.valueOf(10.00), vm.discountAmount());
        assertEquals(BigDecimal.valueOf(5.00), vm.taxAmount());
        assertEquals(BigDecimal.valueOf(5.00), vm.taxPercent());
    }

    @Test
    void testOrderItemPostVm_equalsAndHashCode() {
        // Arrange
        OrderItemPostVm vm1 = OrderItemPostVm.builder()
                .productId(1L)
                .productName("Product")
                .quantity(5)
                .productPrice(BigDecimal.valueOf(50))
                .build();

        OrderItemPostVm vm2 = OrderItemPostVm.builder()
                .productId(1L)
                .productName("Product")
                .quantity(5)
                .productPrice(BigDecimal.valueOf(50))
                .build();

        // Assert
        assertEquals(vm1, vm2);
        assertEquals(vm1.hashCode(), vm2.hashCode());
    }

    @Test
    void testOrderItemPostVm_toString() {
        // Arrange
        OrderItemPostVm vm = OrderItemPostVm.builder()
                .productId(1L)
                .productName("Product")
                .quantity(5)
                .build();

        // Act
        String result = vm.toString();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("1"));
        assertTrue(result.contains("Product"));
        assertTrue(result.contains("5"));
    }

    @Test
    void testOrderItemPostVm_withNullValues() {
        // Act
        OrderItemPostVm vm = new OrderItemPostVm(
                null,
                null,
                0,
                null,
                null,
                null,
                null,
                null
        );

        // Assert
        assertNotNull(vm);
        assertNull(vm.productId());
        assertNull(vm.productName());
        assertEquals(0, vm.quantity());
    }
}
