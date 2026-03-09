package com.yas.customer.viewmodel.customer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GuestUserVmTest {

    @Test
    void testGuestUserVm_constructor_shouldCreateValidObject() {
        // Act
        GuestUserVm vm = new GuestUserVm(
            "guest123",
            "guest@example.com",
            "guestPassword123"
        );

        // Assert
        assertNotNull(vm);
        assertEquals("guest123", vm.userId());
        assertEquals("guest@example.com", vm.email());
        assertEquals("guestPassword123", vm.password());
    }

    @Test
    void testGuestUserVm_withNullValues_shouldHandleNulls() {
        // Act
        GuestUserVm vm = new GuestUserVm(null, null, null);

        // Assert
        assertNotNull(vm);
        assertNull(vm.userId());
        assertNull(vm.email());
        assertNull(vm.password());
    }

    @Test
    void testGuestUserVm_equals_shouldWorkCorrectly() {
        // Arrange
        GuestUserVm vm1 = new GuestUserVm("guest1", "email1@test.com", "pass1");
        GuestUserVm vm2 = new GuestUserVm("guest1", "email1@test.com", "pass1");
        GuestUserVm vm3 = new GuestUserVm("guest2", "email2@test.com", "pass2");

        // Act & Assert
        assertEquals(vm1, vm2);
        assertNotEquals(vm1, vm3);
    }

    @Test
    void testGuestUserVm_hashCode_shouldBeConsistent() {
        // Arrange
        GuestUserVm vm1 = new GuestUserVm("guest", "guest@test.com", "password");
        GuestUserVm vm2 = new GuestUserVm("guest", "guest@test.com", "password");

        // Act & Assert
        assertEquals(vm1.hashCode(), vm2.hashCode());
    }

    @Test
    void testGuestUserVm_toString_shouldNotBeNull() {
        // Arrange
        GuestUserVm vm = new GuestUserVm("testGuest", "test@example.com", "testPass");

        // Act
        String result = vm.toString();

        // Assert
        assertNotNull(result);
    }
}
