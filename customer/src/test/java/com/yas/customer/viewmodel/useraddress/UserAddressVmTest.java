package com.yas.customer.viewmodel.useraddress;

import com.yas.customer.model.UserAddress;
import com.yas.customer.viewmodel.address.AddressVm;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserAddressVmTest {

    @Test
    void testUserAddressVm_builder_shouldCreateValidObject() {
        // Arrange
        AddressVm addressVm = AddressVm.builder()
            .id(100L)
            .contactName("John Doe")
            .phone("123456789")
            .addressLine1("123 Main St")
            .city("City")
            .zipCode("12345")
            .districtId(10L)
            .stateOrProvinceId(20L)
            .countryId(30L)
            .build();

        // Act
        UserAddressVm vm = UserAddressVm.builder()
            .id(1L)
            .userId("user123")
            .addressGetVm(addressVm)
            .isActive(true)
            .build();

        // Assert
        assertNotNull(vm);
        assertEquals(1L, vm.id());
        assertEquals("user123", vm.userId());
        assertEquals(addressVm, vm.addressGetVm());
        assertTrue(vm.isActive());
    }

    @Test
    void testUserAddressVm_fromModel_shouldConvertCorrectly() {
        // Arrange
        UserAddress userAddress = UserAddress.builder()
            .id(1L)
            .userId("user456")
            .addressId(200L)
            .isActive(false)
            .build();

        AddressVm addressVm = AddressVm.builder()
            .id(200L)
            .contactName("Jane Smith")
            .phone("987654321")
            .addressLine1("456 Oak Ave")
            .city("Town")
            .zipCode("54321")
            .districtId(15L)
            .stateOrProvinceId(25L)
            .countryId(35L)
            .build();

        // Act
        UserAddressVm vm = UserAddressVm.fromModel(userAddress, addressVm);

        // Assert
        assertNotNull(vm);
        assertEquals(1L, vm.id());
        assertEquals("user456", vm.userId());
        assertEquals(addressVm, vm.addressGetVm());
        assertFalse(vm.isActive());
    }

    @Test
    void testUserAddressVm_withNullAddressVm_shouldHandleNull() {
        // Act
        UserAddressVm vm = UserAddressVm.builder()
            .id(1L)
            .userId("user")
            .addressGetVm(null)
            .isActive(true)
            .build();

        // Assert
        assertNotNull(vm);
        assertNull(vm.addressGetVm());
    }

    @Test
    void testUserAddressVm_equals_shouldWorkCorrectly() {
        // Arrange
        AddressVm address = AddressVm.builder().id(100L).build();
        
        UserAddressVm vm1 = UserAddressVm.builder()
            .id(1L)
            .userId("user1")
            .addressGetVm(address)
            .isActive(true)
            .build();

        UserAddressVm vm2 = UserAddressVm.builder()
            .id(1L)
            .userId("user1")
            .addressGetVm(address)
            .isActive(true)
            .build();

        UserAddressVm vm3 = UserAddressVm.builder()
            .id(2L)
            .userId("user2")
            .addressGetVm(null)
            .isActive(false)
            .build();

        // Act & Assert
        assertEquals(vm1, vm2);
        assertNotEquals(vm1, vm3);
    }

    @Test
    void testUserAddressVm_hashCode_shouldBeConsistent() {
        // Arrange
        AddressVm address = AddressVm.builder().id(100L).build();
        
        UserAddressVm vm1 = UserAddressVm.builder()
            .id(1L)
            .userId("user")
            .addressGetVm(address)
            .isActive(true)
            .build();

        UserAddressVm vm2 = UserAddressVm.builder()
            .id(1L)
            .userId("user")
            .addressGetVm(address)
            .isActive(true)
            .build();

        // Act & Assert
        assertEquals(vm1.hashCode(), vm2.hashCode());
    }

    @Test
    void testUserAddressVm_toString_shouldNotBeNull() {
        // Arrange
        UserAddressVm vm = UserAddressVm.builder()
            .id(1L)
            .userId("testUser")
            .isActive(true)
            .build();

        // Act
        String result = vm.toString();

        // Assert
        assertNotNull(result);
    }
}
