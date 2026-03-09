package com.yas.inventory.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.yas.commonlibrary.exception.DuplicatedException;
import com.yas.commonlibrary.exception.NotFoundException;
import com.yas.inventory.model.Warehouse;
import com.yas.inventory.model.enumeration.FilterExistInWhSelection;
import com.yas.inventory.repository.StockRepository;
import com.yas.inventory.repository.WarehouseRepository;
import com.yas.inventory.viewmodel.address.AddressDetailVm;
import com.yas.inventory.viewmodel.address.AddressPostVm;
import com.yas.inventory.viewmodel.address.AddressVm;
import com.yas.inventory.viewmodel.product.ProductInfoVm;
import com.yas.inventory.viewmodel.warehouse.WarehouseDetailVm;
import com.yas.inventory.viewmodel.warehouse.WarehouseGetVm;
import com.yas.inventory.viewmodel.warehouse.WarehouseListGetVm;
import com.yas.inventory.viewmodel.warehouse.WarehousePostVm;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class WarehouseServiceTest {

    @Mock
    private WarehouseRepository warehouseRepository;

    @Mock
    private StockRepository stockRepository;

    @Mock
    private ProductService productService;

    @Mock
    private LocationService locationService;

    @InjectMocks
    private WarehouseService warehouseService;

    private Warehouse warehouse;
    private WarehousePostVm warehousePostVm;
    private AddressVm addressVm;
    private AddressDetailVm addressDetailVm;

    @BeforeEach
    void setUp() {
        warehouse = Warehouse.builder()
            .id(1L)
            .name("Main Warehouse")
            .addressId(100L)
            .build();

        warehousePostVm = new WarehousePostVm(
            null,
            "Main Warehouse",
            "John Doe",
            "123456789",
            "123 Main St",
            "Apt 1",
            "City",
            "12345",
            1L,
            2L,
            3L
        );

        addressVm = AddressVm.builder()
            .id(100L)
            .contactName("John Doe")
            .phone("123456789")
            .addressLine1("123 Main St")
            .city("City")
            .zipCode("12345")
            .districtId(1L)
            .stateOrProvinceId(2L)
            .countryId(3L)
            .build();

        addressDetailVm = AddressDetailVm.builder()
            .id(100L)
            .contactName("John Doe")
            .phone("123456789")
            .addressLine1("123 Main St")
            .addressLine2("Apt 1")
            .city("City")
            .zipCode("12345")
            .districtId(1L)
            .districtName("District 1")
            .stateOrProvinceId(2L)
            .stateOrProvinceName("State 1")
            .countryId(3L)
            .countryName("Country 1")
            .build();
    }

    @Test
    void testFindAllWarehouses_whenWarehousesExist_shouldReturnList() {
        // Arrange
        List<Warehouse> warehouses = List.of(warehouse);
        when(warehouseRepository.findAll()).thenReturn(warehouses);

        // Act
        List<WarehouseGetVm> result = warehouseService.findAllWarehouses();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Main Warehouse", result.get(0).name());

        verify(warehouseRepository).findAll();
    }

    @Test
    void testFindAllWarehouses_whenNoWarehouses_shouldReturnEmptyList() {
        // Arrange
        when(warehouseRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<WarehouseGetVm> result = warehouseService.findAllWarehouses();

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());

        verify(warehouseRepository).findAll();
    }

    @Test
    void testGetProductWarehouse_whenProductsExistInWarehouse_shouldReturnProducts() {
        // Arrange
        Long warehouseId = 1L;
        String productName = "Product 1";
        String productSku = "SKU001";
        FilterExistInWhSelection selection = FilterExistInWhSelection.YES;

        List<Long> productIds = List.of(10L, 20L);
        ProductInfoVm productInfoVm1 = new ProductInfoVm(10L, "Product 1", "SKU001", true);
        ProductInfoVm productInfoVm2 = new ProductInfoVm(20L, "Product 2", "SKU002", false);
        List<ProductInfoVm> productVmList = List.of(productInfoVm1, productInfoVm2);

        when(stockRepository.getProductIdsInWarehouse(warehouseId)).thenReturn(productIds);
        when(productService.filterProducts(productName, productSku, productIds, selection))
            .thenReturn(productVmList);

        // Act
        List<ProductInfoVm> result = warehouseService.getProductWarehouse(
            warehouseId, productName, productSku, selection);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.get(0).existInWh());

        verify(stockRepository).getProductIdsInWarehouse(warehouseId);
        verify(productService).filterProducts(productName, productSku, productIds, selection);
    }

    @Test
    void testGetProductWarehouse_whenNoProductsInWarehouse_shouldReturnFilteredProducts() {
        // Arrange
        Long warehouseId = 1L;
        String productName = "Product 1";
        String productSku = "SKU001";
        FilterExistInWhSelection selection = FilterExistInWhSelection.NO;

        List<Long> productIds = Collections.emptyList();
        ProductInfoVm productInfoVm = new ProductInfoVm(10L, "Product 1", "SKU001", false);
        List<ProductInfoVm> productVmList = List.of(productInfoVm);

        when(stockRepository.getProductIdsInWarehouse(warehouseId)).thenReturn(productIds);
        when(productService.filterProducts(productName, productSku, productIds, selection))
            .thenReturn(productVmList);

        // Act
        List<ProductInfoVm> result = warehouseService.getProductWarehouse(
            warehouseId, productName, productSku, selection);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());

        verify(stockRepository).getProductIdsInWarehouse(warehouseId);
        verify(productService).filterProducts(productName, productSku, productIds, selection);
    }

    @Test
    void testFindById_whenWarehouseExists_shouldReturnWarehouseDetail() {
        // Arrange
        Long id = 1L;

        when(warehouseRepository.findById(id)).thenReturn(Optional.of(warehouse));
        when(locationService.getAddressById(100L)).thenReturn(addressDetailVm);

        // Act
        WarehouseDetailVm result = warehouseService.findById(id);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Main Warehouse", result.name());
        assertEquals("John Doe", result.contactName());

        verify(warehouseRepository).findById(id);
        verify(locationService).getAddressById(100L);
    }

    @Test
    void testFindById_whenWarehouseNotFound_shouldThrowNotFoundException() {
        // Arrange
        Long id = 999L;

        when(warehouseRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> warehouseService.findById(id));

        verify(warehouseRepository).findById(id);
        verify(locationService, times(0)).getAddressById(anyLong());
    }

    @Test
    void testCreate_whenValidInput_shouldCreateWarehouse() {
        // Arrange
        when(warehouseRepository.existsByName("Main Warehouse")).thenReturn(false);
        when(locationService.createAddress(any(AddressPostVm.class))).thenReturn(addressVm);
        when(warehouseRepository.save(any(Warehouse.class))).thenReturn(warehouse);

        // Act
        Warehouse result = warehouseService.create(warehousePostVm);

        // Assert
        assertNotNull(result);
        assertEquals("Main Warehouse", result.getName());
        assertEquals(100L, result.getAddressId());

        verify(warehouseRepository).existsByName("Main Warehouse");
        verify(locationService).createAddress(any(AddressPostVm.class));
        verify(warehouseRepository).save(any(Warehouse.class));
    }

    @Test
    void testCreate_whenNameAlreadyExists_shouldThrowDuplicatedException() {
        // Arrange
        when(warehouseRepository.existsByName("Main Warehouse")).thenReturn(true);

        // Act & Assert
        assertThrows(DuplicatedException.class, 
            () -> warehouseService.create(warehousePostVm));

        verify(warehouseRepository).existsByName("Main Warehouse");
        verify(locationService, times(0)).createAddress(any(AddressPostVm.class));
    }

    @Test
    void testUpdate_whenValidInput_shouldUpdateWarehouse() {
        // Arrange
        Long id = 1L;

        when(warehouseRepository.findById(id)).thenReturn(Optional.of(warehouse));
        when(warehouseRepository.existsByNameWithDifferentId("Main Warehouse", id)).thenReturn(false);
        when(warehouseRepository.save(any(Warehouse.class))).thenReturn(warehouse);

        // Act
        assertDoesNotThrow(() -> warehouseService.update(warehousePostVm, id));

        // Assert
        verify(warehouseRepository).findById(id);
        verify(warehouseRepository).existsByNameWithDifferentId("Main Warehouse", id);
        verify(locationService).updateAddress(anyLong(), any(AddressPostVm.class));
        verify(warehouseRepository).save(any(Warehouse.class));
    }

    @Test
    void testUpdate_whenWarehouseNotFound_shouldThrowNotFoundException() {
        // Arrange
        Long id = 999L;

        when(warehouseRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, 
            () -> warehouseService.update(warehousePostVm, id));

        verify(warehouseRepository).findById(id);
        verify(warehouseRepository, times(0)).existsByNameWithDifferentId(any(), anyLong());
    }

    @Test
    void testUpdate_whenNameExistsForDifferentWarehouse_shouldThrowDuplicatedException() {
        // Arrange
        Long id = 1L;

        when(warehouseRepository.findById(id)).thenReturn(Optional.of(warehouse));
        when(warehouseRepository.existsByNameWithDifferentId("Main Warehouse", id)).thenReturn(true);

        // Act & Assert
        assertThrows(DuplicatedException.class, 
            () -> warehouseService.update(warehousePostVm, id));

        verify(warehouseRepository).findById(id);
        verify(warehouseRepository).existsByNameWithDifferentId("Main Warehouse", id);
        verify(locationService, times(0)).updateAddress(anyLong(), any(AddressPostVm.class));
    }

    @Test
    void testDelete_whenWarehouseExists_shouldDeleteWarehouse() {
        // Arrange
        Long id = 1L;

        when(warehouseRepository.findById(id)).thenReturn(Optional.of(warehouse));

        // Act
        assertDoesNotThrow(() -> warehouseService.delete(id));

        // Assert
        verify(warehouseRepository).findById(id);
        verify(warehouseRepository).deleteById(id);
        verify(locationService).deleteAddress(100L);
    }

    @Test
    void testDelete_whenWarehouseNotFound_shouldThrowNotFoundException() {
        // Arrange
        Long id = 999L;

        when(warehouseRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> warehouseService.delete(id));

        verify(warehouseRepository).findById(id);
        verify(warehouseRepository, times(0)).deleteById(anyLong());
        verify(locationService, times(0)).deleteAddress(anyLong());
    }

    @Test
    void testGetPageableWarehouses_whenWarehousesExist_shouldReturnPagedList() {
        // Arrange
        int pageNo = 0;
        int pageSize = 10;
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        List<Warehouse> warehouses = List.of(warehouse);
        Page<Warehouse> warehousePage = new PageImpl<>(warehouses, pageable, 1);

        when(warehouseRepository.findAll(pageable)).thenReturn(warehousePage);

        // Act
        WarehouseListGetVm result = warehouseService.getPageableWarehouses(pageNo, pageSize);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.warehouseContent().size());
        assertEquals(0, result.pageNo());
        assertEquals(10, result.pageSize());
        assertEquals(1, result.totalElements());
        assertEquals(1, result.totalPages());
        assertTrue(result.isLast());

        verify(warehouseRepository).findAll(pageable);
    }

    @Test
    void testGetPageableWarehouses_whenNoWarehouses_shouldReturnEmptyPage() {
        // Arrange
        int pageNo = 0;
        int pageSize = 10;
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Page<Warehouse> warehousePage = new PageImpl<>(Collections.emptyList(), pageable, 0);

        when(warehouseRepository.findAll(pageable)).thenReturn(warehousePage);

        // Act
        WarehouseListGetVm result = warehouseService.getPageableWarehouses(pageNo, pageSize);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.warehouseContent().size());
        assertEquals(0, result.totalElements());

        verify(warehouseRepository).findAll(pageable);
    }

    @Test
    void testGetPageableWarehouses_whenMultiplePages_shouldReturnCorrectPage() {
        // Arrange
        int pageNo = 1;
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        List<Warehouse> warehouses = List.of(warehouse);
        Page<Warehouse> warehousePage = new PageImpl<>(warehouses, pageable, 15);

        when(warehouseRepository.findAll(pageable)).thenReturn(warehousePage);

        // Act
        WarehouseListGetVm result = warehouseService.getPageableWarehouses(pageNo, pageSize);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.pageNo());
        assertEquals(5, result.pageSize());
        assertEquals(15, result.totalElements());
        assertEquals(3, result.totalPages());

        verify(warehouseRepository).findAll(pageable);
    }
}
