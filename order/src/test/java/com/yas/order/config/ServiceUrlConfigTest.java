package com.yas.order.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServiceUrlConfigTest {

    @Test
    void testServiceUrlConfig_shouldCreateWithAllFields() {
        // Arrange
        String cart = "http://cart-service";
        String customer = "http://customer-service";
        String product = "http://product-service";
        String tax = "http://tax-service";
        String promotion = "http://promotion-service";

        // Act
        ServiceUrlConfig config = new ServiceUrlConfig(cart, customer, product, tax, promotion);

        // Assert
        assertNotNull(config);
        assertEquals(cart, config.cart());
        assertEquals(customer, config.customer());
        assertEquals(product, config.product());
        assertEquals(tax, config.tax());
        assertEquals(promotion, config.promotion());
    }

    @Test
    void testServiceUrlConfig_equalsAndHashCode() {
        // Arrange
        ServiceUrlConfig config1 = new ServiceUrlConfig("cart", "customer", "product", "tax", "promotion");
        ServiceUrlConfig config2 = new ServiceUrlConfig("cart", "customer", "product", "tax", "promotion");
        ServiceUrlConfig config3 = new ServiceUrlConfig("different", "customer", "product", "tax", "promotion");

        // Assert
        assertEquals(config1, config2);
        assertNotEquals(config1, config3);
        assertEquals(config1.hashCode(), config2.hashCode());
    }

    @Test
    void testServiceUrlConfig_toString() {
        // Arrange
        ServiceUrlConfig config = new ServiceUrlConfig("cart", "customer", "product", "tax", "promotion");

        // Act
        String result = config.toString();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("cart"));
        assertTrue(result.contains("customer"));
        assertTrue(result.contains("product"));
        assertTrue(result.contains("tax"));
        assertTrue(result.contains("promotion"));
    }

    @Test
    void testServiceUrlConfig_withNullValues() {
        // Act
        ServiceUrlConfig config = new ServiceUrlConfig(null, null, null, null, null);

        // Assert
        assertNotNull(config);
        assertNull(config.cart());
        assertNull(config.customer());
        assertNull(config.product());
        assertNull(config.tax());
        assertNull(config.promotion());
    }

    @Test
    void testServiceUrlConfig_cart() {
        // Arrange
        ServiceUrlConfig config = new ServiceUrlConfig("http://cart", "customer", "product", "tax", "promotion");

        // Act & Assert
        assertEquals("http://cart", config.cart());
    }

    @Test
    void testServiceUrlConfig_customer() {
        // Arrange
        ServiceUrlConfig config = new ServiceUrlConfig("cart", "http://customer", "product", "tax", "promotion");

        // Act & Assert
        assertEquals("http://customer", config.customer());
    }

    @Test
    void testServiceUrlConfig_product() {
        // Arrange
        ServiceUrlConfig config = new ServiceUrlConfig("cart", "customer", "http://product", "tax", "promotion");

        // Act & Assert
        assertEquals("http://product", config.product());
    }

    @Test
    void testServiceUrlConfig_tax() {
        // Arrange
        ServiceUrlConfig config = new ServiceUrlConfig("cart", "customer", "product", "http://tax", "promotion");

        // Act & Assert
        assertEquals("http://tax", config.tax());
    }

    @Test
    void testServiceUrlConfig_promotion() {
        // Arrange
        ServiceUrlConfig config = new ServiceUrlConfig("cart", "customer", "product", "tax", "http://promotion");

        // Act & Assert
        assertEquals("http://promotion", config.promotion());
    }
}
