package com.yas.cart.viewmodel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductThumbnailVmTest {

    @Test
    void testProductThumbnailVm_builder() {
        // Arrange & Act
        ProductThumbnailVm productThumbnailVm = ProductThumbnailVm.builder()
            .id(1L)
            .name("Test Product")
            .slug("test-product")
            .thumbnailUrl("https://example.com/image.jpg")
            .build();

        // Assert
        assertNotNull(productThumbnailVm);
        assertEquals(1L, productThumbnailVm.id());
        assertEquals("Test Product", productThumbnailVm.name());
        assertEquals("test-product", productThumbnailVm.slug());
        assertEquals("https://example.com/image.jpg", productThumbnailVm.thumbnailUrl());
    }

    @Test
    void testProductThumbnailVm_constructor() {
        // Arrange & Act
        ProductThumbnailVm productThumbnailVm = new ProductThumbnailVm(
            2L,
            "Another Product",
            "another-product",
            "https://example.com/another.jpg"
        );

        // Assert
        assertNotNull(productThumbnailVm);
        assertEquals(2L, productThumbnailVm.id());
        assertEquals("Another Product", productThumbnailVm.name());
        assertEquals("another-product", productThumbnailVm.slug());
        assertEquals("https://example.com/another.jpg", productThumbnailVm.thumbnailUrl());
    }

    @Test
    void testProductThumbnailVm_withNullValues() {
        // Arrange & Act
        ProductThumbnailVm productThumbnailVm = new ProductThumbnailVm(1L, null, null, null);

        // Assert
        assertNotNull(productThumbnailVm);
        assertEquals(1L, productThumbnailVm.id());
        assertNull(productThumbnailVm.name());
        assertNull(productThumbnailVm.slug());
        assertNull(productThumbnailVm.thumbnailUrl());
    }

    @Test
    void testProductThumbnailVm_equals_withSameValues() {
        // Arrange
        ProductThumbnailVm vm1 = new ProductThumbnailVm(1L, "Product", "product", "url");
        ProductThumbnailVm vm2 = new ProductThumbnailVm(1L, "Product", "product", "url");

        // Act & Assert
        assertEquals(vm1, vm2);
    }

    @Test
    void testProductThumbnailVm_equals_withDifferentId() {
        // Arrange
        ProductThumbnailVm vm1 = new ProductThumbnailVm(1L, "Product", "product", "url");
        ProductThumbnailVm vm2 = new ProductThumbnailVm(2L, "Product", "product", "url");

        // Act & Assert
        assertNotEquals(vm1, vm2);
    }

    @Test
    void testProductThumbnailVm_equals_withDifferentName() {
        // Arrange
        ProductThumbnailVm vm1 = new ProductThumbnailVm(1L, "Product1", "product", "url");
        ProductThumbnailVm vm2 = new ProductThumbnailVm(1L, "Product2", "product", "url");

        // Act & Assert
        assertNotEquals(vm1, vm2);
    }

    @Test
    void testProductThumbnailVm_equals_withDifferentSlug() {
        // Arrange
        ProductThumbnailVm vm1 = new ProductThumbnailVm(1L, "Product", "product-1", "url");
        ProductThumbnailVm vm2 = new ProductThumbnailVm(1L, "Product", "product-2", "url");

        // Act & Assert
        assertNotEquals(vm1, vm2);
    }

    @Test
    void testProductThumbnailVm_equals_withDifferentThumbnailUrl() {
        // Arrange
        ProductThumbnailVm vm1 = new ProductThumbnailVm(1L, "Product", "product", "url1");
        ProductThumbnailVm vm2 = new ProductThumbnailVm(1L, "Product", "product", "url2");

        // Act & Assert
        assertNotEquals(vm1, vm2);
    }

    @Test
    void testProductThumbnailVm_hashCode_consistency() {
        // Arrange
        ProductThumbnailVm vm1 = new ProductThumbnailVm(1L, "Product", "product", "url");
        ProductThumbnailVm vm2 = new ProductThumbnailVm(1L, "Product", "product", "url");

        // Act & Assert
        assertEquals(vm1.hashCode(), vm2.hashCode());
    }

    @Test
    void testProductThumbnailVm_hashCode_withDifferentValues() {
        // Arrange
        ProductThumbnailVm vm1 = new ProductThumbnailVm(1L, "Product1", "product-1", "url1");
        ProductThumbnailVm vm2 = new ProductThumbnailVm(2L, "Product2", "product-2", "url2");

        // Act & Assert
        assertNotEquals(vm1.hashCode(), vm2.hashCode());
    }

    @Test
    void testProductThumbnailVm_toString_containsFields() {
        // Arrange
        ProductThumbnailVm productThumbnailVm = new ProductThumbnailVm(
            1L,
            "Test Product",
            "test-product",
            "https://example.com/image.jpg"
        );

        // Act
        String toString = productThumbnailVm.toString();

        // Assert
        assertNotNull(toString);
        assertTrue(toString.contains("1"));
        assertTrue(toString.contains("Test Product"));
        assertTrue(toString.contains("test-product"));
        assertTrue(toString.contains("https://example.com/image.jpg"));
    }

    @Test
    void testProductThumbnailVm_builder_withPartialData() {
        // Arrange & Act
        ProductThumbnailVm productThumbnailVm = ProductThumbnailVm.builder()
            .id(3L)
            .name("Partial Product")
            .build();

        // Assert
        assertNotNull(productThumbnailVm);
        assertEquals(3L, productThumbnailVm.id());
        assertEquals("Partial Product", productThumbnailVm.name());
        assertNull(productThumbnailVm.slug());
        assertNull(productThumbnailVm.thumbnailUrl());
    }

    @Test
    void testProductThumbnailVm_withEmptyStrings() {
        // Arrange & Act
        ProductThumbnailVm productThumbnailVm = new ProductThumbnailVm(1L, "", "", "");

        // Assert
        assertNotNull(productThumbnailVm);
        assertEquals(1L, productThumbnailVm.id());
        assertEquals("", productThumbnailVm.name());
        assertEquals("", productThumbnailVm.slug());
        assertEquals("", productThumbnailVm.thumbnailUrl());
    }

    @Test
    void testProductThumbnailVm_withLongStrings() {
        // Arrange
        String longName = "A".repeat(1000);
        String longSlug = "a-".repeat(500);
        String longUrl = "https://example.com/" + "image".repeat(100) + ".jpg";

        // Act
        ProductThumbnailVm productThumbnailVm = new ProductThumbnailVm(1L, longName, longSlug, longUrl);

        // Assert
        assertNotNull(productThumbnailVm);
        assertEquals(longName, productThumbnailVm.name());
        assertEquals(longSlug, productThumbnailVm.slug());
        assertEquals(longUrl, productThumbnailVm.thumbnailUrl());
    }
}
