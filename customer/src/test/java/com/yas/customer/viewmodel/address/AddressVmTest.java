package com.yas.customer.viewmodel.address;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressVmTest {

    @Test
    void testAddressVm_builder_shouldCreateValidObject() {
        // Act
        AddressVm vm = AddressVm.builder()
            .id(1L)
            .contactName("John Doe")
            .phone("123456789")
            .addressLine1("123 Main St")
            .city("City")
            .zipCode("12345")
            .districtId(10L)
            .stateOrProvinceId(20L)
            .countryId(30L)
            .build();

        // Assert
        assertNotNull(vm);
        assertEquals(1L, vm.id());
        assertEquals("John Doe", vm.contactName());
        assertEquals("123456789", vm.phone());
        assertEquals("123 Main St", vm.addressLine1());
        assertEquals("City", vm.city());
        assertEquals("12345", vm.zipCode());
        assertEquals(10L, vm.districtId());
        assertEquals(20L, vm.stateOrProvinceId());
        assertEquals(30L, vm.countryId());
    }

    @Test
    void testAddressVm_builderWithNullValues_shouldHandleNulls() {
        // Act
        AddressVm vm = AddressVm.builder().build();

        // Assert
        assertNotNull(vm);
        assertNull(vm.id());
        assertNull(vm.contactName());
        assertNull(vm.phone());
    }

    @Test
    void testAddressVm_equals_shouldWorkCorrectly() {
        // Arrange
        AddressVm vm1 = AddressVm.builder()
            .id(1L)
            .contactName("John")
            .phone("123")
            .addressLine1("Address")
            .city("City")
            .zipCode("12345")
            .districtId(10L)
            .stateOrProvinceId(20L)
            .countryId(30L)
            .build();

        AddressVm vm2 = AddressVm.builder()
            .id(1L)
            .contactName("John")
            .phone("123")
            .addressLine1("Address")
            .city("City")
            .zipCode("12345")
            .districtId(10L)
            .stateOrProvinceId(20L)
            .countryId(30L)
            .build();

        AddressVm vm3 = AddressVm.builder()
            .id(2L)
            .contactName("Jane")
            .build();

        // Act & Assert
        assertEquals(vm1, vm2);
        assertNotEquals(vm1, vm3);
    }

    @Test
    void testAddressVm_hashCode_shouldBeConsistent() {
        // Arrange
        AddressVm vm1 = AddressVm.builder()
            .id(1L)
            .contactName("John")
            .phone("123")
            .build();

        AddressVm vm2 = AddressVm.builder()
            .id(1L)
            .contactName("John")
            .phone("123")
            .build();

        // Act & Assert
        assertEquals(vm1.hashCode(), vm2.hashCode());
    }

    @Test
    void testAddressVm_toString_shouldNotBeNull() {
        // Arrange
        AddressVm vm = AddressVm.builder()
            .id(1L)
            .contactName("Test User")
            .build();

        // Act
        String result = vm.toString();

        // Assert
        assertNotNull(result);
    }
}
