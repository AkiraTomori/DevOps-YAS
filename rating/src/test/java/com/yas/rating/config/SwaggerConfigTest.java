package com.yas.rating.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SwaggerConfigTest {

    @Test
    void testSwaggerConfig_shouldHaveOpenAPIDefinitionAnnotation() {
        // Act
        OpenAPIDefinition annotation = SwaggerConfig.class.getAnnotation(OpenAPIDefinition.class);

        // Assert
        assertNotNull(annotation);
        assertEquals("Rating Service API", annotation.info().title());
        assertEquals("Rating API documentation", annotation.info().description());
        assertEquals("1.0", annotation.info().version());
    }

    @Test
    void testSwaggerConfig_shouldHaveSecuritySchemeAnnotation() {
        // Act
        SecurityScheme annotation = SwaggerConfig.class.getAnnotation(SecurityScheme.class);

        // Assert
        assertNotNull(annotation);
        assertEquals("oauth2_bearer", annotation.name());
    }

    @Test
    void testSwaggerConfig_shouldHaveSecurityRequirement() {
        // Act
        OpenAPIDefinition annotation = SwaggerConfig.class.getAnnotation(OpenAPIDefinition.class);

        // Assert
        assertNotNull(annotation.security());
        assertEquals(1, annotation.security().length);
        assertEquals("oauth2_bearer", annotation.security()[0].name());
    }

    @Test
    void testSwaggerConfig_shouldBeInstantiable() {
        // Act & Assert
        assertDoesNotThrow(() -> new SwaggerConfig());
    }
}
