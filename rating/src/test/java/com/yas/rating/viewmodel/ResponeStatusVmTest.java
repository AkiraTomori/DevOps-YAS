package com.yas.rating.viewmodel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResponeStatusVmTest {

    @Test
    void testResponeStatusVm_shouldCreateWithAllFields() {
        // Act
        ResponeStatusVm vm = new ResponeStatusVm("Success", "Operation completed successfully", "200");

        // Assert
        assertNotNull(vm);
        assertEquals("Success", vm.title());
        assertEquals("Operation completed successfully", vm.message());
        assertEquals("200", vm.statusCode());
    }

    @Test
    void testResponeStatusVm_withErrorStatus() {
        // Act
        ResponeStatusVm vm = new ResponeStatusVm("Error", "An error occurred", "500");

        // Assert
        assertNotNull(vm);
        assertEquals("Error", vm.title());
        assertEquals("An error occurred", vm.message());
        assertEquals("500", vm.statusCode());
    }

    @Test
    void testResponeStatusVm_withNullValues() {
        // Act
        ResponeStatusVm vm = new ResponeStatusVm(null, null, null);

        // Assert
        assertNotNull(vm);
        assertNull(vm.title());
        assertNull(vm.message());
        assertNull(vm.statusCode());
    }

    @Test
    void testResponeStatusVm_recordEquality() {
        // Arrange
        ResponeStatusVm vm1 = new ResponeStatusVm("Title", "Message", "Code");
        ResponeStatusVm vm2 = new ResponeStatusVm("Title", "Message", "Code");

        // Assert
        assertEquals(vm1, vm2);
    }

    @Test
    void testResponeStatusVm_recordInequality() {
        // Arrange
        ResponeStatusVm vm1 = new ResponeStatusVm("Title1", "Message1", "Code1");
        ResponeStatusVm vm2 = new ResponeStatusVm("Title2", "Message2", "Code2");

        // Assert
        assertNotEquals(vm1, vm2);
    }

    @Test
    void testResponeStatusVm_recordToString() {
        // Arrange
        ResponeStatusVm vm = new ResponeStatusVm("Test Title", "Test Message", "201");

        // Act
        String result = vm.toString();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("Test Title"));
        assertTrue(result.contains("Test Message"));
        assertTrue(result.contains("201"));
    }
}
