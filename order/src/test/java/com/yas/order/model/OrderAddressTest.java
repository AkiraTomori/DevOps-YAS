package com.yas.order.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderAddressTest {

    @Test
    void testOrderAddress_builder() {
        // Arrange & Act
        OrderAddress address = OrderAddress.builder()
                .id(1L)
                .contactName("John Doe")
                .phone("123-456-7890")
                .addressLine1("123 Main St")
                .addressLine2("Apt 4B")
                .city("New York")
                .zipCode("10001")
                .districtId(100L)
                .districtName("Manhattan")
                .stateOrProvinceId(200L)
                .stateOrProvinceName("New York")
                .countryId(1L)
                .countryName("USA")
                .build();

        // Assert
        assertNotNull(address);
        assertEquals(1L, address.getId());
        assertEquals("John Doe", address.getContactName());
        assertEquals("123-456-7890", address.getPhone());
        assertEquals("123 Main St", address.getAddressLine1());
        assertEquals("Apt 4B", address.getAddressLine2());
        assertEquals("New York", address.getCity());
        assertEquals("10001", address.getZipCode());
        assertEquals(100L, address.getDistrictId());
        assertEquals("Manhattan", address.getDistrictName());
        assertEquals(200L, address.getStateOrProvinceId());
        assertEquals("New York", address.getStateOrProvinceName());
        assertEquals(1L, address.getCountryId());
        assertEquals("USA", address.getCountryName());
    }

    @Test
    void testOrderAddress_gettersAndSetters() {
        // Arrange
        OrderAddress address = new OrderAddress();

        // Act
        address.setId(2L);
        address.setContactName("Jane Smith");
        address.setPhone("987-654-3210");
        address.setAddressLine1("456 Oak Ave");
        address.setAddressLine2("Suite 200");
        address.setCity("Los Angeles");
        address.setZipCode("90001");
        address.setDistrictId(101L);
        address.setDistrictName("Downtown");
        address.setStateOrProvinceId(201L);
        address.setStateOrProvinceName("California");
        address.setCountryId(2L);
        address.setCountryName("USA");

        // Assert
        assertEquals(2L, address.getId());
        assertEquals("Jane Smith", address.getContactName());
        assertEquals("987-654-3210", address.getPhone());
        assertEquals("456 Oak Ave", address.getAddressLine1());
        assertEquals("Suite 200", address.getAddressLine2());
        assertEquals("Los Angeles", address.getCity());
        assertEquals("90001", address.getZipCode());
        assertEquals(101L, address.getDistrictId());
        assertEquals("Downtown", address.getDistrictName());
        assertEquals(201L, address.getStateOrProvinceId());
        assertEquals("California", address.getStateOrProvinceName());
        assertEquals(2L, address.getCountryId());
        assertEquals("USA", address.getCountryName());
    }

    @Test
    void testOrderAddress_noArgsConstructor() {
        // Act
        OrderAddress address = new OrderAddress();

        // Assert
        assertNotNull(address);
        assertNull(address.getId());
        assertNull(address.getContactName());
    }

    @Test
    void testOrderAddress_allArgsConstructor() {
        // Act
        OrderAddress address = new OrderAddress(
                1L,
                "John Doe",
                "123-456-7890",
                "123 Main St",
                "Apt 4B",
                "New York",
                "10001",
                100L,
                "Manhattan",
                200L,
                "New York State",
                1L,
                "USA"
        );

        // Assert
        assertNotNull(address);
        assertEquals(1L, address.getId());
        assertEquals("John Doe", address.getContactName());
        assertEquals("123 Main St", address.getAddressLine1());
    }

    @Test
    void testOrderAddress_setContactName() {
        // Arrange
        OrderAddress address = new OrderAddress();
        String name = "Alice Johnson";

        // Act
        address.setContactName(name);

        // Assert
        assertEquals(name, address.getContactName());
    }

    @Test
    void testOrderAddress_setPhone() {
        // Arrange
        OrderAddress address = new OrderAddress();
        String phone = "555-1234";

        // Act
        address.setPhone(phone);

        // Assert
        assertEquals(phone, address.getPhone());
    }

    @Test
    void testOrderAddress_setCity() {
        // Arrange
        OrderAddress address = new OrderAddress();
        String city = "San Francisco";

        // Act
        address.setCity(city);

        // Assert
        assertEquals(city, address.getCity());
    }

    @Test
    void testOrderAddress_setZipCode() {
        // Arrange
        OrderAddress address = new OrderAddress();
        String zipCode = "94102";

        // Act
        address.setZipCode(zipCode);

        // Assert
        assertEquals(zipCode, address.getZipCode());
    }

    @Test
    void testOrderAddress_setCountryName() {
        // Arrange
        OrderAddress address = new OrderAddress();
        String country = "Canada";

        // Act
        address.setCountryName(country);

        // Assert
        assertEquals(country, address.getCountryName());
    }
}
