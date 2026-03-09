package com.yas.order.model;

import com.yas.order.model.enumeration.CheckoutState;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CheckoutTest {

    @Test
    void testCheckout_builder() {
        // Arrange & Act
        Checkout checkout = Checkout.builder()
                .id("checkout-123")
                .email("test@example.com")
                .note("Test note")
                .promotionCode("PROMO10")
                .checkoutState(CheckoutState.COMPLETED)
                .build();

        // Assert
        assertNotNull(checkout);
        assertEquals("checkout-123", checkout.getId());
        assertEquals("test@example.com", checkout.getEmail());
        assertEquals("Test note", checkout.getNote());
        assertEquals("PROMO10", checkout.getPromotionCode());
        assertEquals(CheckoutState.COMPLETED, checkout.getCheckoutState());
    }

    @Test
    void testCheckout_gettersAndSetters() {
        // Arrange
        Checkout checkout = new Checkout();

        // Act
        checkout.setId("checkout-456");
        checkout.setEmail("user@test.com");
        checkout.setNote("Important notes");
        checkout.setPromotionCode("SAVE20");
        checkout.setCheckoutState(CheckoutState.PENDING);

        // Assert
        assertEquals("checkout-456", checkout.getId());
        assertEquals("user@test.com", checkout.getEmail());
        assertEquals("Important notes", checkout.getNote());
        assertEquals("SAVE20", checkout.getPromotionCode());
        assertEquals(CheckoutState.PENDING, checkout.getCheckoutState());
    }

    @Test
    void testCheckout_noArgsConstructor() {
        // Act
        Checkout checkout = new Checkout();

        // Assert
        assertNotNull(checkout);
        assertNull(checkout.getId());
        assertNull(checkout.getEmail());
    }

    @Test
    void testCheckout_setEmail() {
        // Arrange
        Checkout checkout = new Checkout();
        String email = "newemail@example.com";

        // Act
        checkout.setEmail(email);

        // Assert
        assertEquals(email, checkout.getEmail());
    }

    @Test
    void testCheckout_setNote() {
        // Arrange
        Checkout checkout = new Checkout();
        String note = "Delivery instructions";

        // Act
        checkout.setNote(note);

        // Assert
        assertEquals(note, checkout.getNote());
    }

    @Test
    void testCheckout_setPromotionCode() {
        // Arrange
        Checkout checkout = new Checkout();
        String code = "DISCOUNT50";

        // Act
        checkout.setPromotionCode(code);

        // Assert
        assertEquals(code, checkout.getPromotionCode());
    }

    @Test
    void testCheckout_setCheckoutState() {
        // Arrange
        Checkout checkout = new Checkout();

        // Act
        checkout.setCheckoutState(CheckoutState.COMPLETED);

        // Assert
        assertEquals(CheckoutState.COMPLETED, checkout.getCheckoutState());
    }

    @Test
    void testCheckout_withDifferentStates() {
        // Arrange
        Checkout checkout1 = Checkout.builder().checkoutState(CheckoutState.PENDING).build();
        Checkout checkout2 = Checkout.builder().checkoutState(CheckoutState.COMPLETED).build();

        // Assert
        assertEquals(CheckoutState.PENDING, checkout1.getCheckoutState());
        assertEquals(CheckoutState.COMPLETED, checkout2.getCheckoutState());
    }
}
