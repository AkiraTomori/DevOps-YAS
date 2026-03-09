package com.yas.customer.viewmodel.customer;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerListVmTest {

    @Test
    void testCustomerListVm_constructor_shouldCreateValidObject() {
        // Arrange
        CustomerAdminVm customer1 = new CustomerAdminVm("id1", "user1", "email1@test.com", "John", "Doe", LocalDateTime.now());
        CustomerAdminVm customer2 = new CustomerAdminVm("id2", "user2", "email2@test.com", "Jane", "Smith", LocalDateTime.now());
        List<CustomerAdminVm> customers = Arrays.asList(customer1, customer2);

        // Act
        CustomerListVm vm = new CustomerListVm(2, customers, 1);

        // Assert
        assertNotNull(vm);
        assertEquals(2, vm.totalUser());
        assertEquals(2, vm.customers().size());
        assertEquals(1, vm.totalPage());
    }

    @Test
    void testCustomerListVm_withEmptyList_shouldHandleEmptyCustomers() {
        // Act
        CustomerListVm vm = new CustomerListVm(0, Collections.emptyList(), 0);

        // Assert
        assertNotNull(vm);
        assertEquals(0, vm.totalUser());
        assertTrue(vm.customers().isEmpty());
        assertEquals(0, vm.totalPage());
    }

    @Test
    void testCustomerListVm_equals_shouldWorkCorrectly() {
        // Arrange
        List<CustomerAdminVm> customers = Collections.singletonList(
            new CustomerAdminVm("id1", "user1", "email1", "First", "Last", LocalDateTime.now())
        );

        CustomerListVm vm1 = new CustomerListVm(1, customers, 1);
        CustomerListVm vm2 = new CustomerListVm(1, customers, 1);
        CustomerListVm vm3 = new CustomerListVm(2, customers, 2);

        // Act & Assert
        assertEquals(vm1, vm2);
        assertNotEquals(vm1, vm3);
    }

    @Test
    void testCustomerListVm_hashCode_shouldBeConsistent() {
        // Arrange
        List<CustomerAdminVm> customers = Collections.emptyList();
        CustomerListVm vm1 = new CustomerListVm(0, customers, 0);
        CustomerListVm vm2 = new CustomerListVm(0, customers, 0);

        // Act & Assert
        assertEquals(vm1.hashCode(), vm2.hashCode());
    }

    @Test
    void testCustomerListVm_toString_shouldNotBeNull() {
        // Arrange
        CustomerListVm vm = new CustomerListVm(10, Collections.emptyList(), 2);

        // Act
        String result = vm.toString();

        // Assert
        assertNotNull(result);
    }
}
