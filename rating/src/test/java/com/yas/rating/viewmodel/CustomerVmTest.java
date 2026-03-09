package com.yas.rating.viewmodel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerVmTest {

    @Test
    void testCustomerVm_shouldCreateWithAllFields() {
        // Act
        CustomerVm vm = new CustomerVm("johndoe", "john@example.com", "John", "Doe");

        // Assert
        assertNotNull(vm);
        assertEquals("johndoe", vm.username());
        assertEquals("john@example.com", vm.email());
        assertEquals("John", vm.firstName());
        assertEquals("Doe", vm.lastName());
    }

    @Test
    void testCustomerVm_withNullValues() {
        // Act
        CustomerVm vm = new CustomerVm(null, null, null, null);

        // Assert
        assertNotNull(vm);
        assertNull(vm.username());
        assertNull(vm.email());
        assertNull(vm.firstName());
        assertNull(vm.lastName());
    }

    @Test
    void testCustomerVm_recordEquality() {
        // Arrange
        CustomerVm vm1 = new CustomerVm("user1", "user1@test.com", "First", "Last");
        CustomerVm vm2 = new CustomerVm("user1", "user1@test.com", "First", "Last");

        // Assert
        assertEquals(vm1, vm2);
    }

    @Test
    void testCustomerVm_recordInequality() {
        // Arrange
        CustomerVm vm1 = new CustomerVm("user1", "user1@test.com", "First", "Last");
        CustomerVm vm2 = new CustomerVm("user2", "user2@test.com", "Other", "Name");

        // Assert
        assertNotEquals(vm1, vm2);
    }

    @Test
    void testCustomerVm_recordToString() {
        // Arrange
        CustomerVm vm = new CustomerVm("testuser", "test@example.com", "Test", "User");

        // Act
        String result = vm.toString();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("testuser"));
        assertTrue(result.contains("test@example.com"));
    }
}
