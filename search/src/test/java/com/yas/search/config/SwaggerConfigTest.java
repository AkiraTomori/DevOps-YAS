package com.yas.search.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.junit.jupiter.api.Test;

class SwaggerConfigTest {

    @Test
    void testSwaggerConfig_shouldHaveOpenApiDefinitionAnnotation() {
        assertTrue(SwaggerConfig.class.isAnnotationPresent(OpenAPIDefinition.class));
    }

    @Test
    void testSwaggerConfig_shouldHaveSecuritySchemeAnnotation() {
        assertTrue(SwaggerConfig.class.isAnnotationPresent(SecurityScheme.class));
    }

    @Test
    void testSwaggerConfig_shouldExposeApiMetadata() {
        OpenAPIDefinition annotation = SwaggerConfig.class.getAnnotation(OpenAPIDefinition.class);

        assertNotNull(annotation);
        assertEquals("Search Service API", annotation.info().title());
        assertEquals("Search API documentation", annotation.info().description());
        assertEquals("1.0", annotation.info().version());
        assertEquals("oauth2_bearer", annotation.security()[0].name());
    }

    @Test
    void testSwaggerConfig_shouldExposeSecuritySchemeName() {
        SecurityScheme annotation = SwaggerConfig.class.getAnnotation(SecurityScheme.class);

        assertNotNull(annotation);
        assertEquals("oauth2_bearer", annotation.name());
    }
}