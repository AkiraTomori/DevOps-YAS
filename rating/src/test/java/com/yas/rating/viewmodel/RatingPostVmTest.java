package com.yas.rating.viewmodel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RatingPostVmTest {

    @Test
    void testRatingPostVm_shouldCreateWithAllFields() {
        // Act
        RatingPostVm vm = RatingPostVm.builder()
                .content("Great product!")
                .star(5)
                .productId(101L)
                .productName("Test Product")
                .build();

        // Assert
        assertNotNull(vm);
        assertEquals("Great product!", vm.content());
        assertEquals(5, vm.star());
        assertEquals(101L, vm.productId());
        assertEquals("Test Product", vm.productName());
    }

    @Test
    void testRatingPostVm_withMinimumRating() {
        // Act
        RatingPostVm vm = RatingPostVm.builder()
                .content("Bad product")
                .star(1)
                .productId(102L)
                .productName("Another Product")
                .build();

        // Assert
        assertEquals(1, vm.star());
    }

    @Test
    void testRatingPostVm_withMaximumRating() {
        // Act
        RatingPostVm vm = RatingPostVm.builder()
                .content("Excellent product")
                .star(5)
                .productId(103L)
                .productName("Best Product")
                .build();

        // Assert
        assertEquals(5, vm.star());
    }

    @Test
    void testRatingPostVm_recordEquality() {
        // Arrange
        RatingPostVm vm1 = RatingPostVm.builder()
                .content("Test")
                .star(4)
                .productId(100L)
                .productName("Product")
                .build();
        RatingPostVm vm2 = RatingPostVm.builder()
                .content("Test")
                .star(4)
                .productId(100L)
                .productName("Product")
                .build();

        // Assert
        assertEquals(vm1, vm2);
    }

    @Test
    void testRatingPostVm_recordToString() {
        // Arrange
        RatingPostVm vm = RatingPostVm.builder()
                .content("Test content")
                .star(3)
                .productId(100L)
                .productName("Product Name")
                .build();

        // Act
        String result = vm.toString();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("Test content"));
        assertTrue(result.contains("3"));
        assertTrue(result.contains("100"));
    }
}
