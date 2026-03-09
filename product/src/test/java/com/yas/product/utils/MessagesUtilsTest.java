package com.yas.product.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class MessagesUtilsTest {

    @Test
    void testGetMessage_WithValidErrorCode() {
        // When
        String message = MessagesUtils.getMessage("PRODUCT_NOT_FOUND", 123L);

        // Then
        assertNotNull(message);
        assertTrue(message.contains("123"));
        assertEquals("Product 123 is not found", message);
    }

    @Test
    void testGetMessage_WithMultipleParameters() {
        // When
        String message = MessagesUtils.getMessage("SLUG_ALREADY_EXISTED_OR_DUPLICATED", "test-product");

        // Then
        assertNotNull(message);
        assertTrue(message.contains("test-product"));
        assertEquals("Slug test-product is already existed or is duplicated", message);
    }

    @Test
    void testGetMessage_WithInvalidErrorCode() {
        // When - Invalid error code should return the code itself
        String message = MessagesUtils.getMessage("INVALID_ERROR_CODE", "param");

        // Then
        assertNotNull(message);
        assertEquals("INVALID_ERROR_CODE", message);
    }

    @Test
    void testGetMessage_WithNoParameters() {
        // When
        String message = MessagesUtils.getMessage("MAKE_SURE_CATEGORY_DO_NOT_CONTAIN_CHILDREN");

        // Then
        assertNotNull(message);
        assertEquals("Please make sure this category contains no children", message);
    }

    @Test
    void testGetMessage_WithCategoryNotFound() {
        // When
        String message = MessagesUtils.getMessage("CATEGORY_NOT_FOUND", 456L);

        // Then
        assertNotNull(message);
        assertEquals("Category 456 is not found", message);
    }

    @Test
    void testGetMessage_WithBrandNotFound() {
        // When
        String message = MessagesUtils.getMessage("BRAND_NOT_FOUND", 789L);

        // Then
        assertNotNull(message);
        assertEquals("Brand 789 is not found", message);
    }

    @Test
    void testGetMessage_WithSkuDuplicated() {
        // When
        String message = MessagesUtils.getMessage("SKU_ALREADY_EXISTED_OR_DUPLICATED", "SKU-001");

        // Then
        assertNotNull(message);
        assertTrue(message.contains("SKU-001"));
    }

    @Test
    void testGetMessage_WithGtinDuplicated() {
        // When
        String message = MessagesUtils.getMessage("GTIN_ALREADY_EXISTED_OR_DUPLICATED", "GTIN-123");

        // Then
        assertNotNull(message);
        assertTrue(message.contains("GTIN-123"));
    }
}
