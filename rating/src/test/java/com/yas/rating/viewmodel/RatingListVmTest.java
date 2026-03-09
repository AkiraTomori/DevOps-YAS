package com.yas.rating.viewmodel;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RatingListVmTest {

    @Test
    void testRatingListVm_shouldCreateWithAllFields() {
        // Arrange
        RatingVm rating1 = RatingVm.builder()
                .id(1L)
                .content("Great!")
                .star(5)
                .build();
        RatingVm rating2 = RatingVm.builder()
                .id(2L)
                .content("Good")
                .star(4)
                .build();
        List<RatingVm> ratingList = Arrays.asList(rating1, rating2);

        // Act
        RatingListVm vm = new RatingListVm(ratingList, 10L, 2);

        // Assert
        assertNotNull(vm);
        assertEquals(2, vm.ratingList().size());
        assertEquals(10L, vm.totalElements());
        assertEquals(2, vm.totalPages());
    }

    @Test
    void testRatingListVm_withEmptyList() {
        // Act
        RatingListVm vm = new RatingListVm(Collections.emptyList(), 0L, 0);

        // Assert
        assertNotNull(vm);
        assertTrue(vm.ratingList().isEmpty());
        assertEquals(0L, vm.totalElements());
        assertEquals(0, vm.totalPages());
    }

    @Test
    void testRatingListVm_recordEquality() {
        // Arrange
        List<RatingVm> list = Collections.emptyList();
        RatingListVm vm1 = new RatingListVm(list, 5L, 1);
        RatingListVm vm2 = new RatingListVm(list, 5L, 1);

        // Assert
        assertEquals(vm1, vm2);
    }

    @Test
    void testRatingListVm_recordToString() {
        // Arrange
        RatingListVm vm = new RatingListVm(Collections.emptyList(), 15L, 3);

        // Act
        String result = vm.toString();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("15"));
        assertTrue(result.contains("3"));
    }
}
