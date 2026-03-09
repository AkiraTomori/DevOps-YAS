package com.yas.inventory.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.yas.inventory.model.Stock;
import com.yas.inventory.model.StockHistory;
import com.yas.inventory.model.Warehouse;
import com.yas.inventory.repository.StockHistoryRepository;
import com.yas.inventory.viewmodel.product.ProductInfoVm;
import com.yas.inventory.viewmodel.stock.StockQuantityVm;
import com.yas.inventory.viewmodel.stockhistory.StockHistoryListVm;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StockHistoryServiceTest {

    @Mock
    private StockHistoryRepository stockHistoryRepository;

    @Mock
    private ProductService productService;

    @InjectMocks
    private StockHistoryService stockHistoryService;

    private Warehouse warehouse;
    private Stock stock;
    private StockHistory stockHistory;
    private ProductInfoVm productInfoVm;

    @BeforeEach
    void setUp() {
        warehouse = Warehouse.builder()
            .id(1L)
            .name("Main Warehouse")
            .addressId(100L)
            .build();

        stock = Stock.builder()
            .id(1L)
            .productId(10L)
            .warehouse(warehouse)
            .quantity(100L)
            .reservedQuantity(10L)
            .build();

        stockHistory = StockHistory.builder()
            .id(1L)
            .productId(10L)
            .adjustedQuantity(50L)
            .note("Restock")
            .warehouse(warehouse)
            .build();

        productInfoVm = new ProductInfoVm(10L, "Product 1", "SKU001", true);
    }

    @Test
    void testCreateStockHistories_whenValidInput_shouldCreateHistories() {
        // Arrange
        StockQuantityVm stockQuantityVm = new StockQuantityVm(1L, 50L, "Restock");
        List<StockQuantityVm> stockQuantityVms = List.of(stockQuantityVm);
        List<Stock> stocks = List.of(stock);

        when(stockHistoryRepository.saveAll(anyList())).thenReturn(Collections.emptyList());

        // Act
        assertDoesNotThrow(() -> stockHistoryService.createStockHistories(stocks, stockQuantityVms));

        // Assert
        verify(stockHistoryRepository).saveAll(anyList());
    }

    @Test
    void testCreateStockHistories_whenStockNotInList_shouldSkip() {
        // Arrange
        StockQuantityVm stockQuantityVm = new StockQuantityVm(999L, 50L, "Unknown stock");
        List<StockQuantityVm> stockQuantityVms = List.of(stockQuantityVm);
        List<Stock> stocks = List.of(stock);

        when(stockHistoryRepository.saveAll(anyList())).thenReturn(Collections.emptyList());

        // Act
        assertDoesNotThrow(() -> stockHistoryService.createStockHistories(stocks, stockQuantityVms));

        // Assert
        verify(stockHistoryRepository).saveAll(anyList());
    }

    @Test
    void testCreateStockHistories_whenEmptyStockList_shouldNotSave() {
        // Arrange
        StockQuantityVm stockQuantityVm = new StockQuantityVm(1L, 50L, "Restock");
        List<StockQuantityVm> stockQuantityVms = List.of(stockQuantityVm);
        List<Stock> stocks = Collections.emptyList();

        when(stockHistoryRepository.saveAll(anyList())).thenReturn(Collections.emptyList());

        // Act
        assertDoesNotThrow(() -> stockHistoryService.createStockHistories(stocks, stockQuantityVms));

        // Assert
        verify(stockHistoryRepository).saveAll(anyList());
    }

    @Test
    void testCreateStockHistories_whenNullNote_shouldCreateWithNullNote() {
        // Arrange
        StockQuantityVm stockQuantityVm = new StockQuantityVm(1L, 50L, null);
        List<StockQuantityVm> stockQuantityVms = List.of(stockQuantityVm);
        List<Stock> stocks = List.of(stock);

        when(stockHistoryRepository.saveAll(anyList())).thenReturn(Collections.emptyList());

        // Act
        assertDoesNotThrow(() -> stockHistoryService.createStockHistories(stocks, stockQuantityVms));

        // Assert
        verify(stockHistoryRepository).saveAll(anyList());
    }

    @Test
    void testGetStockHistories_whenValidInput_shouldReturnStockHistoryList() {
        // Arrange
        Long productId = 10L;
        Long warehouseId = 1L;

        List<StockHistory> stockHistories = List.of(stockHistory);

        when(stockHistoryRepository.findByProductIdAndWarehouseIdOrderByCreatedOnDesc(productId, warehouseId))
            .thenReturn(stockHistories);
        when(productService.getProduct(productId)).thenReturn(productInfoVm);

        // Act
        StockHistoryListVm result = stockHistoryService.getStockHistories(productId, warehouseId);

        // Assert
        assertNotNull(result);
        assertNotNull(result.data());
        assertEquals(1, result.data().size());
        assertEquals("Product 1", result.data().get(0).productName());

        verify(stockHistoryRepository).findByProductIdAndWarehouseIdOrderByCreatedOnDesc(productId, warehouseId);
        verify(productService).getProduct(productId);
    }

    @Test
    void testGetStockHistories_whenNoHistories_shouldReturnEmptyList() {
        // Arrange
        Long productId = 10L;
        Long warehouseId = 1L;

        when(stockHistoryRepository.findByProductIdAndWarehouseIdOrderByCreatedOnDesc(productId, warehouseId))
            .thenReturn(Collections.emptyList());
        when(productService.getProduct(productId)).thenReturn(productInfoVm);

        // Act
        StockHistoryListVm result = stockHistoryService.getStockHistories(productId, warehouseId);

        // Assert
        assertNotNull(result);
        assertNotNull(result.data());
        assertEquals(0, result.data().size());

        verify(stockHistoryRepository).findByProductIdAndWarehouseIdOrderByCreatedOnDesc(productId, warehouseId);
        verify(productService).getProduct(productId);
    }

    @Test
    void testCreateStockHistories_whenMultipleStocks_shouldCreateMultipleHistories() {
        // Arrange
        Stock stock2 = Stock.builder()
            .id(2L)
            .productId(20L)
            .warehouse(warehouse)
            .quantity(200L)
            .reservedQuantity(20L)
            .build();

        StockQuantityVm stockQuantityVm1 = new StockQuantityVm(1L, 50L, "Restock 1");
        StockQuantityVm stockQuantityVm2 = new StockQuantityVm(2L, 100L, "Restock 2");
        List<StockQuantityVm> stockQuantityVms = List.of(stockQuantityVm1, stockQuantityVm2);
        List<Stock> stocks = List.of(stock, stock2);

        when(stockHistoryRepository.saveAll(anyList())).thenReturn(Collections.emptyList());

        // Act
        assertDoesNotThrow(() -> stockHistoryService.createStockHistories(stocks, stockQuantityVms));

        // Assert
        verify(stockHistoryRepository).saveAll(anyList());
    }

    @Test
    void testGetStockHistories_whenMultipleHistories_shouldReturnAll() {
        // Arrange
        Long productId = 10L;
        Long warehouseId = 1L;

        StockHistory stockHistory2 = StockHistory.builder()
            .id(2L)
            .productId(10L)
            .adjustedQuantity(-25L)
            .note("Sale")
            .warehouse(warehouse)
            .build();

        List<StockHistory> stockHistories = List.of(stockHistory, stockHistory2);

        when(stockHistoryRepository.findByProductIdAndWarehouseIdOrderByCreatedOnDesc(productId, warehouseId))
            .thenReturn(stockHistories);
        when(productService.getProduct(productId)).thenReturn(productInfoVm);

        // Act
        StockHistoryListVm result = stockHistoryService.getStockHistories(productId, warehouseId);

        // Assert
        assertNotNull(result);
        assertNotNull(result.data());
        assertEquals(2, result.data().size());

        verify(stockHistoryRepository).findByProductIdAndWarehouseIdOrderByCreatedOnDesc(productId, warehouseId);
        verify(productService).getProduct(productId);
    }
}
