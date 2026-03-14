package com.yas.search.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ElasticsearchDataConfigTest {

    @Test
    void testElasticsearchDataConfig_shouldStoreConfiguredValues() {
        ElasticsearchDataConfig config = new ElasticsearchDataConfig();

        config.setUrl("localhost:9200");
        config.setUsername("elastic");
        config.setPassword("secret");

        assertEquals("localhost:9200", config.getUrl());
        assertEquals("elastic", config.getUsername());
        assertEquals("secret", config.getPassword());
    }

    @Test
    void testElasticsearchDataConfig_toStringShouldContainConfiguredValues() {
        ElasticsearchDataConfig config = new ElasticsearchDataConfig();
        config.setUrl("localhost:9200");
        config.setUsername("elastic");

        String result = config.toString();

        assertNotNull(result);
        assertTrue(result.contains("localhost:9200"));
        assertTrue(result.contains("elastic"));
    }
}