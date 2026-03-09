package com.yas.order.viewmodel.order;

import com.yas.order.model.enumeration.DeliveryMethod;
import com.yas.order.model.enumeration.DeliveryStatus;
import com.yas.order.model.enumeration.OrderStatus;
import com.yas.order.model.enumeration.PaymentStatus;
import com.yas.order.viewmodel.orderaddress.OrderAddressVm;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class OrderBriefVmTest {

    @Test
    void testOrderBriefVm_builder() {
        // Arrange
        ZonedDateTime now = ZonedDateTime.now();
        OrderAddressVm billingAddress = createOrderAddressVm();

        // Act
        OrderBriefVm vm = OrderBriefVm.builder()
                .id(1L)
                .email("test@example.com")
                .billingAddressVm(billingAddress)
                .totalPrice(BigDecimal.valueOf(100))
                .orderStatus(OrderStatus.ACCEPTED)
                .deliveryMethod(DeliveryMethod.YAS_EXPRESS)
                .deliveryStatus(DeliveryStatus.PREPARING)
                .paymentStatus(PaymentStatus.COMPLETED)
                .createdOn(now)
                .build();

        // Assert
        assertNotNull(vm);
        assertEquals(1L, vm.id());
        assertEquals("test@example.com", vm.email());
        assertEquals(billingAddress, vm.billingAddressVm());
        assertEquals(BigDecimal.valueOf(100), vm.totalPrice());
        assertEquals(OrderStatus.ACCEPTED, vm.orderStatus());
        assertEquals(DeliveryMethod.YAS_EXPRESS, vm.deliveryMethod());
        assertEquals(DeliveryStatus.PREPARING, vm.deliveryStatus());
        assertEquals(PaymentStatus.COMPLETED, vm.paymentStatus());
        assertEquals(now, vm.createdOn());
    }

    @Test
    void testOrderBriefVm_getters() {
        // Arrange
        ZonedDateTime createdTime = ZonedDateTime.now();
        OrderAddressVm address = createOrderAddressVm();

        OrderBriefVm vm = OrderBriefVm.builder()
                .id(100L)
                .email("user@test.com")
                .billingAddressVm(address)
                .totalPrice(BigDecimal.valueOf(500.00))
                .orderStatus(OrderStatus.PENDING)
                .deliveryMethod(DeliveryMethod.VIETTEL_POST)
                .deliveryStatus(DeliveryStatus.DELIVERING)
                .paymentStatus(PaymentStatus.PENDING)
                .createdOn(createdTime)
                .build();

        // Assert
        assertEquals(100L, vm.id());
        assertEquals("user@test.com", vm.email());
        assertEquals(address, vm.billingAddressVm());
        assertEquals(BigDecimal.valueOf(500.00), vm.totalPrice());
        assertEquals(OrderStatus.PENDING, vm.orderStatus());
        assertEquals(DeliveryMethod.VIETTEL_POST, vm.deliveryMethod());
        assertEquals(DeliveryStatus.DELIVERING, vm.deliveryStatus());
        assertEquals(PaymentStatus.PENDING, vm.paymentStatus());
        assertEquals(createdTime, vm.createdOn());
    }

    @Test
    void testOrderBriefVm_equalsAndHashCode() {
        // Arrange
        ZonedDateTime now = ZonedDateTime.now();
        OrderBriefVm vm1 = OrderBriefVm.builder()
                .id(1L)
                .email("test@example.com")
                .totalPrice(BigDecimal.valueOf(100))
                .orderStatus(OrderStatus.ACCEPTED)
                .createdOn(now)
                .build();

        OrderBriefVm vm2 = OrderBriefVm.builder()
                .id(1L)
                .email("test@example.com")
                .totalPrice(BigDecimal.valueOf(100))
                .orderStatus(OrderStatus.ACCEPTED)
                .createdOn(now)
                .build();

        // Assert
        assertEquals(vm1, vm2);
        assertEquals(vm1.hashCode(), vm2.hashCode());
    }

    @Test
    void testOrderBriefVm_toString() {
        // Arrange
        OrderBriefVm vm = OrderBriefVm.builder()
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
        assertTrue(result.contains("ACCEPTED"));
    }

    @Test
    void testOrderBriefVm_withMinimalData() {
        // Act
        OrderBriefVm vm = OrderBriefVm.builder()
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
}

