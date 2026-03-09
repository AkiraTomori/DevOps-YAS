package com.yas.cart.config;

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
        assertTrue(hasAnnotation, "SwaggerConfig should have OpenAPIDefinition annotation");
    }

    @Test
    void testSwaggerConfig_shouldHaveSecuritySchemeAnnotation() {
        // Act
        boolean hasAnnotation = SwaggerConfig.class.isAnnotationPresent(SecurityScheme.class);

        // Assert
        assertTrue(hasAnnotation, "SwaggerConfig should have SecurityScheme annotation");
    }

    @Test
    void testSwaggerConfig_openAPIDefinitionHasInfo() {
        // Act
        OpenAPIDefinition openAPIDefinition = SwaggerConfig.class.getAnnotation(OpenAPIDefinition.class);

        // Assert
        assertNotNull(openAPIDefinition);
        assertNotNull(openAPIDefinition.info());
        assertEquals("Product Service API", openAPIDefinition.info().title());
        assertEquals("Product API documentation", openAPIDefinition.info().description());
        assertEquals("1.0", openAPIDefinition.info().version());
    }

    @Test
    void testSwaggerConfig_openAPIDefinitionHasSecurityRequirement() {
        // Act
        OpenAPIDefinition openAPIDefinition = SwaggerConfig.class.getAnnotation(OpenAPIDefinition.class);

        // Assert
        assertNotNull(openAPIDefinition);
        assertNotNull(openAPIDefinition.security());
        assertEquals(1, openAPIDefinition.security().length);
        assertEquals("oauth2_bearer", openAPIDefinition.security()[0].name());
    }

    @Test
    void testSwaggerConfig_securitySchemeConfiguration() {
        // Act
        SecurityScheme securityScheme = SwaggerConfig.class.getAnnotation(SecurityScheme.class);

        // Assert
        assertNotNull(securityScheme);
        assertEquals("oauth2_bearer", securityScheme.name());
        assertNotNull(securityScheme.flows());
    }

    @Test
    void testSwaggerConfig_canBeInstantiated() {
        // Act & Assert
        assertDoesNotThrow(() -> new SwaggerConfig());
    }
}
