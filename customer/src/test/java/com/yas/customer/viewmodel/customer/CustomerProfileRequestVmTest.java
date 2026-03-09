package com.yas.customer.viewmodel.customer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerProfileRequestVmTest {

    @Test
    void testCustomerProfileRequestVm_constructor_shouldCreateValidObject() {
        // Act
        CustomerProfileRequestVm vm = new CustomerProfileRequestVm(
            "John",
            "Doe",
            "john.doe@example.com"
        );

        // Assert
        assertNotNull(vm);
        assertEquals("John", vm.firstName());
        assertEquals("Doe", vm.lastName());
        assertEquals("john.doe@example.com", vm.email());
    }

    @Test
    void testCustomerProfileRequestVm_withNullValues_shouldHandleNulls() {
        // Act
        CustomerProfileRequestVm vm = new CustomerProfileRequestVm(null, null, null);

        // Assert
        assertNotNull(vm);
        assertNull(vm.firstName());
        assertNull(vm.lastName());
        assertNull(vm.email());
    }

    @Test
    void testCustomerProfileRequestVm_equals_shouldWorkCorrectly() {
        // Arrange
        CustomerProfileRequestVm vm1 = new CustomerProfileRequestVm("John", "Doe", "john@test.com");
        CustomerProfileRequestVm vm2 = new CustomerProfileRequestVm("John", "Doe", "john@test.com");
        CustomerProfileRequestVm vm3 = new CustomerProfileRequestVm("Jane", "Smith", "jane@test.com");

        // Act & Assert
        assertEquals(vm1, vm2);
        assertNotEquals(vm1, vm3);
    }

    @Test
    void testCustomerProfileRequestVm_hashCode_shouldBeConsistent() {
        // Arrange
        CustomerProfileRequestVm vm1 = new CustomerProfileRequestVm("Test", "User", "test@test.com");
        CustomerProfileRequestVm vm2 = new CustomerProfileRequestVm("Test", "User", "test@test.com");

        // Act & Assert
        assertEquals(vm1.hashCode(), vm2.hashCode());
    }

    @Test
    void testCustomerProfileRequestVm_toString_shouldNotBeNull() {
        // Arrange
        CustomerProfileRequestVm vm = new CustomerProfileRequestVm("First", "Last", "email@domain.com");

        // Act
        String result = vm.toString();

        // Assert
        assertNotNull(result);
    }
}
