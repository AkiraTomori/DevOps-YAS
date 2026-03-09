package com.yas.customer.viewmodel.address;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressDetailVmTest {

    @Test
    void testAddressDetailVm_builder_shouldCreateValidObject() {
        // Act
        AddressDetailVm vm = AddressDetailVm.builder()
            .id(1L)
            .contactName("John Doe")
            .phone("123456789")
            .addressLine1("123 Main St")
            .city("City")
            .zipCode("12345")
            .districtId(10L)
            .districtName("District")
            .stateOrProvinceId(20L)
            .stateOrProvinceName("Province")
            .countryId(30L)
            .countryName("Country")
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
        assertEquals("District", vm.districtName());
        assertEquals(20L, vm.stateOrProvinceId());
        assertEquals("Province", vm.stateOrProvinceName());
        assertEquals(30L, vm.countryId());
        assertEquals("Country", vm.countryName());
    }

    @Test
    void testAddressDetailVm_builderWithNullValues_shouldHandleNulls() {
        // Act
        AddressDetailVm vm = AddressDetailVm.builder().build();

        // Assert
        assertNotNull(vm);
        assertNull(vm.id());
        assertNull(vm.contactName());
        assertNull(vm.phone());
    }

    @Test
    void testAddressDetailVm_equals_shouldWorkCorrectly() {
        // Arrange
        AddressDetailVm vm1 = AddressDetailVm.builder()
            .id(1L)
            .contactName("John")
            .phone("123")
            .addressLine1("Address")
            .city("City")
            .zipCode("12345")
            .districtId(10L)
            .districtName("District")
            .stateOrProvinceId(20L)
            .stateOrProvinceName("Province")
            .countryId(30L)
            .countryName("Country")
            .build();

        AddressDetailVm vm2 = AddressDetailVm.builder()
            .id(1L)
            .contactName("John")
            .phone("123")
            .addressLine1("Address")
            .city("City")
            .zipCode("12345")
            .districtId(10L)
            .districtName("District")
            .stateOrProvinceId(20L)
            .stateOrProvinceName("Province")
            .countryId(30L)
            .countryName("Country")
            .build();

        AddressDetailVm vm3 = AddressDetailVm.builder()
            .id(2L)
            .contactName("Jane")
            .build();

        // Act & Assert
        assertEquals(vm1, vm2);
        assertNotEquals(vm1, vm3);
    }

    @Test
    void testAddressDetailVm_hashCode_shouldBeConsistent() {
        // Arrange
        AddressDetailVm vm1 = AddressDetailVm.builder()
            .id(1L)
            .contactName("John")
            .phone("123")
            .build();

        AddressDetailVm vm2 = AddressDetailVm.builder()
            .id(1L)
            .contactName("John")
            .phone("123")
            .build();

        // Act & Assert
        assertEquals(vm1.hashCode(), vm2.hashCode());
    }

    @Test
    void testAddressDetailVm_toString_shouldNotBeNull() {
        // Arrange
        AddressDetailVm vm = AddressDetailVm.builder()
            .id(1L)
            .contactName("Test User")
            .build();

        // Act
        String result = vm.toString();

        // Assert
        assertNotNull(result);
    }
}
