package com.yas.order.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class OrderItemTest {

    @Test
    void testOrderItem_builder() {
        // Arrange & Act
        OrderItem orderItem = OrderItem.builder()
                .id(1L)
                .productId(100L)
                .orderId(200L)
                .productName("Test Product")
                .quantity(5)
                .productPrice(BigDecimal.valueOf(50.00))
                .note("Product note")
                .discountAmount(BigDecimal.valueOf(5.00))
                .taxAmount(BigDecimal.valueOf(2.50))
                .taxPercent(BigDecimal.valueOf(5.00))
                .shipmentFee(BigDecimal.valueOf(3.00))
                .build();

        // Assert
        assertNotNull(orderItem);
        assertEquals(1L, orderItem.getId());
        assertEquals(100L, orderItem.getProductId());
        assertEquals(200L, orderItem.getOrderId());
        assertEquals("Test Product", orderItem.getProductName());
        assertEquals(5, orderItem.getQuantity());
        assertEquals(BigDecimal.valueOf(50.00), orderItem.getProductPrice());
        assertEquals("Product note", orderItem.getNote());
        assertEquals(BigDecimal.valueOf(5.00), orderItem.getDiscountAmount());
        assertEquals(BigDecimal.valueOf(2.50), orderItem.getTaxAmount());
        assertEquals(BigDecimal.valueOf(5.00), orderItem.getTaxPercent());
    }

    @Test
    void testOrderItem_gettersAndSetters() {
        // Arrange
        OrderItem orderItem = new OrderItem();

        // Act
        orderItem.setId(2L);
        orderItem.setProductId(101L);
        orderItem.setOrderId(201L);
        orderItem.setProductName("Another Product");
        orderItem.setQuantity(10);
        orderItem.setProductPrice(BigDecimal.valueOf(75.00));
        orderItem.setNote("Important note");
        orderItem.setDiscountAmount(BigDecimal.valueOf(7.50));
        orderItem.setTaxAmount(BigDecimal.valueOf(3.75));
        orderItem.setTaxPercent(BigDecimal.valueOf(5.00));

        // Assert
        assertEquals(2L, orderItem.getId());
        assertEquals(101L, orderItem.getProductId());
        assertEquals(201L, orderItem.getOrderId());
        assertEquals("Another Product", orderItem.getProductName());
        assertEquals(10, orderItem.getQuantity());
        assertEquals(BigDecimal.valueOf(75.00), orderItem.getProductPrice());
        assertEquals("Important note", orderItem.getNote());
        assertEquals(BigDecimal.valueOf(7.50), orderItem.getDiscountAmount());
        assertEquals(BigDecimal.valueOf(3.75), orderItem.getTaxAmount());
        assertEquals(BigDecimal.valueOf(5.00), orderItem.getTaxPercent());
    }

    @Test
    void testOrderItem_noArgsConstructor() {
        // Act
        OrderItem orderItem = new OrderItem();

        // Assert
        assertNotNull(orderItem);
        assertNull(orderItem.getId());
        assertNull(orderItem.getProductId());
        assertNull(orderItem.getProductName());
    }

    @Test
    void testOrderItem_allArgsConstructor() {
        // Act
        OrderItem orderItem = new OrderItem(
                1L,
                100L,
                200L,
                "Product",
                5,
                BigDecimal.valueOf(50),
                "Note",
                BigDecimal.valueOf(5),
                BigDecimal.valueOf(2.5),
                BigDecimal.valueOf(5),
                BigDecimal.valueOf(3),
                "ACTIVE",
                BigDecimal.valueOf(2),
                "{\"state\":\"new\"}",
                null
        );

        // Assert
        assertNotNull(orderItem);
        assertEquals(1L, orderItem.getId());
        assertEquals(100L, orderItem.getProductId());
        assertEquals("Product", orderItem.getProductName());
    }

    @Test
    void testOrderItem_setProductId() {
        // Arrange
        OrderItem orderItem = new OrderItem();
        Long productId = 999L;

        // Act
        orderItem.setProductId(productId);

        // Assert
        assertEquals(productId, orderItem.getProductId());
    }

    @Test
    void testOrderItem_setQuantity() {
        // Arrange
        OrderItem orderItem = new OrderItem();
        int quantity = 25;

        // Act
        orderItem.setQuantity(quantity);

        // Assert
        assertEquals(quantity, orderItem.getQuantity());
    }

    @Test
    void testOrderItem_setProductPrice() {
        // Arrange
        OrderItem orderItem = new OrderItem();
        BigDecimal price = BigDecimal.valueOf(123.45);

        // Act
        orderItem.setProductPrice(price);

        // Assert
        assertEquals(price, orderItem.getProductPrice());
    }

    @Test
    void testOrderItem_setDiscountAmount() {
        // Arrange
        OrderItem orderItem = new OrderItem();
        BigDecimal discount = BigDecimal.valueOf(10.00);

        // Act
        orderItem.setDiscountAmount(discount);

        // Assert
        assertEquals(discount, orderItem.getDiscountAmount());
    }

    @Test
    void testOrderItem_setTaxAmount() {
        // Arrange
        OrderItem orderItem = new OrderItem();
        BigDecimal tax = BigDecimal.valueOf(5.00);

        // Act
        orderItem.setTaxAmount(tax);

        // Assert
        assertEquals(tax, orderItem.getTaxAmount());
    }
}
