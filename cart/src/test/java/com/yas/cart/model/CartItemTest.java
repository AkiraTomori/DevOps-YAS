package com.yas.cart.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CartItemTest {

    @Test
    void testCartItem_builder() {
        // Arrange
        String customerId = "customer123";
        Long productId = 1L;
        int quantity = 5;

        // Act
        CartItem cartItem = CartItem.builder()
            .customerId(customerId)
            .productId(productId)
            .quantity(quantity)
            .build();

        // Assert
        assertNotNull(cartItem);
        assertEquals(customerId, cartItem.getCustomerId());
        assertEquals(productId, cartItem.getProductId());
        assertEquals(quantity, cartItem.getQuantity());
    }

    @Test
    void testCartItem_noArgsConstructor() {
        // Act
        CartItem cartItem = new CartItem();

        // Assert
        assertNotNull(cartItem);
        assertNull(cartItem.getCustomerId());
        assertNull(cartItem.getProductId());
        assertEquals(0, cartItem.getQuantity());
    }

    @Test
    void testCartItem_allArgsConstructor() {
        // Arrange
        String customerId = "customer456";
        Long productId = 2L;
        int quantity = 10;

        // Act
        CartItem cartItem = new CartItem(customerId, productId, quantity);

        // Assert
        assertNotNull(cartItem);
        assertEquals(customerId, cartItem.getCustomerId());
        assertEquals(productId, cartItem.getProductId());
        assertEquals(quantity, cartItem.getQuantity());
    }

    @Test
    void testCartItem_gettersAndSetters() {
        // Arrange
        CartItem cartItem = new CartItem();
        String customerId = "customer789";
        Long productId = 3L;
        int quantity = 15;

        // Act
        cartItem.setCustomerId(customerId);
        cartItem.setProductId(productId);
        cartItem.setQuantity(quantity);

        // Assert
        assertEquals(customerId, cartItem.getCustomerId());
        assertEquals(productId, cartItem.getProductId());
        assertEquals(quantity, cartItem.getQuantity());
    }

    @Test
    void testCartItem_setQuantity_withZero() {
        // Arrange
        CartItem cartItem = CartItem.builder()
            .customerId("customer1")
            .productId(1L)
            .quantity(5)
            .build();

        // Act
        cartItem.setQuantity(0);

        // Assert
        assertEquals(0, cartItem.getQuantity());
    }

    @Test
    void testCartItem_setQuantity_withNegative() {
        // Arrange
        CartItem cartItem = CartItem.builder()
            .customerId("customer1")
            .productId(1L)
            .quantity(5)
            .build();

        // Act
        cartItem.setQuantity(-1);

        // Assert
        assertEquals(-1, cartItem.getQuantity());
    }

    @Test
    void testCartItem_builderWithPartialData() {
        // Arrange & Act
        CartItem cartItem = CartItem.builder()
            .customerId("customer1")
            .productId(1L)
            .build();

        // Assert
        assertNotNull(cartItem);
        assertEquals("customer1", cartItem.getCustomerId());
        assertEquals(1L, cartItem.getProductId());
        assertEquals(0, cartItem.getQuantity()); // default int value
    }
}
