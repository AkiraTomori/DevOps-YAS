package com.yas.inventory.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class StockHistoryTest {

    @Test
    void testStockHistoryBuilder_whenAllFieldsProvided_shouldCreateStockHistory() {
        // Arrange
        Warehouse warehouse = Warehouse.builder()
            .id(1L)
            .name("Main Warehouse")
            .addressId(100L)
            .build();

        // Act
        StockHistory stockHistory = StockHistory.builder()
            .id(1L)
            .productId(10L)
            .adjustedQuantity(50L)
            .note("Restock")
            .warehouse(warehouse)
            .build();

        // Assert
        assertNotNull(stockHistory);
        assertEquals(1L, stockHistory.getId());
        assertEquals(10L, stockHistory.getProductId());
        assertEquals(50L, stockHistory.getAdjustedQuantity());
        assertEquals("Restock", stockHistory.getNote());
        assertEquals(warehouse, stockHistory.getWarehouse());
    }

    @Test
    void testStockHistoryNoArgsConstructor_whenCreated_shouldHaveNullFields() {
        // Act
        StockHistory stockHistory = new StockHistory();

        // Assert
        assertNotNull(stockHistory);
        assertNull(stockHistory.getId());
        assertNull(stockHistory.getProductId());
        assertNull(stockHistory.getAdjustedQuantity());
        assertNull(stockHistory.getNote());
        assertNull(stockHistory.getWarehouse());
    }

    @Test
    void testStockHistorySetters_whenCalled_shouldSetValues() {
        // Arrange
        StockHistory stockHistory = new StockHistory();
        Warehouse warehouse = Warehouse.builder()
            .id(1L)
            .name("Main Warehouse")
            .addressId(100L)
            .build();

        // Act
        stockHistory.setId(1L);
        stockHistory.setProductId(10L);
        stockHistory.setAdjustedQuantity(50L);
        stockHistory.setNote("Restock");
        stockHistory.setWarehouse(warehouse);

        // Assert
        assertEquals(1L, stockHistory.getId());
        assertEquals(10L, stockHistory.getProductId());
        assertEquals(50L, stockHistory.getAdjustedQuantity());
        assertEquals("Restock", stockHistory.getNote());
        assertEquals(warehouse, stockHistory.getWarehouse());
    }

    @Test
    void testStockHistoryGetters_whenValueSet_shouldReturnCorrectValue() {
        // Arrange
        Warehouse warehouse = Warehouse.builder()
            .id(1L)
            .name("Main Warehouse")
            .addressId(100L)
            .build();

        StockHistory stockHistory = StockHistory.builder()
            .id(1L)
            .productId(10L)
            .adjustedQuantity(50L)
            .note("Restock")
            .warehouse(warehouse)
            .build();

        // Act & Assert
        assertEquals(1L, stockHistory.getId());
        assertEquals(10L, stockHistory.getProductId());
        assertEquals(50L, stockHistory.getAdjustedQuantity());
        assertEquals("Restock", stockHistory.getNote());
        assertEquals(warehouse, stockHistory.getWarehouse());
    }

    @Test
    void testStockHistoryBuilder_whenPartialFieldsProvided_shouldCreateStockHistory() {
        // Arrange
        Warehouse warehouse = Warehouse.builder()
            .id(1L)
            .name("Main Warehouse")
            .addressId(100L)
            .build();

        // Act
        StockHistory stockHistory = StockHistory.builder()
            .productId(10L)
            .adjustedQuantity(50L)
            .warehouse(warehouse)
            .build();

        // Assert
        assertNotNull(stockHistory);
        assertNull(stockHistory.getId());
        assertEquals(10L, stockHistory.getProductId());
        assertEquals(50L, stockHistory.getAdjustedQuantity());
        assertNull(stockHistory.getNote());
        assertEquals(warehouse, stockHistory.getWarehouse());
    }

    @Test
    void testStockHistoryAdjustedQuantity_whenNegative_shouldAcceptNegativeValue() {
        // Arrange
        Warehouse warehouse = Warehouse.builder()
            .id(1L)
            .name("Main Warehouse")
            .addressId(100L)
            .build();

        // Act
        StockHistory stockHistory = StockHistory.builder()
            .id(1L)
            .productId(10L)
            .adjustedQuantity(-25L)
            .note("Sale")
            .warehouse(warehouse)
            .build();

        // Assert
        assertEquals(-25L, stockHistory.getAdjustedQuantity());
        assertEquals("Sale", stockHistory.getNote());
    }

    @Test
    void testStockHistoryNote_whenUpdated_shouldReflectNewValue() {
        // Arrange
        Warehouse warehouse = Warehouse.builder()
            .id(1L)
            .name("Main Warehouse")
            .addressId(100L)
            .build();

        StockHistory stockHistory = StockHistory.builder()
            .id(1L)
            .productId(10L)
            .adjustedQuantity(50L)
            .note("Restock")
            .warehouse(warehouse)
            .build();

        // Act
        stockHistory.setNote("Updated Note");

        // Assert
        assertEquals("Updated Note", stockHistory.getNote());
    }

    @Test
    void testStockHistoryAdjustedQuantity_whenUpdated_shouldReflectNewValue() {
        // Arrange
        Warehouse warehouse = Warehouse.builder()
            .id(1L)
            .name("Main Warehouse")
            .addressId(100L)
            .build();

        StockHistory stockHistory = StockHistory.builder()
            .id(1L)
            .productId(10L)
            .adjustedQuantity(50L)
            .note("Restock")
            .warehouse(warehouse)
            .build();

        // Act
        stockHistory.setAdjustedQuantity(75L);

        // Assert
        assertEquals(75L, stockHistory.getAdjustedQuantity());
    }

    @Test
    void testStockHistoryWarehouse_whenUpdated_shouldReflectNewValue() {
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

        StockHistory stockHistory = StockHistory.builder()
            .id(1L)
            .productId(10L)
            .adjustedQuantity(50L)
            .note("Restock")
            .warehouse(warehouse1)
            .build();

        // Act
        stockHistory.setWarehouse(warehouse2);

        // Assert
        assertEquals(warehouse2, stockHistory.getWarehouse());
        assertEquals("Secondary Warehouse", stockHistory.getWarehouse().getName());
    }

    @Test
    void testStockHistoryNote_whenNull_shouldAcceptNull() {
        // Arrange
        Warehouse warehouse = Warehouse.builder()
            .id(1L)
            .name("Main Warehouse")
            .addressId(100L)
            .build();

        // Act
        StockHistory stockHistory = StockHistory.builder()
            .id(1L)
            .productId(10L)
            .adjustedQuantity(50L)
            .note(null)
            .warehouse(warehouse)
            .build();

        // Assert
        assertNull(stockHistory.getNote());
    }
}
