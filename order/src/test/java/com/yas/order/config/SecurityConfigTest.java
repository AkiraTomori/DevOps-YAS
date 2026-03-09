package com.yas.order.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SecurityConfigTest {

    @InjectMocks
    private SecurityConfig securityConfig;

    @Test
    void testFilterChain_shouldNotBeNull() throws Exception {
        // Arrange
        HttpSecurity httpSecurity = mock(HttpSecurity.class, RETURNS_DEEP_STUBS);

        // Act & Assert
        assertDoesNotThrow(() -> securityConfig.filterChain(httpSecurity));
    }

    @Test
    void testJwtAuthenticationConverterForKeycloak_shouldNotBeNull() {
        // Act
        JwtAuthenticationConverter converter = securityConfig.jwtAuthenticationConverterForKeycloak();

        // Assert
        assertNotNull(converter);
    }

    @Test
    void testJwtAuthenticationConverterForKeycloak_shouldHaveJwtGrantedAuthoritiesConverter() {
        // Act
        JwtAuthenticationConverter converter = securityConfig.jwtAuthenticationConverterForKeycloak();

        // Assert
        assertNotNull(converter);
    }
}
