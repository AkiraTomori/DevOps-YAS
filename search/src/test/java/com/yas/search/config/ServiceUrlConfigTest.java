package com.yas.search.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ServiceUrlConfigTest {

    @Test
    void testServiceUrlConfig_shouldCreateWithProductUrl() {
        String product = "http://product-service";

        ServiceUrlConfig config = new ServiceUrlConfig(product);

        assertNotNull(config);
        assertEquals(product, config.product());
    }

    @Test
    void testServiceUrlConfig_shouldSupportEqualityAndHashCode() {
        ServiceUrlConfig config1 = new ServiceUrlConfig("http://product-service");
        ServiceUrlConfig config2 = new ServiceUrlConfig("http://product-service");
        ServiceUrlConfig config3 = new ServiceUrlConfig("http://another-service");

        assertEquals(config1, config2);
        assertEquals(config1.hashCode(), config2.hashCode());
        assertNotEquals(config1, config3);
    }

    @Test
    void testServiceUrlConfig_shouldIncludeProductInToString() {
        ServiceUrlConfig config = new ServiceUrlConfig("http://product-service");

        String result = config.toString();

        assertNotNull(result);
        assertTrue(result.contains("http://product-service"));
    }

    @Test
    void testServiceUrlConfig_shouldSupportNullValue() {
        ServiceUrlConfig config = new ServiceUrlConfig(null);

        assertNotNull(config);
        assertNull(config.product());
    }
}