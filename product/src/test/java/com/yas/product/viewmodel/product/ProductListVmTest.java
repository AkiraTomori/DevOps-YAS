package com.yas.product.viewmodel.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.yas.product.model.Product;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;

class ProductListVmTest {

    @Test
    void testFromModel_WithNullParent() {
        // Given
        Product product = Product.builder()
            .id(1L)
            .name("Test Product")
            .slug("test-product")
            .isAllowedToOrder(true)
            .isPublished(true)
            .isFeatured(false)
            .isVisibleIndividually(true)
            .price(99.99)
            .taxClassId(1L)
            .parent(null)
            .build();
        product.setCreatedOn(ZonedDateTime.now());

        // When
        ProductListVm result = ProductListVm.fromModel(product);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Test Product", result.name());
        assertEquals("test-product", result.slug());
        assertEquals(true, result.isAllowedToOrder());
        assertEquals(true, result.isPublished());
        assertEquals(false, result.isFeatured());
        assertEquals(true, result.isVisibleIndividually());
        assertEquals(99.99, result.price());
        assertEquals(1L, result.taxClassId());
        assertNull(result.parentId());
    }

    @Test
    void testFromModel_WithParent() {
        // Given
        Product parent = Product.builder()
            .id(2L)
            .name("Parent Product")
            .build();

        Product product = Product.builder()
            .id(1L)
            .name("Child Product")
            .slug("child-product")
            .isAllowedToOrder(true)
            .isPublished(true)
            .isFeatured(true)
            .isVisibleIndividually(false)
            .price(49.99)
            .taxClassId(2L)
            .parent(parent)
            .build();
        product.setCreatedOn(ZonedDateTime.now());

        // When
        ProductListVm result = ProductListVm.fromModel(product);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Child Product", result.name());
        assertEquals(2L, result.parentId());
    }

    @Test
    void testFromModel_WithZeroPrice() {
        // Given
        Product product = Product.builder()
            .id(1L)
            .name("Free Product")
            .slug("free-product")
            .isAllowedToOrder(true)
            .isPublished(true)
            .isFeatured(false)
            .isVisibleIndividually(true)
            .price(0.0)
            .build();
        product.setCreatedOn(ZonedDateTime.now());

        // When
        ProductListVm result = ProductListVm.fromModel(product);

        // Then
        assertNotNull(result);
        assertEquals(0.0, result.price());
    }
}
