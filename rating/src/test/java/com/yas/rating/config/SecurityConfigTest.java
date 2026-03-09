package com.yas.rating.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SecurityConfigTest {

    @InjectMocks
    private SecurityConfig securityConfig;

    @Test
    void testFilterChain_shouldReturnSecurityFilterChain() throws Exception {
        // Arrange
        HttpSecurity httpSecurity = mock(HttpSecurity.class, RETURNS_DEEP_STUBS);

        // Act
        SecurityFilterChain filterChain = securityConfig.filterChain(httpSecurity);

        // Assert
        assertNotNull(filterChain);
    }

    @Test
    void testJwtAuthenticationConverterForKeycloak_shouldReturnConverter() {
        // Act
        JwtAuthenticationConverter converter = securityConfig.jwtAuthenticationConverterForKeycloak();

        // Assert
        assertNotNull(converter);
    }
}
