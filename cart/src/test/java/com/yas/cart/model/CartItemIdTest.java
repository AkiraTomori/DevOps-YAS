package com.yas.cart.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CartItemIdTest {

    @Test
    void testCartItemId_noArgsConstructor() {
        // Act
        CartItemId cartItemId = new CartItemId();

        // Assert
        assertNotNull(cartItemId);
        assertNull(cartItemId.getCustomerId());
        assertNull(cartItemId.getProductId());
    }

    @Test
    void testCartItemId_allArgsConstructor() {
        // Arrange
        String customerId = "customer123";
        Long productId = 1L;

        // Act
        CartItemId cartItemId = new CartItemId(customerId, productId);

        // Assert
        assertNotNull(cartItemId);
        assertEquals(customerId, cartItemId.getCustomerId());
        assertEquals(productId, cartItemId.getProductId());
    }

    @Test
    void testCartItemId_gettersAndSetters() {
        // Arrange
        CartItemId cartItemId = new CartItemId();
        String customerId = "customer456";
        Long productId = 2L;

        // Act
        cartItemId.setCustomerId(customerId);
        cartItemId.setProductId(productId);

        // Assert
        assertEquals(customerId, cartItemId.getCustomerId());
        assertEquals(productId, cartItemId.getProductId());
    }

    @Test
    void testCartItemId_equals_withSameValues() {
        // Arrange
        String customerId = "customer789";
        Long productId = 3L;
        CartItemId cartItemId1 = new CartItemId(customerId, productId);
        CartItemId cartItemId2 = new CartItemId(customerId, productId);

        // Act & Assert
        assertEquals(cartItemId1, cartItemId2);
    }

    @Test
    void testCartItemId_equals_withDifferentCustomerId() {
        // Arrange
        CartItemId cartItemId1 = new CartItemId("customer1", 1L);
        CartItemId cartItemId2 = new CartItemId("customer2", 1L);

        // Act & Assert
        assertNotEquals(cartItemId1, cartItemId2);
    }

    @Test
    void testCartItemId_equals_withDifferentProductId() {
        // Arrange
        CartItemId cartItemId1 = new CartItemId("customer1", 1L);
        CartItemId cartItemId2 = new CartItemId("customer1", 2L);

        // Act & Assert
        assertNotEquals(cartItemId1, cartItemId2);
    }

    @Test
    void testCartItemId_equals_withSameObject() {
        // Arrange
        CartItemId cartItemId = new CartItemId("customer1", 1L);

        // Act & Assert
        assertEquals(cartItemId, cartItemId);
    }

    @Test
    void testCartItemId_equals_withNull() {
        // Arrange
        CartItemId cartItemId = new CartItemId("customer1", 1L);

        // Act & Assert
        assertNotEquals(null, cartItemId);
    }

    @Test
    void testCartItemId_equals_withDifferentClass() {
        // Arrange
        CartItemId cartItemId = new CartItemId("customer1", 1L);
        String notACartItemId = "notACartItemId";

        // Act & Assert
        assertNotEquals(cartItemId, notACartItemId);
    }

    @Test
    void testCartItemId_hashCode_withSameValues() {
        // Arrange
        String customerId = "customer123";
        Long productId = 1L;
        CartItemId cartItemId1 = new CartItemId(customerId, productId);
        CartItemId cartItemId2 = new CartItemId(customerId, productId);

        // Act & Assert
        assertEquals(cartItemId1.hashCode(), cartItemId2.hashCode());
    }

    @Test
    void testCartItemId_hashCode_withDifferentValues() {
        // Arrange
        CartItemId cartItemId1 = new CartItemId("customer1", 1L);
        CartItemId cartItemId2 = new CartItemId("customer2", 2L);

        // Act & Assert
        assertNotEquals(cartItemId1.hashCode(), cartItemId2.hashCode());
    }

    @Test
    void testCartItemId_equals_withNullFields() {
        // Arrange
        CartItemId cartItemId1 = new CartItemId(null, null);
        CartItemId cartItemId2 = new CartItemId(null, null);

        // Act & Assert
        assertEquals(cartItemId1, cartItemId2);
        assertEquals(cartItemId1.hashCode(), cartItemId2.hashCode());
    }

    @Test
    void testCartItemId_equals_withPartialNullFields() {
        // Arrange
        CartItemId cartItemId1 = new CartItemId("customer1", null);
        CartItemId cartItemId2 = new CartItemId("customer1", null);
        CartItemId cartItemId3 = new CartItemId(null, 1L);
        CartItemId cartItemId4 = new CartItemId(null, 1L);

        // Act & Assert
        assertEquals(cartItemId1, cartItemId2);
        assertEquals(cartItemId3, cartItemId4);
        assertNotEquals(cartItemId1, cartItemId3);
    }
}
