package com.yas.inventory.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class StockTest {

    @Test
    void testStockBuilder_whenAllFieldsProvided_shouldCreateStock() {
        // Arrange
        Warehouse warehouse = Warehouse.builder()
            .id(1L)
            .name("Main Warehouse")
            .addressId(100L)
            .build();

        // Act
        Stock stock = Stock.builder()
            .id(1L)
            .productId(10L)
            .quantity(100L)
            .reservedQuantity(10L)
            .warehouse(warehouse)
            .build();

        // Assert
        assertNotNull(stock);
        assertEquals(1L, stock.getId());
        assertEquals(10L, stock.getProductId());
        assertEquals(100L, stock.getQuantity());
        assertEquals(10L, stock.getReservedQuantity());
        assertEquals(warehouse, stock.getWarehouse());
    }

    @Test
    void testStockNoArgsConstructor_whenCreated_shouldHaveNullFields() {
        // Act
        Stock stock = new Stock();

        // Assert
        assertNotNull(stock);
        assertNull(stock.getId());
        assertNull(stock.getProductId());
        assertNull(stock.getQuantity());
        assertNull(stock.getReservedQuantity());
        assertNull(stock.getWarehouse());
    }

    @Test
    void testStockSetters_whenCalled_shouldSetValues() {
        // Arrange
        Stock stock = new Stock();
        Warehouse warehouse = Warehouse.builder()
            .id(1L)
            .name("Main Warehouse")
            .addressId(100L)
            .build();

        // Act
        stock.setId(1L);
        stock.setProductId(10L);
        stock.setQuantity(100L);
        stock.setReservedQuantity(10L);
        stock.setWarehouse(warehouse);

        // Assert
        assertEquals(1L, stock.getId());
        assertEquals(10L, stock.getProductId());
        assertEquals(100L, stock.getQuantity());
        assertEquals(10L, stock.getReservedQuantity());
        assertEquals(warehouse, stock.getWarehouse());
    }

    @Test
    void testStockGetters_whenValueSet_shouldReturnCorrectValue() {
        // Arrange
        Warehouse warehouse = Warehouse.builder()
            .id(1L)
            .name("Main Warehouse")
            .addressId(100L)
            .build();

        Stock stock = Stock.builder()
            .id(1L)
            .productId(10L)
            .quantity(100L)
            .reservedQuantity(10L)
            .warehouse(warehouse)
            .build();

        // Act & Assert
        assertEquals(1L, stock.getId());
        assertEquals(10L, stock.getProductId());
        assertEquals(100L, stock.getQuantity());
        assertEquals(10L, stock.getReservedQuantity());
        assertEquals(warehouse, stock.getWarehouse());
    }

    @Test
    void testStockBuilder_whenPartialFieldsProvided_shouldCreateStock() {
        // Arrange
        Warehouse warehouse = Warehouse.builder()
            .id(1L)
            .name("Main Warehouse")
            .addressId(100L)
            .build();

        // Act
        Stock stock = Stock.builder()
            .productId(10L)
            .warehouse(warehouse)
            .build();

        // Assert
        assertNotNull(stock);
        assertNull(stock.getId());
        assertEquals(10L, stock.getProductId());
        assertNull(stock.getQuantity());
        assertNull(stock.getReservedQuantity());
        assertEquals(warehouse, stock.getWarehouse());
    }

    @Test
    void testStockQuantity_whenUpdated_shouldReflectNewValue() {
        // Arrange
        Warehouse warehouse = Warehouse.builder()
            .id(1L)
            .name("Main Warehouse")
            .addressId(100L)
            .build();

        Stock stock = Stock.builder()
            .id(1L)
            .productId(10L)
            .quantity(100L)
            .reservedQuantity(10L)
            .warehouse(warehouse)
            .build();

        // Act
        stock.setQuantity(150L);

        // Assert
        assertEquals(150L, stock.getQuantity());
    }

    @Test
    void testStockReservedQuantity_whenUpdated_shouldReflectNewValue() {
        // Arrange
        Warehouse warehouse = Warehouse.builder()
            .id(1L)
            .name("Main Warehouse")
            .addressId(100L)
            .build();

        Stock stock = Stock.builder()
            .id(1L)
            .productId(10L)
            .quantity(100L)
            .reservedQuantity(10L)
            .warehouse(warehouse)
            .build();

        // Act
        stock.setReservedQuantity(20L);

        // Assert
        assertEquals(20L, stock.getReservedQuantity());
    }

    @Test
    void testStockWarehouse_whenUpdated_shouldReflectNewValue() {
        // Arrange
        Warehouse warehouse1 = Warehouse.builder()
            .id(1L)
            .name("Main Warehouse")
            .addressId(100L)
            .build();

        Warehouse warehouse2 = Warehouse.builder()
            .id(2L)
            .name("Secondary Warehouse")
            .addressId(200L)
            .build();

        Stock stock = Stock.builder()
            .id(1L)
            .productId(10L)
            .quantity(100L)
            .reservedQuantity(10L)
            .warehouse(warehouse1)
            .build();

        // Act
        stock.setWarehouse(warehouse2);

        // Assert
        assertEquals(warehouse2, stock.getWarehouse());
        assertEquals("Secondary Warehouse", stock.getWarehouse().getName());
    }

    @Test
    void testStockProductId_whenUpdated_shouldReflectNewValue() {
        // Arrange
        Warehouse warehouse = Warehouse.builder()
            .id(1L)
            .name("Main Warehouse")
            .addressId(100L)
            .build();

        Stock stock = Stock.builder()
            .id(1L)
            .productId(10L)
            .quantity(100L)
            .reservedQuantity(10L)
            .warehouse(warehouse)
            .build();

        // Act
        stock.setProductId(20L);

        // Assert
        assertEquals(20L, stock.getProductId());
    }
}
