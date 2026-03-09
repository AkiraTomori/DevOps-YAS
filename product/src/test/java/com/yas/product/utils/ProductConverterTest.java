package com.yas.product.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ProductConverterTest {

    @Test
    void testToSlug_WithNormalText() {
        // When
        String result = ProductConverter.toSlug("Test Product Name");

        // Then
        assertEquals("test-product-name", result);
    }

    @Test
    void testToSlug_WithSpecialCharacters() {
        // When
        String result = ProductConverter.toSlug("Test@Product#Name!");

        // Then
        assertEquals("test-product-name-", result);
    }

    @Test
    void testToSlug_WithMultipleSpaces() {
        // When
        String result = ProductConverter.toSlug("Test   Product   Name");

        // Then
        assertEquals("test-product-name", result);
    }

    @Test
    void testToSlug_WithLeadingHyphen() {
        // When
        String result = ProductConverter.toSlug("-Test Product");

        // Then
        assertEquals("test-product", result);
    }

    @Test
    void testToSlug_WithMultipleHyphens() {
        // When
        String result = ProductConverter.toSlug("Test--Product--Name");

        // Then
        assertEquals("test-product-name", result);
    }

    @Test
    void testToSlug_WithUpperCase() {
        // When
        String result = ProductConverter.toSlug("TEST PRODUCT NAME");

        // Then
        assertEquals("test-product-name", result);
    }

    @Test
    void testToSlug_WithNumbers() {
        // When
        String result = ProductConverter.toSlug("Product 123");

        // Then
        assertEquals("product-123", result);
    }

    @Test
    void testToSlug_WithMixedCharacters() {
        // When
        String result = ProductConverter.toSlug("Product#123 Test@456");

        // Then
        assertEquals("product-123-test-456", result);
    }

    @Test
    void testToSlug_WithTrailingSpaces() {
        // When
        String result = ProductConverter.toSlug("  Test Product  ");

        // Then
        assertEquals("test-product", result);
    }

    @Test
    void testToSlug_EmptyString() {
        // When
        String result = ProductConverter.toSlug("");

        // Then
        assertEquals("", result);
    }
}
