package com.yas.inventory.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class WarehouseTest {

    @Test
    void testWarehouseBuilder_whenAllFieldsProvided_shouldCreateWarehouse() {
        // Act
        Warehouse warehouse = Warehouse.builder()
            .id(1L)
            .name("Main Warehouse")
            .addressId(100L)
            .build();

        // Assert
        assertNotNull(warehouse);
        assertEquals(1L, warehouse.getId());
        assertEquals("Main Warehouse", warehouse.getName());
        assertEquals(100L, warehouse.getAddressId());
    }

    @Test
    void testWarehouseNoArgsConstructor_whenCreated_shouldHaveNullFields() {
        // Act
        Warehouse warehouse = new Warehouse();

        // Assert
        assertNotNull(warehouse);
        assertNull(warehouse.getId());
        assertNull(warehouse.getName());
        assertNull(warehouse.getAddressId());
    }

    @Test
    void testWarehouseSetters_whenCalled_shouldSetValues() {
        // Arrange
        Warehouse warehouse = new Warehouse();

        // Act
        warehouse.setId(1L);
        warehouse.setName("Main Warehouse");
        warehouse.setAddressId(100L);

        // Assert
        assertEquals(1L, warehouse.getId());
        assertEquals("Main Warehouse", warehouse.getName());
        assertEquals(100L, warehouse.getAddressId());
    }

    @Test
    void testWarehouseGetters_whenValueSet_shouldReturnCorrectValue() {
        // Arrange
        Warehouse warehouse = Warehouse.builder()
            .id(1L)
            .name("Main Warehouse")
            .addressId(100L)
            .build();

        // Act & Assert
        assertEquals(1L, warehouse.getId());
        assertEquals("Main Warehouse", warehouse.getName());
        assertEquals(100L, warehouse.getAddressId());
    }

    @Test
    void testWarehouseBuilder_whenPartialFieldsProvided_shouldCreateWarehouse() {
        // Act
        Warehouse warehouse = Warehouse.builder()
            .name("Main Warehouse")
            .build();

        // Assert
        assertNotNull(warehouse);
        assertNull(warehouse.getId());
        assertEquals("Main Warehouse", warehouse.getName());
        assertNull(warehouse.getAddressId());
    }

    @Test
    void testWarehouseName_whenUpdated_shouldReflectNewValue() {
        // Arrange
        Warehouse warehouse = Warehouse.builder()
            .id(1L)
            .name("Main Warehouse")
            .addressId(100L)
            .build();

        // Act
        warehouse.setName("Updated Warehouse");

        // Assert
        assertEquals("Updated Warehouse", warehouse.getName());
    }

    @Test
    void testWarehouseAddressId_whenUpdated_shouldReflectNewValue() {
        // Arrange
        Warehouse warehouse = Warehouse.builder()
            .id(1L)
            .name("Main Warehouse")
            .addressId(100L)
            .build();

        // Act
        warehouse.setAddressId(200L);

        // Assert
        assertEquals(200L, warehouse.getAddressId());
    }

    @Test
    void testWarehouseId_whenUpdated_shouldReflectNewValue() {
        // Arrange
        Warehouse warehouse = Warehouse.builder()
            .id(1L)
            .name("Main Warehouse")
            .addressId(100L)
            .build();

        // Act
        warehouse.setId(2L);

        // Assert
        assertEquals(2L, warehouse.getId());
    }

    @Test
    void testWarehouseBuilder_whenNameIsEmpty_shouldAcceptEmptyString() {
        // Act
        Warehouse warehouse = Warehouse.builder()
            .id(1L)
            .name("")
            .addressId(100L)
            .build();

        // Assert
        assertNotNull(warehouse);
        assertEquals("", warehouse.getName());
    }

    @Test
    void testWarehouseBuilder_whenAddressIdIsNull_shouldAcceptNull() {
        // Act
        Warehouse warehouse = Warehouse.builder()
            .id(1L)
            .name("Main Warehouse")
            .addressId(null)
            .build();

        // Assert
        assertNotNull(warehouse);
        assertNull(warehouse.getAddressId());
    }
}
