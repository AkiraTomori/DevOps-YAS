package com.yas.customer.viewmodel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ErrorVmTest {

    @Test
    void testErrorVm_constructor_shouldCreateValidObject() {
        // Act
        ErrorVm vm = new ErrorVm("404", "Not Found", "Resource not found");

        // Assert
        assertNotNull(vm);
        assertEquals("404", vm.statusCode());
        assertEquals("Not Found", vm.title());
        assertEquals("Resource not found", vm.detail());
    }

    @Test
    void testErrorVm_withNullValues_shouldHandleNulls() {
        // Act
        ErrorVm vm = new ErrorVm(null, null, null);

        // Assert
        assertNotNull(vm);
        assertNull(vm.statusCode());
        assertNull(vm.title());
        assertNull(vm.detail());
    }

    @Test
    void testErrorVm_equals_shouldWorkCorrectly() {
        // Arrange
        ErrorVm vm1 = new ErrorVm("500", "Error", "Server error");
        ErrorVm vm2 = new ErrorVm("500", "Error", "Server error");
        ErrorVm vm3 = new ErrorVm("400", "Bad Request", "Invalid input");

        // Act & Assert
        assertEquals(vm1, vm2);
        assertNotEquals(vm1, vm3);
    }

    @Test
    void testErrorVm_hashCode_shouldBeConsistent() {
        // Arrange
        ErrorVm vm1 = new ErrorVm("403", "Forbidden", "Access denied");
        ErrorVm vm2 = new ErrorVm("403", "Forbidden", "Access denied");

        // Act & Assert
        assertEquals(vm1.hashCode(), vm2.hashCode());
    }

    @Test
    void testErrorVm_toString_shouldNotBeNull() {
        // Arrange
        ErrorVm vm = new ErrorVm("200", "OK", "Success");

        // Act
        String result = vm.toString();

        // Assert
        assertNotNull(result);
    }
}
