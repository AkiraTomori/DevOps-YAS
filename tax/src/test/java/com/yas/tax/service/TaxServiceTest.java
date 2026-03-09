package com.yas.tax.service;

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

import com.yas.commonlibrary.exception.BadRequestException;
import com.yas.commonlibrary.exception.NotFoundException;
import com.yas.tax.model.TaxClass;
import com.yas.tax.model.TaxRate;
import com.yas.tax.repository.TaxClassRepository;
import com.yas.tax.repository.TaxRateRepository;
import com.yas.tax.viewmodel.location.StateOrProvinceAndCountryGetNameVm;
import com.yas.tax.viewmodel.taxrate.TaxRateGetDetailVm;
import com.yas.tax.viewmodel.taxrate.TaxRateListGetVm;
import com.yas.tax.viewmodel.taxrate.TaxRatePostVm;
import com.yas.tax.viewmodel.taxrate.TaxRateVm;
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
class TaxServiceTest {

    @Mock
    private TaxRateRepository taxRateRepository;

    @Mock
    private TaxClassRepository taxClassRepository;

    @Mock
    private LocationService locationService;

    @InjectMocks
    private TaxRateService taxRateService;

    private TaxClass taxClass;
    private TaxRate taxRate;
    private TaxRatePostVm taxRatePostVm;

    @BeforeEach
    void setUp() {
        taxClass = TaxClass.builder()
            .id(1L)
            .name("Standard Tax")
            .build();

        taxRate = TaxRate.builder()
            .id(1L)
            .rate(10.5)
            .zipCode("12345")
            .stateOrProvinceId(1L)
            .countryId(100L)
            .taxClass(taxClass)
            .build();

        taxRatePostVm = new TaxRatePostVm(
            10.5,
            "12345",
            1L,
            1L,
            100L
        );
    }

    @Test
    void testFindAll_whenTaxRatesExist_shouldReturnAllTaxRates() {
        // Arrange
        when(taxRateRepository.findAll()).thenReturn(List.of(taxRate));

        // Act
        List<TaxRateVm> result = taxRateService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).id());
        assertEquals(10.5, result.get(0).rate());

        verify(taxRateRepository).findAll();
    }

    @Test
    void testFindAll_whenNoTaxRates_shouldReturnEmptyList() {
        // Arrange
        when(taxRateRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<TaxRateVm> result = taxRateService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());

        verify(taxRateRepository).findAll();
    }

    @Test
    void testFindById_whenTaxRateExists_shouldReturnTaxRate() {
        // Arrange
        Long id = 1L;
        when(taxRateRepository.findById(id)).thenReturn(Optional.of(taxRate));

        // Act
        TaxRateVm result = taxRateService.findById(id);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals(10.5, result.rate());
        assertEquals("12345", result.zipCode());

        verify(taxRateRepository).findById(id);
    }

    @Test
    void testFindById_whenTaxRateNotFound_shouldThrowNotFoundException() {
        // Arrange
        Long id = 999L;
        when(taxRateRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> taxRateService.findById(id));

        verify(taxRateRepository).findById(id);
    }

    @Test
    void testCreateTaxRate_whenValidInput_shouldCreateTaxRate() {
        // Arrange
        when(taxClassRepository.existsById(1L)).thenReturn(true);
        when(taxClassRepository.getReferenceById(1L)).thenReturn(taxClass);
        when(taxRateRepository.save(any(TaxRate.class))).thenReturn(taxRate);

        // Act
        TaxRate result = taxRateService.createTaxRate(taxRatePostVm);

        // Assert
        assertNotNull(result);
        assertEquals(10.5, result.getRate());
        assertEquals("12345", result.getZipCode());

        verify(taxClassRepository).existsById(1L);
        verify(taxRateRepository).save(any(TaxRate.class));
    }

    @Test
    void testCreateTaxRate_whenTaxClassNotFound_shouldThrowNotFoundException() {
        // Arrange
        when(taxClassRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        assertThrows(NotFoundException.class, () -> taxRateService.createTaxRate(taxRatePostVm));

        verify(taxClassRepository).existsById(1L);
        verify(taxRateRepository, times(0)).save(any(TaxRate.class));
    }

    @Test
    void testUpdateTaxRate_whenValidInput_shouldUpdateTaxRate() {
        // Arrange
        Long id = 1L;
        TaxRatePostVm updateVm = new TaxRatePostVm(
            15.0,
            "54321",
            1L,
            2L,
            200L
        );

        when(taxRateRepository.findById(id)).thenReturn(Optional.of(taxRate));
        when(taxClassRepository.existsById(1L)).thenReturn(true);
        when(taxClassRepository.getReferenceById(1L)).thenReturn(taxClass);
        when(taxRateRepository.save(any(TaxRate.class))).thenReturn(taxRate);

        // Act
        assertDoesNotThrow(() -> taxRateService.updateTaxRate(updateVm, id));

        // Assert
        assertEquals(15.0, taxRate.getRate());
        assertEquals("54321", taxRate.getZipCode());
        assertEquals(2L, taxRate.getStateOrProvinceId());
        assertEquals(200L, taxRate.getCountryId());

        verify(taxRateRepository).findById(id);
        verify(taxClassRepository).existsById(1L);
        verify(taxRateRepository).save(any(TaxRate.class));
    }

    @Test
    void testUpdateTaxRate_whenTaxRateNotFound_shouldThrowNotFoundException() {
        // Arrange
        Long id = 999L;
        TaxRatePostVm updateVm = new TaxRatePostVm(15.0, "54321", 1L, 2L, 200L);
        when(taxRateRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> taxRateService.updateTaxRate(updateVm, id));

        verify(taxRateRepository).findById(id);
        verify(taxClassRepository, times(0)).existsById(anyLong());
    }

    @Test
    void testUpdateTaxRate_whenTaxClassNotFound_shouldThrowBadRequestException() {
        // Arrange
        Long id = 1L;
        TaxRatePostVm updateVm = new TaxRatePostVm(15.0, "54321", 1L, 2L, 200L);
        when(taxRateRepository.findById(id)).thenReturn(Optional.of(taxRate));
        when(taxClassRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        assertThrows(NotFoundException.class, () -> taxRateService.updateTaxRate(updateVm, id));

        verify(taxRateRepository).findById(id);
        verify(taxClassRepository).existsById(1L);
        verify(taxRateRepository, times(0)).save(any(TaxRate.class));
    }

    @Test
    void testDelete_whenTaxRateExists_shouldDeleteTaxRate() {
        // Arrange
        Long id = 1L;
        when(taxRateRepository.existsById(id)).thenReturn(true);

        // Act
        assertDoesNotThrow(() -> taxRateService.delete(id));

        // Assert
        verify(taxRateRepository).existsById(id);
        verify(taxRateRepository).deleteById(id);
    }

    @Test
    void testDelete_whenTaxRateNotFound_shouldThrowNotFoundException() {
        // Arrange
        Long id = 999L;
        when(taxRateRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(NotFoundException.class, () -> taxRateService.delete(id));

        verify(taxRateRepository).existsById(id);
        verify(taxRateRepository, times(0)).deleteById(anyLong());
    }

    @Test
    void testGetPageableTaxRates_whenTaxRatesExist_shouldReturnPagedListWithLocationNames() {
        // Arrange
        int pageNo = 0;
        int pageSize = 10;
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        List<TaxRate> taxRates = List.of(taxRate);
        Page<TaxRate> taxRatePage = new PageImpl<>(taxRates, pageable, 1);

        StateOrProvinceAndCountryGetNameVm locationVm = new StateOrProvinceAndCountryGetNameVm(
            1L,
            "California",
            "United States"
        );

        when(taxRateRepository.findAll(pageable)).thenReturn(taxRatePage);
        when(locationService.getStateOrProvinceAndCountryNames(any()))
            .thenReturn(List.of(locationVm));

        // Act
        TaxRateListGetVm result = taxRateService.getPageableTaxRates(pageNo, pageSize);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.taxRateGetDetailContent().size());
        assertEquals(0, result.pageNo());
        assertEquals(10, result.pageSize());
        assertEquals(1, result.totalElements());
        assertEquals(1, result.totalPages());
        assertTrue(result.isLast());

        TaxRateGetDetailVm taxRateDetail = result.taxRateGetDetailContent().get(0);
        assertEquals(10.5, taxRateDetail.rate());
        assertEquals("12345", taxRateDetail.zipCode());
        assertEquals("California", taxRateDetail.stateOrProvinceName());
        assertEquals("United States", taxRateDetail.countryName());

        verify(taxRateRepository).findAll(pageable);
        verify(locationService).getStateOrProvinceAndCountryNames(any());
    }

    @Test
    void testGetPageableTaxRates_whenNoTaxRates_shouldReturnEmptyPage() {
        // Arrange
        int pageNo = 0;
        int pageSize = 10;
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Page<TaxRate> taxRatePage = new PageImpl<>(Collections.emptyList(), pageable, 0);

        when(taxRateRepository.findAll(pageable)).thenReturn(taxRatePage);

        // Act
        TaxRateListGetVm result = taxRateService.getPageableTaxRates(pageNo, pageSize);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.taxRateGetDetailContent().size());
        assertEquals(0, result.totalElements());

        verify(taxRateRepository).findAll(pageable);
        verify(locationService, times(0)).getStateOrProvinceAndCountryNames(any());
    }

    @Test
    void testGetPageableTaxRates_whenMultiplePages_shouldReturnCorrectPage() {
        // Arrange
        int pageNo = 1;
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        List<TaxRate> taxRates = List.of(taxRate);
        Page<TaxRate> taxRatePage = new PageImpl<>(taxRates, pageable, 15);

        StateOrProvinceAndCountryGetNameVm locationVm = new StateOrProvinceAndCountryGetNameVm(
            1L,
            "California",
            "United States"
        );

        when(taxRateRepository.findAll(pageable)).thenReturn(taxRatePage);
        when(locationService.getStateOrProvinceAndCountryNames(any()))
            .thenReturn(List.of(locationVm));

        // Act
        TaxRateListGetVm result = taxRateService.getPageableTaxRates(pageNo, pageSize);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.pageNo());
        assertEquals(5, result.pageSize());
        assertEquals(15, result.totalElements());
        assertEquals(3, result.totalPages());

        verify(taxRateRepository).findAll(pageable);
    }

    @Test
    void testCreateTaxRate_whenValidInputWithAllFields_shouldCreateTaxRate() {
        // Arrange
        TaxRatePostVm fullVm = new TaxRatePostVm(
            12.5,
            "98765",
            1L,
            3L,
            300L
        );

        when(taxClassRepository.existsById(1L)).thenReturn(true);
        when(taxClassRepository.getReferenceById(1L)).thenReturn(taxClass);
        when(taxRateRepository.save(any(TaxRate.class))).thenReturn(taxRate);

        // Act
        TaxRate result = taxRateService.createTaxRate(fullVm);

        // Assert
        assertNotNull(result);
        verify(taxClassRepository).existsById(1L);
        verify(taxRateRepository).save(any(TaxRate.class));
    }
}
