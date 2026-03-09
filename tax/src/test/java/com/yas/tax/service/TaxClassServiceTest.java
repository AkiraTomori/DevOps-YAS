package com.yas.tax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.yas.commonlibrary.exception.DuplicatedException;
import com.yas.commonlibrary.exception.NotFoundException;
import com.yas.tax.model.TaxClass;
import com.yas.tax.repository.TaxClassRepository;
import com.yas.tax.viewmodel.taxclass.TaxClassListGetVm;
import com.yas.tax.viewmodel.taxclass.TaxClassPostVm;
import com.yas.tax.viewmodel.taxclass.TaxClassVm;
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
import org.springframework.data.domain.Sort;

@ExtendWith(MockitoExtension.class)
class TaxClassServiceTest {

    @Mock
    private TaxClassRepository taxClassRepository;

    @InjectMocks
    private TaxClassService taxClassService;

    private TaxClass taxClass;
    private TaxClassPostVm taxClassPostVm;

    @BeforeEach
    void setUp() {
        taxClass = TaxClass.builder()
            .id(1L)
            .name("Standard Tax")
            .build();

        taxClassPostVm = new TaxClassPostVm(null, "Standard Tax");
    }

    @Test
    void testFindAllTaxClasses_whenTaxClassesExist_shouldReturnSortedList() {
        // Arrange
        TaxClass taxClass2 = TaxClass.builder()
            .id(2L)
            .name("Reduced Tax")
            .build();

        List<TaxClass> taxClasses = List.of(taxClass, taxClass2);
        when(taxClassRepository.findAll(Sort.by(Sort.Direction.ASC, "name")))
            .thenReturn(taxClasses);

        // Act
        List<TaxClassVm> result = taxClassService.findAllTaxClasses();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Standard Tax", result.get(0).name());
        assertEquals("Reduced Tax", result.get(1).name());

        verify(taxClassRepository).findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @Test
    void testFindAllTaxClasses_whenNoTaxClasses_shouldReturnEmptyList() {
        // Arrange
        when(taxClassRepository.findAll(Sort.by(Sort.Direction.ASC, "name")))
            .thenReturn(Collections.emptyList());

        // Act
        List<TaxClassVm> result = taxClassService.findAllTaxClasses();

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());

        verify(taxClassRepository).findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @Test
    void testFindById_whenTaxClassExists_shouldReturnTaxClass() {
        // Arrange
        Long id = 1L;
        when(taxClassRepository.findById(id)).thenReturn(Optional.of(taxClass));

        // Act
        TaxClassVm result = taxClassService.findById(id);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Standard Tax", result.name());

        verify(taxClassRepository).findById(id);
    }

    @Test
    void testFindById_whenTaxClassNotFound_shouldThrowNotFoundException() {
        // Arrange
        Long id = 999L;
        when(taxClassRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> taxClassService.findById(id));

        verify(taxClassRepository).findById(id);
    }

    @Test
    void testCreate_whenValidInput_shouldCreateTaxClass() {
        // Arrange
        when(taxClassRepository.existsByName("Standard Tax")).thenReturn(false);
        when(taxClassRepository.save(any(TaxClass.class))).thenReturn(taxClass);

        // Act
        TaxClass result = taxClassService.create(taxClassPostVm);

        // Assert
        assertNotNull(result);
        assertEquals("Standard Tax", result.getName());

        verify(taxClassRepository).existsByName("Standard Tax");
        verify(taxClassRepository).save(any(TaxClass.class));
    }

    @Test
    void testCreate_whenNameAlreadyExists_shouldThrowDuplicatedException() {
        // Arrange
        when(taxClassRepository.existsByName("Standard Tax")).thenReturn(true);

        // Act & Assert
        assertThrows(DuplicatedException.class, () -> taxClassService.create(taxClassPostVm));

        verify(taxClassRepository).existsByName("Standard Tax");
        verify(taxClassRepository, times(0)).save(any(TaxClass.class));
    }

    @Test
    void testUpdate_whenValidInput_shouldUpdateTaxClass() {
        // Arrange
        Long id = 1L;
        TaxClassPostVm updateVm = new TaxClassPostVm(null, "Updated Tax");

        when(taxClassRepository.findById(id)).thenReturn(Optional.of(taxClass));
        when(taxClassRepository.existsByNameNotUpdatingTaxClass("Updated Tax", id)).thenReturn(false);
        when(taxClassRepository.save(any(TaxClass.class))).thenReturn(taxClass);

        // Act
        assertDoesNotThrow(() -> taxClassService.update(updateVm, id));

        // Assert
        assertEquals("Updated Tax", taxClass.getName());
        verify(taxClassRepository).findById(id);
        verify(taxClassRepository).existsByNameNotUpdatingTaxClass("Updated Tax", id);
        verify(taxClassRepository).save(any(TaxClass.class));
    }

    @Test
    void testUpdate_whenTaxClassNotFound_shouldThrowNotFoundException() {
        // Arrange
        Long id = 999L;
        when(taxClassRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> taxClassService.update(taxClassPostVm, id));

        verify(taxClassRepository).findById(id);
        verify(taxClassRepository, times(0)).existsByNameNotUpdatingTaxClass(anyString(), anyLong());
    }

    @Test
    void testUpdate_whenNameExistsForDifferentTaxClass_shouldThrowDuplicatedException() {
        // Arrange
        Long id = 1L;
        when(taxClassRepository.findById(id)).thenReturn(Optional.of(taxClass));
        when(taxClassRepository.existsByNameNotUpdatingTaxClass("Standard Tax", id)).thenReturn(true);

        // Act & Assert
        assertThrows(DuplicatedException.class, () -> taxClassService.update(taxClassPostVm, id));

        verify(taxClassRepository).findById(id);
        verify(taxClassRepository).existsByNameNotUpdatingTaxClass("Standard Tax", id);
        verify(taxClassRepository, times(0)).save(any(TaxClass.class));
    }

    @Test
    void testDelete_whenTaxClassExists_shouldDeleteTaxClass() {
        // Arrange
        Long id = 1L;
        when(taxClassRepository.existsById(id)).thenReturn(true);

        // Act
        assertDoesNotThrow(() -> taxClassService.delete(id));

        // Assert
        verify(taxClassRepository).existsById(id);
        verify(taxClassRepository).deleteById(id);
    }

    @Test
    void testDelete_whenTaxClassNotFound_shouldThrowNotFoundException() {
        // Arrange
        Long id = 999L;
        when(taxClassRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(NotFoundException.class, () -> taxClassService.delete(id));

        verify(taxClassRepository).existsById(id);
        verify(taxClassRepository, times(0)).deleteById(anyLong());
    }

    @Test
    void testGetPageableTaxClasses_whenTaxClassesExist_shouldReturnPagedList() {
        // Arrange
        int pageNo = 0;
        int pageSize = 10;
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        List<TaxClass> taxClasses = List.of(taxClass);
        Page<TaxClass> taxClassPage = new PageImpl<>(taxClasses, pageable, 1);

        when(taxClassRepository.findAll(pageable)).thenReturn(taxClassPage);

        // Act
        TaxClassListGetVm result = taxClassService.getPageableTaxClasses(pageNo, pageSize);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.taxClassContent().size());
        assertEquals(0, result.pageNo());
        assertEquals(10, result.pageSize());
        assertEquals(1, result.totalElements());
        assertEquals(1, result.totalPages());
        assertTrue(result.isLast());

        verify(taxClassRepository).findAll(pageable);
    }

    @Test
    void testGetPageableTaxClasses_whenNoTaxClasses_shouldReturnEmptyPage() {
        // Arrange
        int pageNo = 0;
        int pageSize = 10;
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Page<TaxClass> taxClassPage = new PageImpl<>(Collections.emptyList(), pageable, 0);

        when(taxClassRepository.findAll(pageable)).thenReturn(taxClassPage);

        // Act
        TaxClassListGetVm result = taxClassService.getPageableTaxClasses(pageNo, pageSize);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.taxClassContent().size());
        assertEquals(0, result.totalElements());

        verify(taxClassRepository).findAll(pageable);
    }

    @Test
    void testGetPageableTaxClasses_whenMultiplePages_shouldReturnCorrectPage() {
        // Arrange
        int pageNo = 1;
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        List<TaxClass> taxClasses = List.of(taxClass);
        Page<TaxClass> taxClassPage = new PageImpl<>(taxClasses, pageable, 15);

        when(taxClassRepository.findAll(pageable)).thenReturn(taxClassPage);

        // Act
        TaxClassListGetVm result = taxClassService.getPageableTaxClasses(pageNo, pageSize);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.pageNo());
        assertEquals(5, result.pageSize());
        assertEquals(15, result.totalElements());
        assertEquals(3, result.totalPages());

        verify(taxClassRepository).findAll(pageable);
    }
}
