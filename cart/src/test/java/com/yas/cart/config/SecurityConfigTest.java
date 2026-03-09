package com.yas.cart.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SecurityConfigTest {

    @InjectMocks
    private SecurityConfig securityConfig;

    @Test
    void testFilterChain_shouldCreateSecurityFilterChain() throws Exception {
        // Arrange
        HttpSecurity http = mock(HttpSecurity.class, RETURNS_DEEP_STUBS);

        // Act
        SecurityFilterChain filterChain = securityConfig.filterChain(http);

        // Assert
        assertNotNull(filterChain);
    }

    @Test
    void testJwtAuthenticationConverter_shouldNotBeNull() {
        // Act
        JwtAuthenticationConverter converter = securityConfig.jwtAuthenticationConverterForKeycloak();

        // Assert
        assertNotNull(converter);
    }

    @Test
    void testJwtAuthenticationConverter_shouldConvertRolesToGrantedAuthorities() {
        // Arrange
        JwtAuthenticationConverter converter = securityConfig.jwtAuthenticationConverterForKeycloak();
        Jwt jwt = mock(Jwt.class, RETURNS_DEEP_STUBS);
        
        Map<String, Collection<String>> realmAccess = new HashMap<>();
        List<String> roles = Arrays.asList("ADMIN", "CUSTOMER");
        realmAccess.put("roles", roles);
        
        lenient().when(jwt.getClaim("realm_access")).thenReturn(realmAccess);

        // Act
        var authentication = converter.convert(jwt);

        // Assert
        assertNotNull(authentication);
        assertEquals(2, authentication.getAuthorities().size());
        assertTrue(authentication.getAuthorities().stream()
            .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")));
        assertTrue(authentication.getAuthorities().stream()
            .anyMatch(auth -> auth.getAuthority().equals("ROLE_CUSTOMER")));
    }

    @Test
    void testJwtAuthenticationConverter_withEmptyRoles_shouldReturnEmptyCollection() {
        // Arrange
        JwtAuthenticationConverter converter = securityConfig.jwtAuthenticationConverterForKeycloak();
        Jwt jwt = mock(Jwt.class, RETURNS_DEEP_STUBS);
        
        Map<String, Collection<String>> realmAccess = new HashMap<>();
        realmAccess.put("roles", Collections.emptyList());
        
        lenient().when(jwt.getClaim("realm_access")).thenReturn(realmAccess);

        // Act
        var authentication = converter.convert(jwt);

        // Assert
        assertNotNull(authentication);
        assertTrue(authentication.getAuthorities().isEmpty());
    }

    @Test
    void testJwtAuthenticationConverter_withMultipleRoles_shouldAddRolePrefix() {
        // Arrange
        JwtAuthenticationConverter converter = securityConfig.jwtAuthenticationConverterForKeycloak();
        Jwt jwt = mock(Jwt.class, RETURNS_DEEP_STUBS);
        
        Map<String, Collection<String>> realmAccess = new HashMap<>();
        List<String> roles = Arrays.asList("USER", "MODERATOR", "ADMIN");
        realmAccess.put("roles", roles);
        
        lenient().when(jwt.getClaim("realm_access")).thenReturn(realmAccess);

        // Act
        var authentication = converter.convert(jwt);

        // Assert
        assertNotNull(authentication);
        assertEquals(3, authentication.getAuthorities().size());
        authentication.getAuthorities().forEach(authority -> 
            assertTrue(authority.getAuthority().startsWith("ROLE_"))
        );
    }
}
