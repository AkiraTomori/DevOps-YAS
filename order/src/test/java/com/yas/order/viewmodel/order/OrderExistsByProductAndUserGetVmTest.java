package com.yas.order.viewmodel.order;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderExistsByProductAndUserGetVmTest {

    @Test
    void testOrderExistsByProductAndUserGetVm_whenOrderExists() {
        // Act
        OrderExistsByProductAndUserGetVm vm = new OrderExistsByProductAndUserGetVm(true);

        // Assert
        assertNotNull(vm);
        assertTrue(vm.isPresent());
    }

    @Test
    void testOrderExistsByProductAndUserGetVm_whenOrderDoesNotExist() {
        // Act
        OrderExistsByProductAndUserGetVm vm = new OrderExistsByProductAndUserGetVm(false);

        // Assert
        assertNotNull(vm);
        assertFalse(vm.isPresent());
    }

    @Test
    void testOrderExistsByProductAndUserGetVm_equalsAndHashCode() {
        // Arrange
        OrderExistsByProductAndUserGetVm vm1 = new OrderExistsByProductAndUserGetVm(true);
        OrderExistsByProductAndUserGetVm vm2 = new OrderExistsByProductAndUserGetVm(true);

        // Assert
        assertEquals(vm1, vm2);
        assertEquals(vm1.hashCode(), vm2.hashCode());
    }

    @Test
    void testOrderExistsByProductAndUserGetVm_notEquals() {
        // Arrange
        OrderExistsByProductAndUserGetVm vm1 = new OrderExistsByProductAndUserGetVm(true);
        OrderExistsByProductAndUserGetVm vm2 = new OrderExistsByProductAndUserGetVm(false);

        // Assert
        assertNotEquals(vm1, vm2);
    }

    @Test
    void testOrderExistsByProductAndUserGetVm_toString() {
        // Arrange
        OrderExistsByProductAndUserGetVm vm = new OrderExistsByProductAndUserGetVm(true);

        // Act
        String result = vm.toString();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("true"));
    }
}
