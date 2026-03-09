package com.yas.order.viewmodel.checkout;

import com.yas.order.model.enumeration.CheckoutState;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CheckoutVmTest {

    @Test
    void testCheckoutVm_builder() {
        // Arrange
        CheckoutItemVm item = CheckoutItemVm.builder()
                .id(1L)
                .productId(1L)
                .productName("Product")
                .quantity(5)
                .build();

        // Act
        CheckoutVm vm = CheckoutVm.builder()
                .id("checkout-123")
                .email("test@example.com")
                .note("Test note")
                .promotionCode("PROMO")
                .checkoutState(CheckoutState.PENDING)
                .progress("50%")
                .totalAmount(BigDecimal.valueOf(100))
                .totalShipmentFee(BigDecimal.valueOf(10))
                .totalShipmentTax(BigDecimal.valueOf(2))
                .totalTax(BigDecimal.valueOf(5))
                .totalDiscountAmount(BigDecimal.valueOf(5))
                .shipmentMethodId("shipment-1")
                .paymentMethodId("payment-1")
                .shippingAddressId(1L)
                .checkoutItemVms(Set.of(item))
                .build();

        // Assert
        assertNotNull(vm);
        assertEquals("checkout-123", vm.id());
        assertEquals("test@example.com", vm.email());
        assertEquals("Test note", vm.note());
        assertEquals("PROMO", vm.promotionCode());
        assertEquals(CheckoutState.PENDING, vm.checkoutState());
        assertEquals("50%", vm.progress());
        assertEquals(BigDecimal.valueOf(100), vm.totalAmount());
        assertEquals(BigDecimal.valueOf(10), vm.totalShipmentFee());
        assertEquals(BigDecimal.valueOf(2), vm.totalShipmentTax());
        assertEquals(BigDecimal.valueOf(5), vm.totalTax());
        assertEquals(BigDecimal.valueOf(5), vm.totalDiscountAmount());
        assertEquals("shipment-1", vm.shipmentMethodId());
        assertEquals("payment-1", vm.paymentMethodId());
        assertEquals(1L, vm.shippingAddressId());
        assertEquals(1, vm.checkoutItemVms().size());
    }

    @Test
    void testCheckoutVm_toBuilder() {
        // Arrange
        CheckoutVm original = CheckoutVm.builder()
                .id("checkout-123")
                .email("test@example.com")
                .totalAmount(BigDecimal.valueOf(100))
                .build();

        // Act
        CheckoutVm modified = original.toBuilder()
                .totalAmount(BigDecimal.valueOf(150))
                .build();

        // Assert
        assertEquals("checkout-123", modified.id());
        assertEquals("test@example.com", modified.email());
        assertEquals(BigDecimal.valueOf(150), modified.totalAmount());
    }

    @Test
    void testCheckoutVm_getters() {
        // Arrange
        CheckoutItemVm item1 = CheckoutItemVm.builder().id(1L).build();
        CheckoutItemVm item2 = CheckoutItemVm.builder().id(2L).build();

        CheckoutVm vm = CheckoutVm.builder()
                .id("checkout-456")
                .email("user@test.com")
                .note("Checkout note")
                .promotionCode("SAVE10")
                .checkoutState(CheckoutState.COMPLETED)
                .progress("100%")
                .totalAmount(BigDecimal.valueOf(200))
                .totalShipmentFee(BigDecimal.valueOf(20))
                .totalShipmentTax(BigDecimal.valueOf(3))
                .totalTax(BigDecimal.valueOf(10))
                .totalDiscountAmount(BigDecimal.valueOf(10))
                .shipmentMethodId("shipment-2")
                .paymentMethodId("payment-2")
                .shippingAddressId(2L)
                .checkoutItemVms(Set.of(item1, item2))
                .build();

        // Assert
        assertEquals("checkout-456", vm.id());
        assertEquals("user@test.com", vm.email());
        assertEquals("Checkout note", vm.note());
        assertEquals("SAVE10", vm.promotionCode());
        assertEquals(CheckoutState.COMPLETED, vm.checkoutState());
        assertEquals("100%", vm.progress());
        assertEquals(BigDecimal.valueOf(200), vm.totalAmount());
        assertEquals(BigDecimal.valueOf(20), vm.totalShipmentFee());
        assertEquals(BigDecimal.valueOf(3), vm.totalShipmentTax());
        assertEquals(BigDecimal.valueOf(10), vm.totalTax());
        assertEquals(BigDecimal.valueOf(10), vm.totalDiscountAmount());
        assertEquals("shipment-2", vm.shipmentMethodId());
        assertEquals("payment-2", vm.paymentMethodId());
        assertEquals(2L, vm.shippingAddressId());
        assertEquals(2, vm.checkoutItemVms().size());
    }

    @Test
    void testCheckoutVm_equalsAndHashCode() {
        // Arrange
        CheckoutVm vm1 = CheckoutVm.builder()
                .id("checkout-123")
                .email("test@example.com")
                .totalAmount(BigDecimal.valueOf(100))
                .build();

        CheckoutVm vm2 = CheckoutVm.builder()
                .id("checkout-123")
                .email("test@example.com")
                .totalAmount(BigDecimal.valueOf(100))
                .build();

        // Assert
        assertEquals(vm1, vm2);
        assertEquals(vm1.hashCode(), vm2.hashCode());
    }

    @Test
    void testCheckoutVm_toString() {
        // Arrange
        CheckoutVm vm = CheckoutVm.builder()
                .id("checkout-123")
                .email("test@example.com")
                .checkoutState(CheckoutState.PENDING)
                .build();

        // Act
        String result = vm.toString();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("checkout-123"));
        assertTrue(result.contains("test@example.com"));
    }

    @Test
    void testCheckoutVm_withNullOptionalFields() {
        // Act
        CheckoutVm vm = CheckoutVm.builder()
                .id("checkout-789")
                .build();

        // Assert
        assertNotNull(vm);
        assertEquals("checkout-789", vm.id());
        assertNull(vm.email());
        assertNull(vm.checkoutState());
    }
}
