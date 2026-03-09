package com.yas.customer.viewmodel.customer;

import org.junit.jupiter.api.Test;
import org.keycloak.representations.idm.UserRepresentation;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CustomerAdminVmTest {

    @Test
    void testCustomerAdminVm_constructor_shouldCreateValidObject() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();

        // Act
        CustomerAdminVm vm = new CustomerAdminVm(
            "id123",
            "username",
            "user@example.com",
            "John",
            "Doe",
            now
        );

        // Assert
        assertNotNull(vm);
        assertEquals("id123", vm.id());
        assertEquals("username", vm.username());
        assertEquals("user@example.com", vm.email());
        assertEquals("John", vm.firstName());
        assertEquals("Doe", vm.lastName());
        assertEquals(now, vm.createdTimestamp());
    }

    @Test
    void testCustomerAdminVm_fromUserRepresentation_shouldConvertCorrectly() {
        // Arrange
        UserRepresentation userRep = new UserRepresentation();
        userRep.setId("user-id-1");
        userRep.setUsername("testuser");
        userRep.setEmail("test@example.com");
        userRep.setFirstName("Test");
        userRep.setLastName("User");
        userRep.setCreatedTimestamp(System.currentTimeMillis());

        // Act
        CustomerAdminVm vm = CustomerAdminVm.fromUserRepresentation(userRep);

        // Assert
        assertNotNull(vm);
        assertEquals("user-id-1", vm.id());
        assertEquals("testuser", vm.username());
        assertEquals("test@example.com", vm.email());
        assertEquals("Test", vm.firstName());
        assertEquals("User", vm.lastName());
        assertNotNull(vm.createdTimestamp());
    }

    @Test
    void testCustomerAdminVm_withNullValues_shouldHandleNulls() {
        // Act
        CustomerAdminVm vm = new CustomerAdminVm(null, null, null, null, null, null);

        // Assert
        assertNotNull(vm);
        assertNull(vm.id());
        assertNull(vm.username());
        assertNull(vm.email());
    }

    @Test
    void testCustomerAdminVm_equals_shouldWorkCorrectly() {
        // Arrange
        LocalDateTime time = LocalDateTime.now();
        CustomerAdminVm vm1 = new CustomerAdminVm("id1", "user1", "email1", "First1", "Last1", time);
        CustomerAdminVm vm2 = new CustomerAdminVm("id1", "user1", "email1", "First1", "Last1", time);
        CustomerAdminVm vm3 = new CustomerAdminVm("id2", "user2", "email2", "First2", "Last2", time);

        // Act & Assert
        assertEquals(vm1, vm2);
        assertNotEquals(vm1, vm3);
    }

    @Test
    void testCustomerAdminVm_hashCode_shouldBeConsistent() {
        // Arrange
        LocalDateTime time = LocalDateTime.now();
        CustomerAdminVm vm1 = new CustomerAdminVm("id1", "user1", "email1", "First", "Last", time);
        CustomerAdminVm vm2 = new CustomerAdminVm("id1", "user1", "email1", "First", "Last", time);

        // Act & Assert
        assertEquals(vm1.hashCode(), vm2.hashCode());
    }
}
