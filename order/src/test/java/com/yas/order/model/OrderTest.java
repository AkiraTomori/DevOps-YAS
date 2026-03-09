package com.yas.order.model;

import com.yas.order.model.enumeration.DeliveryMethod;
import com.yas.order.model.enumeration.DeliveryStatus;
import com.yas.order.model.enumeration.OrderStatus;
import com.yas.order.model.enumeration.PaymentStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void testOrder_builder() {
        // Arrange & Act
        Order order = Order.builder()
                .id(1L)
                .email("test@example.com")
                .note("Test note")
                .tax(10.0f)
                .discount(5.0f)
                .numberItem(3)
                .couponCode("COUPON123")
                .totalPrice(BigDecimal.valueOf(100.00))
                .deliveryFee(BigDecimal.valueOf(10.00))
                .orderStatus(OrderStatus.ACCEPTED)
                .deliveryMethod(DeliveryMethod.YAS_EXPRESS)
                .build();

        // Assert
        assertNotNull(order);
        assertEquals(1L, order.getId());
        assertEquals("test@example.com", order.getEmail());
        assertEquals("Test note", order.getNote());
        assertEquals(10.0f, order.getTax());
        assertEquals(5.0f, order.getDiscount());
        assertEquals(3, order.getNumberItem());
        assertEquals("COUPON123", order.getCouponCode());
        assertEquals(BigDecimal.valueOf(100.00), order.getTotalPrice());
        assertEquals(BigDecimal.valueOf(10.00), order.getDeliveryFee());
        assertEquals(OrderStatus.ACCEPTED, order.getOrderStatus());
        assertEquals(DeliveryMethod.YAS_EXPRESS, order.getDeliveryMethod());
    }

    @Test
    void testOrder_gettersAndSetters() {
        // Arrange
        Order order = new Order();

        // Act
        order.setId(2L);
        order.setEmail("user@test.com");
        order.setNote("Note");
        order.setTax(15.0f);
        order.setDiscount(10.0f);
        order.setNumberItem(5);
        order.setCouponCode("SAVE20");
        order.setTotalPrice(BigDecimal.valueOf(200.00));
        order.setDeliveryFee(BigDecimal.valueOf(15.00));
        order.setOrderStatus(OrderStatus.PENDING);
        order.setDeliveryMethod(DeliveryMethod.YAS_EXPRESS);

        // Assert
        assertEquals(2L, order.getId());
        assertEquals("user@test.com", order.getEmail());
        assertEquals("Note", order.getNote());
        assertEquals(15.0f, order.getTax());
        assertEquals(10.0f, order.getDiscount());
        assertEquals(5, order.getNumberItem());
        assertEquals("SAVE20", order.getCouponCode());
        assertEquals(BigDecimal.valueOf(200.00), order.getTotalPrice());
        assertEquals(BigDecimal.valueOf(15.00), order.getDeliveryFee());
        assertEquals(OrderStatus.PENDING, order.getOrderStatus());
        assertEquals(DeliveryMethod.YAS_EXPRESS, order.getDeliveryMethod());
    }

    @Test
    void testOrder_noArgsConstructor() {
        // Act
        Order order = new Order();

        // Assert
        assertNotNull(order);
        assertNull(order.getId());
        assertNull(order.getEmail());
    }

    @Test
    void testOrder_allArgsConstructor() {
        // Arrange
        OrderAddress shippingAddress = new OrderAddress();
        OrderAddress billingAddress = new OrderAddress();

        // Act
        Order order = new Order(
                1L,
                "test@example.com",
                shippingAddress,
                billingAddress,
                "Note",
                10.0f,
                5.0f,
                3,
                "COUPON",
                BigDecimal.valueOf(100),
                BigDecimal.valueOf(10),
                OrderStatus.ACCEPTED,
                DeliveryMethod.YAS_EXPRESS,
                DeliveryStatus.PREPARING,
                PaymentStatus.PENDING,
                1L,
                "checkoutId",
                "rejectReason",
                "paymentMethodId",
                "progress",
                "customerId",
                "{}",
                "{}",
                BigDecimal.valueOf(5)
        );

        // Assert
        assertNotNull(order);
        assertEquals(1L, order.getId());
        assertEquals("test@example.com", order.getEmail());
    }

    @Test
    void testOrder_withAddresses() {
        // Arrange
        OrderAddress shippingAddress = OrderAddress.builder()
                .id(1L)
                .contactName("John Doe")
                .build();

        OrderAddress billingAddress = OrderAddress.builder()
                .id(2L)
                .contactName("Jane Doe")
                .build();

        // Act
        Order order = Order.builder()
                .shippingAddressId(shippingAddress)
                .billingAddressId(billingAddress)
                .build();

        // Assert
        assertNotNull(order.getShippingAddressId());
        assertNotNull(order.getBillingAddressId());
        assertEquals("John Doe", order.getShippingAddressId().getContactName());
        assertEquals("Jane Doe", order.getBillingAddressId().getContactName());
    }

    @Test
    void testOrder_setEmail() {
        // Arrange
        Order order = new Order();
        String email = "newemail@test.com";

        // Act
        order.setEmail(email);

        // Assert
        assertEquals(email, order.getEmail());
    }

    @Test
    void testOrder_setNote() {
        // Arrange
        Order order = new Order();
        String note = "Special instructions";

        // Act
        order.setNote(note);

        // Assert
        assertEquals(note, order.getNote());
    }

    @Test
    void testOrder_setTotalPrice() {
        // Arrange
        Order order = new Order();
        BigDecimal price = BigDecimal.valueOf(250.50);

        // Act
        order.setTotalPrice(price);

        // Assert
        assertEquals(price, order.getTotalPrice());
    }
}
