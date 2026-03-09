package com.yas.order.viewmodel.checkout;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CheckoutItemVmTest {

    @Test
    void testCheckoutItemVm_builder() {
        // Act
        CheckoutItemVm vm = CheckoutItemVm.builder()
                .id(1L)
                .productId(100L)
                .productName("Test Product")
                .description("Product description")
                .quantity(5)
                .productPrice(BigDecimal.valueOf(50.00))
                .taxAmount(BigDecimal.valueOf(2.50))
                .discountAmount(BigDecimal.valueOf(5.00))
                .shipmentFee(BigDecimal.valueOf(10.00))
                .shipmentTax(BigDecimal.valueOf(1.00))
                .checkoutId("checkout-123")
                .build();

        // Assert
        assertNotNull(vm);
        assertEquals(1L, vm.id());
        assertEquals(100L, vm.productId());
        assertEquals("Test Product", vm.productName());
        assertEquals("Product description", vm.description());
        assertEquals(5, vm.quantity());
        assertEquals(BigDecimal.valueOf(50.00), vm.productPrice());
        assertEquals(BigDecimal.valueOf(2.50), vm.taxAmount());
        assertEquals(BigDecimal.valueOf(5.00), vm.discountAmount());
        assertEquals(BigDecimal.valueOf(10.00), vm.shipmentFee());
        assertEquals(BigDecimal.valueOf(1.00), vm.shipmentTax());
        assertEquals("checkout-123", vm.checkoutId());
    }

    @Test
    void testCheckoutItemVm_getters() {
        // Arrange
        CheckoutItemVm vm = CheckoutItemVm.builder()
                .id(5L)
                .productId(200L)
                .productName("Product Name")
                .description("Description")
                .quantity(10)
                .productPrice(BigDecimal.valueOf(99.99))
                .taxAmount(BigDecimal.valueOf(5.00))
                .discountAmount(BigDecimal.valueOf(10.00))
                .shipmentFee(BigDecimal.valueOf(15.00))
                .shipmentTax(BigDecimal.valueOf(2.00))
                .checkoutId("checkout-456")
                .build();

        // Assert
        assertEquals(5L, vm.id());
        assertEquals(200L, vm.productId());
        assertEquals("Product Name", vm.productName());
        assertEquals("Description", vm.description());
        assertEquals(10, vm.quantity());
        assertEquals(BigDecimal.valueOf(99.99), vm.productPrice());
        assertEquals(BigDecimal.valueOf(5.00), vm.taxAmount());
        assertEquals(BigDecimal.valueOf(10.00), vm.discountAmount());
        assertEquals(BigDecimal.valueOf(15.00), vm.shipmentFee());
        assertEquals(BigDecimal.valueOf(2.00), vm.shipmentTax());
        assertEquals("checkout-456", vm.checkoutId());
    }

    @Test
    void testCheckoutItemVm_equalsAndHashCode() {
        // Arrange
        CheckoutItemVm vm1 = CheckoutItemVm.builder()
                .id(1L)
                .productId(100L)
                .productName("Product")
                .quantity(5)
                .checkoutId("checkout-123")
                .build();

        CheckoutItemVm vm2 = CheckoutItemVm.builder()
                .id(1L)
                .productId(100L)
                .productName("Product")
                .quantity(5)
                .checkoutId("checkout-123")
                .build();

        // Assert
        assertEquals(vm1, vm2);
        assertEquals(vm1.hashCode(), vm2.hashCode());
    }

    @Test
    void testCheckoutItemVm_toString() {
        // Arrange
        CheckoutItemVm vm = CheckoutItemVm.builder()
                .id(1L)
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
    void testCheckoutItemVm_withNullValues() {
        // Act
        CheckoutItemVm vm = CheckoutItemVm.builder()
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
