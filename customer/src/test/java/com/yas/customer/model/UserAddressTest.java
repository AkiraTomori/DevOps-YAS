package com.yas.customer.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserAddressTest {

    @Test
    void testUserAddress_builder_shouldCreateValidObject() {
        // Act
        UserAddress userAddress = UserAddress.builder()
            .id(1L)
            .userId("user123")
            .addressId(100L)
            .isActive(true)
            .build();

        // Assert
        assertNotNull(userAddress);
        assertEquals(1L, userAddress.getId());
        assertEquals("user123", userAddress.getUserId());
        assertEquals(100L, userAddress.getAddressId());
        assertTrue(userAddress.getIsActive());
    }

    @Test
    void testUserAddress_noArgsConstructor_shouldCreateEmptyObject() {
        // Act
        UserAddress userAddress = new UserAddress();

        // Assert
        assertNotNull(userAddress);
        assertNull(userAddress.getId());
        assertNull(userAddress.getUserId());
        assertNull(userAddress.getAddressId());
        assertNull(userAddress.getIsActive());
    }

    @Test
    void testUserAddress_allArgsConstructor_shouldCreateValidObject() {
        // Act
        UserAddress userAddress = new UserAddress(1L, "user456", 200L, false);

        // Assert
        assertNotNull(userAddress);
        assertEquals(1L, userAddress.getId());
        assertEquals("user456", userAddress.getUserId());
        assertEquals(200L, userAddress.getAddressId());
        assertFalse(userAddress.getIsActive());
    }

    @Test
    void testUserAddress_setters_shouldUpdateFields() {
        // Arrange
        UserAddress userAddress = new UserAddress();

        // Act
        userAddress.setId(5L);
        userAddress.setUserId("newUser");
        userAddress.setAddressId(500L);
        userAddress.setIsActive(true);

        // Assert
        assertEquals(5L, userAddress.getId());
        assertEquals("newUser", userAddress.getUserId());
        assertEquals(500L, userAddress.getAddressId());
        assertTrue(userAddress.getIsActive());
    }

    @Test
    void testUserAddress_getters_shouldReturnCorrectValues() {
        // Arrange
        UserAddress userAddress = UserAddress.builder()
            .id(10L)
            .userId("testUser")
            .addressId(1000L)
            .isActive(false)
            .build();

        // Act & Assert
        assertEquals(10L, userAddress.getId());
        assertEquals("testUser", userAddress.getUserId());
        assertEquals(1000L, userAddress.getAddressId());
        assertFalse(userAddress.getIsActive());
    }

    @Test
    void testUserAddress_builderWithPartialFields_shouldHandleNulls() {
        // Act
        UserAddress userAddress = UserAddress.builder()
            .userId("partialUser")
            .addressId(300L)
            .build();

        // Assert
        assertNotNull(userAddress);
        assertNull(userAddress.getId());
        assertEquals("partialUser", userAddress.getUserId());
        assertEquals(300L, userAddress.getAddressId());
        assertNull(userAddress.getIsActive());
    }

    @Test
    void testUserAddress_equalsAndHashCode_shouldWorkCorrectly() {
        // Arrange
        UserAddress address1 = UserAddress.builder()
            .id(1L)
            .userId("user1")
            .addressId(100L)
            .isActive(true)
            .build();

        UserAddress address2 = UserAddress.builder()
            .id(1L)
            .userId("user1")
            .addressId(100L)
            .isActive(true)
            .build();

        UserAddress address3 = UserAddress.builder()
            .id(2L)
            .userId("user2")
            .addressId(200L)
            .isActive(false)
            .build();

        // Act & Assert
        assertEquals(address1, address2);
        assertNotEquals(address1, address3);
        assertEquals(address1.hashCode(), address2.hashCode());
    }

    @Test
    void testUserAddress_toString_shouldNotBeNull() {
        // Arrange
        UserAddress userAddress = UserAddress.builder()
            .id(1L)
            .userId("user1")
            .addressId(100L)
            .isActive(true)
            .build();

        // Act
        String result = userAddress.toString();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("UserAddress"));
    }
}
