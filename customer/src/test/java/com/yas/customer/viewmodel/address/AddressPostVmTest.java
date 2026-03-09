package com.yas.customer.viewmodel.address;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressPostVmTest {

    @Test
    void testAddressPostVm_constructor_shouldCreateValidObject() {
        // Act
        AddressPostVm vm = new AddressPostVm(
            "John Doe",
            "123456789",
            "123 Main Street",
            "New York",
            "10001",
            10L,
            20L,
            30L
        );

        // Assert
        assertNotNull(vm);
        assertEquals("John Doe", vm.contactName());
        assertEquals("123456789", vm.phone());
        assertEquals("123 Main Street", vm.addressLine1());
        assertEquals("New York", vm.city());
        assertEquals("10001", vm.zipCode());
        assertEquals(10L, vm.districtId());
        assertEquals(20L, vm.stateOrProvinceId());
        assertEquals(30L, vm.countryId());
    }

    @Test
    void testAddressPostVm_withNullValues_shouldHandleNulls() {
        // Act
        AddressPostVm vm = new AddressPostVm(null, null, null, null, null, null, null, null);

        // Assert
        assertNotNull(vm);
        assertNull(vm.contactName());
        assertNull(vm.phone());
        assertNull(vm.addressLine1());
    }

    @Test
    void testAddressPostVm_equals_shouldWorkCorrectly() {
        // Arrange
        AddressPostVm vm1 = new AddressPostVm(
            "John", "123", "Address", "City", "12345", 10L, 20L, 30L
        );

        AddressPostVm vm2 = new AddressPostVm(
            "John", "123", "Address", "City", "12345", 10L, 20L, 30L
        );

        AddressPostVm vm3 = new AddressPostVm(
            "Jane", "456", "Other", "Town", "54321", 11L, 21L, 31L
        );

        // Act & Assert
        assertEquals(vm1, vm2);
        assertNotEquals(vm1, vm3);
    }

    @Test
    void testAddressPostVm_hashCode_shouldBeConsistent() {
        // Arrange
        AddressPostVm vm1 = new AddressPostVm(
            "John", "123", "Address", "City", "12345", 10L, 20L, 30L
        );

        AddressPostVm vm2 = new AddressPostVm(
            "John", "123", "Address", "City", "12345", 10L, 20L, 30L
        );

        // Act & Assert
        assertEquals(vm1.hashCode(), vm2.hashCode());
    }

    @Test
    void testAddressPostVm_toString_shouldNotBeNull() {
        // Arrange
        AddressPostVm vm = new AddressPostVm(
            "Test User", "999", "Test Address", "TestCity", "00000", 1L, 2L, 3L
        );

        // Act
        String result = vm.toString();

        // Assert
        assertNotNull(result);
    }
}
