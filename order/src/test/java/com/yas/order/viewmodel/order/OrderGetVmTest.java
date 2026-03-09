package com.yas.order.viewmodel.order;

import com.yas.order.model.enumeration.DeliveryMethod;
import com.yas.order.model.enumeration.DeliveryStatus;
import com.yas.order.model.enumeration.OrderStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderGetVmTest {

    @Test
    void testOrderGetVm_constructor() {
        // Arrange
        ZonedDateTime now = ZonedDateTime.now();
        OrderItemGetVm orderItem = new OrderItemGetVm(
                1L,
                1L,
                "Product",
                2,
                BigDecimal.valueOf(50),
                BigDecimal.valueOf(5),
                BigDecimal.valueOf(2.5)
        );

        // Act
        OrderGetVm vm = new OrderGetVm(
                1L,
                OrderStatus.ACCEPTED,
                BigDecimal.valueOf(100),
                DeliveryStatus.PREPARING,
                DeliveryMethod.YAS_EXPRESS,
                List.of(orderItem),
                now
        );

        // Assert
        assertNotNull(vm);
        assertEquals(1L, vm.id());
        assertEquals(OrderStatus.ACCEPTED, vm.orderStatus());
        assertEquals(BigDecimal.valueOf(100), vm.totalPrice());
        assertEquals(DeliveryStatus.PREPARING, vm.deliveryStatus());
        assertEquals(DeliveryMethod.YAS_EXPRESS, vm.deliveryMethod());
        assertEquals(1, vm.orderItems().size());
        assertEquals(now, vm.createdOn());
    }

    @Test
    void testOrderGetVm_getters() {
        // Arrange
        ZonedDateTime createdTime = ZonedDateTime.now();
        List<OrderItemGetVm> items = List.of(
                new OrderItemGetVm(1L, 1L, "Product 1", 2, BigDecimal.valueOf(50), BigDecimal.valueOf(5), BigDecimal.valueOf(2.5)),
                new OrderItemGetVm(2L, 2L, "Product 2", 3, BigDecimal.valueOf(75), BigDecimal.valueOf(7), BigDecimal.valueOf(3.5))
        );

        OrderGetVm vm = new OrderGetVm(
                100L,
                OrderStatus.PENDING,
                BigDecimal.valueOf(500.00),
                DeliveryStatus.DELIVERING,
                DeliveryMethod.VIETTEL_POST,
                items,
                createdTime
        );

        // Assert
        assertEquals(100L, vm.id());
        assertEquals(OrderStatus.PENDING, vm.orderStatus());
        assertEquals(BigDecimal.valueOf(500.00), vm.totalPrice());
        assertEquals(DeliveryStatus.DELIVERING, vm.deliveryStatus());
        assertEquals(DeliveryMethod.VIETTEL_POST, vm.deliveryMethod());
        assertEquals(2, vm.orderItems().size());
        assertEquals(createdTime, vm.createdOn());
    }

    @Test
    void testOrderGetVm_equalsAndHashCode() {
        // Arrange
        ZonedDateTime now = ZonedDateTime.now();
        List<OrderItemGetVm> items = List.of();

        OrderGetVm vm1 = new OrderGetVm(
                1L,
                OrderStatus.ACCEPTED,
                BigDecimal.valueOf(100),
                DeliveryStatus.PREPARING,
                DeliveryMethod.YAS_EXPRESS,
                items,
                now
        );

        OrderGetVm vm2 = new OrderGetVm(
                1L,
                OrderStatus.ACCEPTED,
                BigDecimal.valueOf(100),
                DeliveryStatus.PREPARING,
                DeliveryMethod.YAS_EXPRESS,
                items,
                now
        );

        // Assert
        assertEquals(vm1, vm2);
        assertEquals(vm1.hashCode(), vm2.hashCode());
    }

    @Test
    void testOrderGetVm_toString() {
        // Arrange
        OrderGetVm vm = new OrderGetVm(
                1L,
                OrderStatus.ACCEPTED,
                BigDecimal.valueOf(100),
                DeliveryStatus.PREPARING,
                DeliveryMethod.YAS_EXPRESS,
                List.of(),
                ZonedDateTime.now()
        );

        // Act
        String result = vm.toString();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("1"));
        assertTrue(result.contains("ACCEPTED"));
    }

    @Test
    void testOrderGetVm_withEmptyOrderItems() {
        // Act
        OrderGetVm vm = new OrderGetVm(
                1L,
                OrderStatus.PENDING,
                BigDecimal.valueOf(0),
                DeliveryStatus.PREPARING,
                DeliveryMethod.YAS_EXPRESS,
                List.of(),
                ZonedDateTime.now()
        );

        // Assert
        assertNotNull(vm);
        assertEquals(0, vm.orderItems().size());
    }
}
