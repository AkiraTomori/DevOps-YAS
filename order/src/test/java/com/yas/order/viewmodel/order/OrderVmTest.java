package com.yas.order.viewmodel.order;

import com.yas.order.model.enumeration.DeliveryMethod;
import com.yas.order.model.enumeration.DeliveryStatus;
import com.yas.order.model.enumeration.OrderStatus;
import com.yas.order.model.enumeration.PaymentStatus;
import com.yas.order.viewmodel.orderaddress.OrderAddressVm;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OrderVmTest {

    @Test
    void testOrderVm_builder() {
        // Arrange & Act
        OrderVm vm = OrderVm.builder()
                .id(1L)
                .email("test@example.com")
                .note("Test note")
                .tax(10.0f)
                .discount(5.0f)
                .numberItem(3)
                .totalPrice(BigDecimal.valueOf(100))
                .deliveryFee(BigDecimal.valueOf(10))
                .couponCode("COUPON")
                .orderStatus(OrderStatus.ACCEPTED)
                .deliveryMethod(DeliveryMethod.YAS_EXPRESS)
                .deliveryStatus(DeliveryStatus.PREPARING)
                .paymentStatus(PaymentStatus.COMPLETED)
                .checkoutId("checkout-123")
                .build();

        // Assert
        assertNotNull(vm);
        assertEquals(1L, vm.id());
        assertEquals("test@example.com", vm.email());
        assertEquals("Test note", vm.note());
        assertEquals(10.0f, vm.tax());
        assertEquals(5.0f, vm.discount());
        assertEquals(3, vm.numberItem());
        assertEquals(BigDecimal.valueOf(100), vm.totalPrice());
        assertEquals(BigDecimal.valueOf(10), vm.deliveryFee());
        assertEquals("COUPON", vm.couponCode());
        assertEquals(OrderStatus.ACCEPTED, vm.orderStatus());
        assertEquals(DeliveryMethod.YAS_EXPRESS, vm.deliveryMethod());
        assertEquals(DeliveryStatus.PREPARING, vm.deliveryStatus());
        assertEquals(PaymentStatus.COMPLETED, vm.paymentStatus());
        assertEquals("checkout-123", vm.checkoutId());
    }

    @Test
    void testOrderVm_getters() {
        // Arrange
        OrderAddressVm shippingAddress = createOrderAddressVm();
        OrderAddressVm billingAddress = createOrderAddressVm();
        OrderItemVm orderItem = createOrderItemVm();
        ZonedDateTime now = ZonedDateTime.now();

        OrderVm vm = OrderVm.builder()
                .id(100L)
                .email("user@test.com")
                .shippingAddressVm(shippingAddress)
                .billingAddressVm(billingAddress)
                .note("Order note")
                .tax(15.0f)
                .discount(10.0f)
                .numberItem(5)
                .totalPrice(BigDecimal.valueOf(500))
                .deliveryFee(BigDecimal.valueOf(20))
                .couponCode("SAVE20")
                .orderStatus(OrderStatus.PENDING)
                .deliveryMethod(DeliveryMethod.VIETTEL_POST)
                .deliveryStatus(DeliveryStatus.PREPARING)
                .paymentStatus(PaymentStatus.PENDING)
                .checkoutId("checkout-456")
                .orderItemVms(Set.of(orderItem))
                .build();

        // Assert
        assertEquals(100L, vm.id());
        assertEquals("user@test.com", vm.email());
        assertEquals(shippingAddress, vm.shippingAddressVm());
        assertEquals(billingAddress, vm.billingAddressVm());
        assertEquals("Order note", vm.note());
        assertEquals(15.0f, vm.tax());
        assertEquals(10.0f, vm.discount());
        assertEquals(5, vm.numberItem());
        assertEquals(BigDecimal.valueOf(500), vm.totalPrice());
        assertEquals(BigDecimal.valueOf(20), vm.deliveryFee());
        assertEquals("SAVE20", vm.couponCode());
        assertEquals(OrderStatus.PENDING, vm.orderStatus());
        assertEquals(DeliveryMethod.VIETTEL_POST, vm.deliveryMethod());
        assertEquals(DeliveryStatus.PREPARING, vm.deliveryStatus());
        assertEquals(PaymentStatus.PENDING, vm.paymentStatus());
        assertEquals("checkout-456", vm.checkoutId());
        assertEquals(1, vm.orderItemVms().size());
    }

    @Test
    void testOrderVm_equalsAndHashCode() {
        // Arrange
        OrderVm vm1 = OrderVm.builder()
                .id(1L)
                .email("test@example.com")
                .totalPrice(BigDecimal.valueOf(100))
                .build();

        OrderVm vm2 = OrderVm.builder()
                .id(1L)
                .email("test@example.com")
                .totalPrice(BigDecimal.valueOf(100))
                .build();

        // Assert
        assertEquals(vm1, vm2);
        assertEquals(vm1.hashCode(), vm2.hashCode());
    }

    @Test
    void testOrderVm_toString() {
        // Arrange
        OrderVm vm = OrderVm.builder()
                .id(1L)
                .email("test@example.com")
                .orderStatus(OrderStatus.ACCEPTED)
                .build();

        // Act
        String result = vm.toString();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("1"));
        assertTrue(result.contains("test@example.com"));
    }

    @Test
    void testOrderVm_withMinimalData() {
        // Act
        OrderVm vm = OrderVm.builder()
                .id(1L)
                .build();

        // Assert
        assertNotNull(vm);
        assertEquals(1L, vm.id());
        assertNull(vm.email());
    }

    private OrderAddressVm createOrderAddressVm() {
        return new OrderAddressVm(
                1L,
                "John Doe",
                "123-456-7890",
                "123 Main St",
                "Apt 4B",
                "New York",
                "10001",
                1L,
                "Manhattan",
                1L,
                "NY",
                1L,
                "USA"
        );
    }

    private OrderItemVm createOrderItemVm() {
        return OrderItemVm.builder()
                .id(1L)
                .productId(1L)
                .productName("Product")
                .quantity(2)
                .build();
    }
}

