package com.yas.order.viewmodel.order;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderListVmTest {

    @Test
    void testOrderListVm_builder() {
        // Arrange
        OrderBriefVm order1 = OrderBriefVm.builder().id(1L).build();
        OrderBriefVm order2 = OrderBriefVm.builder().id(2L).build();
        List<OrderBriefVm> orders = List.of(order1, order2);

        // Act
        OrderListVm vm = OrderListVm.builder()
                .orderList(orders)
                .totalElements(10L)
                .totalPages(2)
                .build();

        // Assert
        assertNotNull(vm);
        assertEquals(2, vm.orderList().size());
        assertEquals(10L, vm.totalElements());
        assertEquals(2, vm.totalPages());
    }

    @Test
    void testOrderListVm_toBuilder() {
        // Arrange
        OrderListVm original = OrderListVm.builder()
                .orderList(List.of())
                .totalElements(5L)
                .totalPages(1)
                .build();

        // Act
        OrderListVm modified = original.toBuilder()
                .totalElements(10L)
                .build();

        // Assert
        assertEquals(10L, modified.totalElements());
        assertEquals(1, modified.totalPages());
    }

    @Test
    void testOrderListVm_getters() {
        // Arrange
        List<OrderBriefVm> orders = List.of(
                OrderBriefVm.builder().id(1L).build(),
                OrderBriefVm.builder().id(2L).build(),
                OrderBriefVm.builder().id(3L).build()
        );

        // Act
        OrderListVm vm = OrderListVm.builder()
                .orderList(orders)
                .totalElements(100L)
                .totalPages(10)
                .build();

        // Assert
        assertEquals(3, vm.orderList().size());
        assertEquals(100L, vm.totalElements());
        assertEquals(10, vm.totalPages());
    }

    @Test
    void testOrderListVm_equalsAndHashCode() {
        // Arrange
        List<OrderBriefVm> orders = List.of(OrderBriefVm.builder().id(1L).build());
        OrderListVm vm1 = OrderListVm.builder()
                .orderList(orders)
                .totalElements(5L)
                .totalPages(1)
                .build();

        OrderListVm vm2 = OrderListVm.builder()
                .orderList(orders)
                .totalElements(5L)
                .totalPages(1)
                .build();

        // Assert
        assertEquals(vm1, vm2);
        assertEquals(vm1.hashCode(), vm2.hashCode());
    }

    @Test
    void testOrderListVm_toString() {
        // Arrange
        OrderListVm vm = OrderListVm.builder()
                .orderList(List.of())
                .totalElements(0L)
                .totalPages(0)
                .build();

        // Act
        String result = vm.toString();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("0"));
    }

    @Test
    void testOrderListVm_withEmptyList() {
        // Act
        OrderListVm vm = OrderListVm.builder()
                .orderList(List.of())
                .totalElements(0L)
                .totalPages(0)
                .build();

        // Assert
        assertNotNull(vm);
        assertTrue(vm.orderList().isEmpty());
        assertEquals(0L, vm.totalElements());
        assertEquals(0, vm.totalPages());
    }
}
