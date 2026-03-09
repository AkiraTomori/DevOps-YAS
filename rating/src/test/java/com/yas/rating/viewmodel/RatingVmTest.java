package com.yas.rating.viewmodel;

import com.yas.rating.model.Rating;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class RatingVmTest {

    @Test
    void testRatingVm_shouldCreateWithAllFields() {
        // Arrange
        ZonedDateTime now = ZonedDateTime.now();

        // Act
        RatingVm vm = RatingVm.builder()
                .id(1L)
                .content("Great product!")
                .star(5)
                .productId(101L)
                .productName("Test Product")
                .createdBy("user123")
                .lastName("Doe")
                .firstName("John")
                .createdOn(now)
                .build();

        // Assert
        assertNotNull(vm);
        assertEquals(1L, vm.id());
        assertEquals("Great product!", vm.content());
        assertEquals(5, vm.star());
        assertEquals(101L, vm.productId());
        assertEquals("Test Product", vm.productName());
        assertEquals("user123", vm.createdBy());
        assertEquals("Doe", vm.lastName());
        assertEquals("John", vm.firstName());
        assertEquals(now, vm.createdOn());
    }

    @Test
    void testRatingVm_fromModel_shouldMapAllFields() {
        // Arrange
        ZonedDateTime now = ZonedDateTime.now();
        Rating rating = Rating.builder()
                .id(1L)
                .content("Excellent!")
                .ratingStar(5)
                .productId(101L)
                .productName("Product")
                .lastName("Smith")
                .firstName("Jane")
                .build();
        rating.setCreatedBy("user456");
        rating.setCreatedOn(now);

        // Act
        RatingVm vm = RatingVm.fromModel(rating);

        // Assert
        assertNotNull(vm);
        assertEquals(1L, vm.id());
        assertEquals("Excellent!", vm.content());
        assertEquals(5, vm.star());
        assertEquals(101L, vm.productId());
        assertEquals("Product", vm.productName());
        assertEquals("Smith", vm.lastName());
        assertEquals("Jane", vm.firstName());
        assertEquals("user456", vm.createdBy());
        assertEquals(now, vm.createdOn());
    }

    @Test
    void testRatingVm_fromModel_withNullValues() {
        // Arrange
        Rating rating = new Rating();

        // Act
        RatingVm vm = RatingVm.fromModel(rating);

        // Assert
        assertNotNull(vm);
        assertNull(vm.id());
        assertNull(vm.content());
        assertEquals(0, vm.star());
    }

    @Test
    void testRatingVm_recordEquality() {
        // Arrange
        ZonedDateTime now = ZonedDateTime.now();
        RatingVm vm1 = RatingVm.builder()
                .id(1L)
                .content("Test")
                .star(4)
                .productId(100L)
                .productName("Product")
                .createdBy("user")
                .lastName("Last")
                .firstName("First")
                .createdOn(now)
                .build();
        RatingVm vm2 = RatingVm.builder()
                .id(1L)
                .content("Test")
                .star(4)
                .productId(100L)
                .productName("Product")
                .createdBy("user")
                .lastName("Last")
                .firstName("First")
                .createdOn(now)
                .build();

        // Assert
        assertEquals(vm1, vm2);
    }

    @Test
    void testRatingVm_recordToString() {
        // Arrange
        RatingVm vm = RatingVm.builder()
                .id(1L)
                .content("Test content")
                .star(3)
                .productId(100L)
                .productName("Product Name")
                .build();

        // Act
        String result = vm.toString();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("1"));
        assertTrue(result.contains("Test content"));
    }
}
