package com.yas.customer.viewmodel.customer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerPostVmTest {

    @Test
    void testCustomerPostVm_constructor_shouldCreateValidObject() {
        // Act
        CustomerPostVm vm = new CustomerPostVm(
            "johndoe",
            "john@example.com",
            "John",
            "Doe",
            "Password123!",
            "CUSTOMER"
        );

        // Assert
        assertNotNull(vm);
        assertEquals("johndoe", vm.username());
        assertEquals("john@example.com", vm.email());
        assertEquals("John", vm.firstName());
        assertEquals("Doe", vm.lastName());
        assertEquals("Password123!", vm.password());
        assertEquals("CUSTOMER", vm.role());
    }

    @Test
    void testCustomerPostVm_withNullValues_shouldHandleNulls() {
        // Act
        CustomerPostVm vm = new CustomerPostVm(null, null, null, null, null, null);

        // Assert
        assertNotNull(vm);
        assertNull(vm.username());
        assertNull(vm.email());
        assertNull(vm.firstName());
        assertNull(vm.lastName());
        assertNull(vm.password());
        assertNull(vm.role());
    }

    @Test
    void testCustomerPostVm_equals_shouldWorkCorrectly() {
        // Arrange
        CustomerPostVm vm1 = new CustomerPostVm("user1", "email@test.com", "First", "Last", "pass", "ROLE");
        CustomerPostVm vm2 = new CustomerPostVm("user1", "email@test.com", "First", "Last", "pass", "ROLE");
        CustomerPostVm vm3 = new CustomerPostVm("user2", "other@test.com", "Other", "Name", "pass2", "ADMIN");

        // Act & Assert
        assertEquals(vm1, vm2);
        assertNotEquals(vm1, vm3);
    }

    @Test
    void testCustomerPostVm_hashCode_shouldBeConsistent() {
        // Arrange
        CustomerPostVm vm1 = new CustomerPostVm("user", "email", "first", "last", "pass", "role");
        CustomerPostVm vm2 = new CustomerPostVm("user", "email", "first", "last", "pass", "role");

        // Act & Assert
        assertEquals(vm1.hashCode(), vm2.hashCode());
    }

    @Test
    void testCustomerPostVm_toString_shouldNotBeNull() {
        // Arrange
        CustomerPostVm vm = new CustomerPostVm("test", "test@test.com", "Test", "User", "pass", "USER");

        // Act
        String result = vm.toString();

        // Assert
        assertNotNull(result);
    }
}
