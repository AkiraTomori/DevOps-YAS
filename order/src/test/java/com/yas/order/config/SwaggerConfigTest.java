package com.yas.order.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SwaggerConfigTest {

    @Test
    void testSwaggerConfig_shouldHaveOpenAPIDefinitionAnnotation() {
        // Act
        boolean hasAnnotation = SwaggerConfig.class.isAnnotationPresent(OpenAPIDefinition.class);

        // Assert
        assertTrue(hasAnnotation);
    }

    @Test
    void testSwaggerConfig_shouldHaveSecuritySchemeAnnotation() {
        // Act
        boolean hasAnnotation = SwaggerConfig.class.isAnnotationPresent(SecurityScheme.class);

        // Assert
        assertTrue(hasAnnotation);
    }

    @Test
    void testSwaggerConfig_openAPIDefinition_shouldHaveTitle() {
        // Arrange
        OpenAPIDefinition annotation = SwaggerConfig.class.getAnnotation(OpenAPIDefinition.class);

        // Act
        String title = annotation.info().title();

        // Assert
        assertNotNull(title);
        assertEquals("Order Service API", title);
    }

    @Test
    void testSwaggerConfig_openAPIDefinition_shouldHaveDescription() {
        // Arrange
        OpenAPIDefinition annotation = SwaggerConfig.class.getAnnotation(OpenAPIDefinition.class);

        // Act
        String description = annotation.info().description();

        // Assert
        assertNotNull(description);
        assertEquals("Order API documentation", description);
    }

    @Test
    void testSwaggerConfig_openAPIDefinition_shouldHaveVersion() {
        // Arrange
        OpenAPIDefinition annotation = SwaggerConfig.class.getAnnotation(OpenAPIDefinition.class);

        // Act
        String version = annotation.info().version();

        // Assert
        assertNotNull(version);
        assertEquals("1.0", version);
    }

    @Test
    void testSwaggerConfig_securityScheme_shouldHaveName() {
        // Arrange
        SecurityScheme annotation = SwaggerConfig.class.getAnnotation(SecurityScheme.class);

        // Act
        String name = annotation.name();

        // Assert
        assertNotNull(name);
        assertEquals("oauth2_bearer", name);
    }
}
