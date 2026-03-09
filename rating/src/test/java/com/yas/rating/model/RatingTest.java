package com.yas.rating.model;

import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class RatingTest {

    @Test
    void testRating_noArgsConstructor() {
        // Act
        Rating rating = new Rating();

        // Assert
        assertNotNull(rating);
    }

    @Test
    void testRating_allArgsConstructor() {
        // Act
        Rating rating = new Rating(
                1L,
                "Great product!",
                5,
                101L,
                "Test Product",
                "Doe",
                "John"
        );

        // Assert
        assertNotNull(rating);
        assertEquals(1L, rating.getId());
        assertEquals("Great product!", rating.getContent());
        assertEquals(5, rating.getRatingStar());
        assertEquals(101L, rating.getProductId());
        assertEquals("Test Product", rating.getProductName());
        assertEquals("Doe", rating.getLastName());
        assertEquals("John", rating.getFirstName());
    }

    @Test
    void testRating_builder() {
        // Act
        Rating rating = Rating.builder()
                .id(1L)
                .content("Excellent!")
                .ratingStar(5)
                .productId(101L)
                .productName("Test Product")
                .lastName("Smith")
                .firstName("Jane")
                .build();

        // Assert
        assertNotNull(rating);
        assertEquals(1L, rating.getId());
        assertEquals("Excellent!", rating.getContent());
        assertEquals(5, rating.getRatingStar());
        assertEquals(101L, rating.getProductId());
        assertEquals("Test Product", rating.getProductName());
        assertEquals("Smith", rating.getLastName());
        assertEquals("Jane", rating.getFirstName());
    }

    @Test
    void testRating_gettersAndSetters() {
        // Arrange
        Rating rating = new Rating();

        // Act
        rating.setId(2L);
        rating.setContent("Good product");
        rating.setRatingStar(4);
        rating.setProductId(102L);
        rating.setProductName("Another Product");
        rating.setLastName("Brown");
        rating.setFirstName("Bob");

        // Assert
        assertEquals(2L, rating.getId());
        assertEquals("Good product", rating.getContent());
        assertEquals(4, rating.getRatingStar());
        assertEquals(102L, rating.getProductId());
        assertEquals("Another Product", rating.getProductName());
        assertEquals("Brown", rating.getLastName());
        assertEquals("Bob", rating.getFirstName());
    }

    @Test
    void testRating_equals_sameId() {
        // Arrange
        Rating rating1 = Rating.builder().id(1L).build();
        Rating rating2 = Rating.builder().id(1L).build();

        // Assert
        assertEquals(rating1, rating2);
    }

    @Test
    void testRating_equals_differentId() {
        // Arrange
        Rating rating1 = Rating.builder().id(1L).build();
        Rating rating2 = Rating.builder().id(2L).build();

        // Assert
        assertNotEquals(rating1, rating2);
    }

    @Test
    void testRating_equals_sameObject() {
        // Arrange
        Rating rating = Rating.builder().id(1L).build();

        // Assert
        assertEquals(rating, rating);
    }

    @Test
    void testRating_equals_nullId() {
        // Arrange
        Rating rating1 = new Rating();
        Rating rating2 = new Rating();

        // Assert
        assertNotEquals(rating1, rating2);
    }

    @Test
    void testRating_equals_differentClass() {
        // Arrange
        Rating rating = Rating.builder().id(1L).build();
        Object other = new Object();

        // Assert
        assertNotEquals(rating, other);
    }

    @Test
    void testRating_hashCode_shouldBeConsistent() {
        // Arrange
        Rating rating = Rating.builder().id(1L).content("Test").build();

        // Act
        int hash1 = rating.hashCode();
        int hash2 = rating.hashCode();

        // Assert
        assertEquals(hash1, hash2);
    }

    @Test
    void testRating_hashCode_sameClass() {
        // Arrange
        Rating rating1 = Rating.builder().id(1L).build();
        Rating rating2 = Rating.builder().id(2L).build();

        // Act & Assert
        assertEquals(rating1.hashCode(), rating2.hashCode());
    }
}
