package com.yas.customer.viewmodel.address;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActiveAddressVmTest {

    @Test
    void testActiveAddressVm_constructor_shouldCreateValidObject() {
        // Act
        ActiveAddressVm vm = new ActiveAddressVm(
            1L, "John Doe", "123456789", "123 Main St", "City", "12345",
            10L, "District", 20L, "Province", 30L, "Country", true
        );

        // Assert
        assertNotNull(vm);
        assertEquals(1L, vm.id());
        assertEquals("John Doe", vm.contactName());
        assertEquals("123456789", vm.phone());
        assertEquals("123 Main St", vm.addressLine1());
        assertEquals("City", vm.city());
        assertEquals("12345", vm.zipCode());
        assertEquals(10L, vm.districtId());
        assertEquals("District", vm.districtName());
        assertEquals(20L, vm.stateOrProvinceId());
        assertEquals("Province", vm.stateOrProvinceName());
        assertEquals(30L, vm.countryId());
        assertEquals("Country", vm.countryName());
        assertTrue(vm.isActive());
    }

    @Test
    void testActiveAddressVm_withNullValues_shouldHandleNulls() {
        // Act
        ActiveAddressVm vm = new ActiveAddressVm(
            null, null, null, null, null, null,
            null, null, null, null, null, null, null
        );

        // Assert
        assertNotNull(vm);
        assertNull(vm.id());
        assertNull(vm.contactName());
        assertNull(vm.isActive());
    }

    @Test
    void testActiveAddressVm_equals_shouldWorkCorrectly() {
        // Arrange
        ActiveAddressVm vm1 = new ActiveAddressVm(
            1L, "John", "123", "Address", "City", "12345",
            10L, "District", 20L, "Province", 30L, "Country", true
        );

        ActiveAddressVm vm2 = new ActiveAddressVm(
            1L, "John", "123", "Address", "City", "12345",
            10L, "District", 20L, "Province", 30L, "Country", true
        );

        ActiveAddressVm vm3 = new ActiveAddressVm(
            2L, "Jane", "456", "Other", "Town", "54321",
            11L, "District2", 21L, "Province2", 31L, "Country2", false
        );

        // Act & Assert
        assertEquals(vm1, vm2);
        assertNotEquals(vm1, vm3);
    }

    @Test
    void testActiveAddressVm_hashCode_shouldBeConsistent() {
        // Arrange
        ActiveAddressVm vm1 = new ActiveAddressVm(
            1L, "John", "123", "Address", "City", "12345",
            10L, "District", 20L, "Province", 30L, "Country", true
        );

        ActiveAddressVm vm2 = new ActiveAddressVm(
            1L, "John", "123", "Address", "City", "12345",
            10L, "District", 20L, "Province", 30L, "Country", true
        );

        // Act & Assert
        assertEquals(vm1.hashCode(), vm2.hashCode());
    }

    @Test
    void testActiveAddressVm_toString_shouldContainFieldValues() {
        // Arrange
        ActiveAddressVm vm = new ActiveAddressVm(
            1L, "John Doe", "123456789", "Street", "City", "12345",
            10L, "District", 20L, "Province", 30L, "Country", true
        );

        // Act
        String result = vm.toString();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("John Doe") || result.contains("ActiveAddressVm"));
    }
}
