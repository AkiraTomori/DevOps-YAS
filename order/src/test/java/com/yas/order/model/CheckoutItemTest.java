package com.yas.order.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CheckoutItemTest {

    @Test
    void testCheckoutItem_builder() {
        // Arrange & Act
        CheckoutItem item = CheckoutItem.builder()
                .id(1L)
                .productId(100L)
                .productName("Test Product")
                .quantity(5)
                .productPrice(BigDecimal.valueOf(50.00))
                .taxAmount(BigDecimal.valueOf(2.50))
                .build();

        // Assert
        assertNotNull(item);
        assertEquals(1L, item.getId());
        assertEquals(100L, item.getProductId());
        assertEquals("Test Product", item.getProductName());
        assertEquals(5, item.getQuantity());
        assertEquals(BigDecimal.valueOf(50.00), item.getProductPrice());
        assertEquals(BigDecimal.valueOf(2.50), item.getTaxAmount());
    }

    @Test
    void testCheckoutItem_gettersAndSetters() {
        // Arrange
        CheckoutItem item = new CheckoutItem();

        // Act
        item.setId(2L);
        item.setProductId(200L);
        item.setProductName("Another Product");
        item.setQuantity(10);
        item.setProductPrice(BigDecimal.valueOf(75.00));
        item.setTaxAmount(BigDecimal.valueOf(3.75));

        // Assert
        assertEquals(2L, item.getId());
        assertEquals(200L, item.getProductId());
        assertEquals("Another Product", item.getProductName());
        assertEquals(10, item.getQuantity());
        assertEquals(BigDecimal.valueOf(75.00), item.getProductPrice());
        assertEquals(BigDecimal.valueOf(3.75), item.getTaxAmount());
    }

    @Test
    void testCheckoutItem_noArgsConstructor() {
        // Act
        CheckoutItem item = new CheckoutItem();

        // Assert
        assertNotNull(item);
        assertNull(item.getId());
        assertNull(item.getProductId());
        assertNull(item.getProductName());
    }

    @Test
    void testCheckoutItem_toBuilder() {
        // Arrange
        CheckoutItem original = CheckoutItem.builder()
                .id(1L)
                .productId(100L)
                .productName("Original")
                .quantity(5)
                .build();

        // Act
        CheckoutItem modified = original.toBuilder()
                .productName("Modified")
                .quantity(10)
                .build();

        // Assert
        assertEquals("Original", original.getProductName());
        assertEquals(5, original.getQuantity());
        assertEquals("Modified", modified.getProductName());
        assertEquals(10, modified.getQuantity());
        assertEquals(1L, modified.getId());
        assertEquals(100L, modified.getProductId());
    }

    @Test
    void testCheckoutItem_setProductId() {
        // Arrange
        CheckoutItem item = new CheckoutItem();
        Long productId = 999L;

        // Act
        item.setProductId(productId);

        // Assert
        assertEquals(productId, item.getProductId());
    }

    @Test
    void testCheckoutItem_setQuantity() {
        // Arrange
        CheckoutItem item = new CheckoutItem();
        int quantity = 15;

        // Act
        item.setQuantity(quantity);

        // Assert
        assertEquals(quantity, item.getQuantity());
    }

    @Test
    void testCheckoutItem_setProductPrice() {
        // Arrange
        CheckoutItem item = new CheckoutItem();
        BigDecimal price = BigDecimal.valueOf(99.99);

        // Act
        item.setProductPrice(price);

        // Assert
        assertEquals(price, item.getProductPrice());
    }

    @Test
    void testCheckoutItem_setTaxAmount() {
        // Arrange
        CheckoutItem item = new CheckoutItem();
        BigDecimal tax = BigDecimal.valueOf(5.00);

        // Act
        item.setTaxAmount(tax);

        // Assert
        assertEquals(tax, item.getTaxAmount());
    }

    @Test
    void testCheckoutItem_allArgsConstructor() {
        // Arrange
        Checkout checkout = new Checkout();

        // Act
        CheckoutItem item = new CheckoutItem(
                1L,
                100L,
                "Product",
                "Description",
                5,
                BigDecimal.valueOf(50),
                BigDecimal.valueOf(2.5),
                BigDecimal.valueOf(10),
                BigDecimal.valueOf(1),
                BigDecimal.valueOf(5),
                checkout
        );

        // Assert
        assertNotNull(item);
        assertEquals(1L, item.getId());
        assertEquals(100L, item.getProductId());
        assertEquals("Product", item.getProductName());
        assertEquals(5, item.getQuantity());
    }
}
