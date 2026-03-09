package com.yas.rating.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServiceUrlConfigTest {

    @Test
    void testServiceUrlConfig_shouldCreateRecordWithAllFields() {
        // Act
        ServiceUrlConfig config = new ServiceUrlConfig(
                "http://product-service",
                "http://customer-service",
                "http://order-service"
        );

        // Assert
        assertNotNull(config);
        assertEquals("http://product-service", config.product());
        assertEquals("http://customer-service", config.customer());
        assertEquals("http://order-service", config.order());
    }

    @Test
    void testServiceUrlConfig_shouldHandleNullValues() {
        // Act
        ServiceUrlConfig config = new ServiceUrlConfig(null, null, null);

        // Assert
        assertNotNull(config);
        assertNull(config.product());
        assertNull(config.customer());
        assertNull(config.order());
    }

    @Test
    void testServiceUrlConfig_shouldSupportRecordEquality() {
        // Arrange
        ServiceUrlConfig config1 = new ServiceUrlConfig("http://product", "http://customer", "http://order");
        ServiceUrlConfig config2 = new ServiceUrlConfig("http://product", "http://customer", "http://order");

        // Assert
        assertEquals(config1, config2);
    }

    @Test
    void testServiceUrlConfig_shouldSupportRecordToString() {
        // Arrange
        ServiceUrlConfig config = new ServiceUrlConfig("http://product", "http://customer", "http://order");

        // Act
        String result = config.toString();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("http://product"));
        assertTrue(result.contains("http://customer"));
        assertTrue(result.contains("http://order"));
    }
}
