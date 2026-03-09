package com.yas.cart.viewmodel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CartItemGetVmTest {

    @Test
    void testCartItemGetVm_builder() {
        // Arrange & Act
        CartItemGetVm cartItemGetVm = CartItemGetVm.builder()
            .customerId("customer123")
            .productId(1L)
            .quantity(10)
            .build();

        // Assert
        assertNotNull(cartItemGetVm);
        assertEquals("customer123", cartItemGetVm.customerId());
        assertEquals(1L, cartItemGetVm.productId());
        assertEquals(10, cartItemGetVm.quantity());
    }

    @Test
    void testCartItemGetVm_constructor() {
        // Arrange & Act
        CartItemGetVm cartItemGetVm = new CartItemGetVm("customer456", 2L, 5);

        // Assert
        assertNotNull(cartItemGetVm);
        assertEquals("customer456", cartItemGetVm.customerId());
        assertEquals(2L, cartItemGetVm.productId());
        assertEquals(5, cartItemGetVm.quantity());
    }

    @Test
    void testCartItemGetVm_withNullValues() {
        // Arrange & Act
        CartItemGetVm cartItemGetVm = new CartItemGetVm(null, null, null);

        // Assert
        assertNotNull(cartItemGetVm);
        assertNull(cartItemGetVm.customerId());
        assertNull(cartItemGetVm.productId());
        assertNull(cartItemGetVm.quantity());
    }

    @Test
    void testCartItemGetVm_equals_withSameValues() {
        // Arrange
        CartItemGetVm vm1 = new CartItemGetVm("customer1", 1L, 5);
        CartItemGetVm vm2 = new CartItemGetVm("customer1", 1L, 5);

        // Act & Assert
        assertEquals(vm1, vm2);
    }

    @Test
    void testCartItemGetVm_equals_withDifferentCustomerId() {
        // Arrange
        CartItemGetVm vm1 = new CartItemGetVm("customer1", 1L, 5);
        CartItemGetVm vm2 = new CartItemGetVm("customer2", 1L, 5);

        // Act & Assert
        assertNotEquals(vm1, vm2);
    }

    @Test
    void testCartItemGetVm_equals_withDifferentProductId() {
        // Arrange
        CartItemGetVm vm1 = new CartItemGetVm("customer1", 1L, 5);
        CartItemGetVm vm2 = new CartItemGetVm("customer1", 2L, 5);

        // Act & Assert
        assertNotEquals(vm1, vm2);
    }

    @Test
    void testCartItemGetVm_equals_withDifferentQuantity() {
        // Arrange
        CartItemGetVm vm1 = new CartItemGetVm("customer1", 1L, 5);
        CartItemGetVm vm2 = new CartItemGetVm("customer1", 1L, 10);

        // Act & Assert
        assertNotEquals(vm1, vm2);
    }

    @Test
    void testCartItemGetVm_hashCode_consistency() {
        // Arrange
        CartItemGetVm vm1 = new CartItemGetVm("customer1", 1L, 5);
        CartItemGetVm vm2 = new CartItemGetVm("customer1", 1L, 5);

        // Act & Assert
        assertEquals(vm1.hashCode(), vm2.hashCode());
    }

    @Test
    void testCartItemGetVm_hashCode_withDifferentValues() {
        // Arrange
        CartItemGetVm vm1 = new CartItemGetVm("customer1", 1L, 5);
        CartItemGetVm vm2 = new CartItemGetVm("customer2", 2L, 10);

        // Act & Assert
        assertNotEquals(vm1.hashCode(), vm2.hashCode());
    }

    @Test
    void testCartItemGetVm_toString_containsFields() {
        // Arrange
        CartItemGetVm cartItemGetVm = new CartItemGetVm("customer123", 1L, 5);

        // Act
        String toString = cartItemGetVm.toString();

        // Assert
        assertNotNull(toString);
        assertTrue(toString.contains("customer123"));
        assertTrue(toString.contains("1"));
        assertTrue(toString.contains("5"));
    }

    @Test
    void testCartItemGetVm_builder_withPartialData() {
        // Arrange & Act
        CartItemGetVm cartItemGetVm = CartItemGetVm.builder()
            .customerId("customer789")
            .build();

        // Assert
        assertNotNull(cartItemGetVm);
        assertEquals("customer789", cartItemGetVm.customerId());
        assertNull(cartItemGetVm.productId());
        assertNull(cartItemGetVm.quantity());
    }

    @Test
    void testCartItemGetVm_builder_withAllFields() {
        // Arrange & Act
        CartItemGetVm cartItemGetVm = CartItemGetVm.builder()
            .customerId("customerABC")
            .productId(99L)
            .quantity(100)
            .build();

        // Assert
        assertEquals("customerABC", cartItemGetVm.customerId());
        assertEquals(99L, cartItemGetVm.productId());
        assertEquals(100, cartItemGetVm.quantity());
    }
}
