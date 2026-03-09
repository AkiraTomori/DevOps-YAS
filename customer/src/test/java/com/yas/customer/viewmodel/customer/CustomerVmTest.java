package com.yas.customer.viewmodel.customer;

import org.junit.jupiter.api.Test;
import org.keycloak.representations.idm.UserRepresentation;

import static org.junit.jupiter.api.Assertions.*;

class CustomerVmTest {

    @Test
    void testCustomerVm_constructor_shouldCreateValidObject() {
        // Act
        CustomerVm vm = new CustomerVm(
            "id123",
            "johndoe",
            "john@example.com",
            "John",
            "Doe"
        );

        // Assert
        assertNotNull(vm);
        assertEquals("id123", vm.id());
        assertEquals("johndoe", vm.username());
        assertEquals("john@example.com", vm.email());
        assertEquals("John", vm.firstName());
        assertEquals("Doe", vm.lastName());
    }

    @Test
    void testCustomerVm_fromUserRepresentation_shouldConvertCorrectly() {
        // Arrange
        UserRepresentation userRep = new UserRepresentation();
        userRep.setId("user-id-1");
        userRep.setUsername("testuser");
        userRep.setEmail("test@example.com");
        userRep.setFirstName("Test");
        userRep.setLastName("User");

        // Act
        CustomerVm vm = CustomerVm.fromUserRepresentation(userRep);

        // Assert
        assertNotNull(vm);
        assertEquals("user-id-1", vm.id());
        assertEquals("testuser", vm.username());
        assertEquals("test@example.com", vm.email());
        assertEquals("Test", vm.firstName());
        assertEquals("User", vm.lastName());
    }

    @Test
    void testCustomerVm_withNullValues_shouldHandleNulls() {
        // Act
        CustomerVm vm = new CustomerVm(null, null, null, null, null);

        // Assert
        assertNotNull(vm);
        assertNull(vm.id());
        assertNull(vm.username());
        assertNull(vm.email());
        assertNull(vm.firstName());
        assertNull(vm.lastName());
    }

    @Test
    void testCustomerVm_equals_shouldWorkCorrectly() {
        // Arrange
        CustomerVm vm1 = new CustomerVm("id1", "user1", "email1", "First1", "Last1");
        CustomerVm vm2 = new CustomerVm("id1", "user1", "email1", "First1", "Last1");
        CustomerVm vm3 = new CustomerVm("id2", "user2", "email2", "First2", "Last2");

        // Act & Assert
        assertEquals(vm1, vm2);
        assertNotEquals(vm1, vm3);
    }

    @Test
    void testCustomerVm_hashCode_shouldBeConsistent() {
        // Arrange
        CustomerVm vm1 = new CustomerVm("id", "user", "email", "first", "last");
        CustomerVm vm2 = new CustomerVm("id", "user", "email", "first", "last");

        // Act & Assert
        assertEquals(vm1.hashCode(), vm2.hashCode());
    }

    @Test
    void testCustomerVm_toString_shouldNotBeNull() {
        // Arrange
        CustomerVm vm = new CustomerVm("id", "username", "email@test.com", "First", "Last");

        // Act
        String result = vm.toString();

        // Assert
        assertNotNull(result);
    }
}
