package com.yas.search.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@ExtendWith(MockitoExtension.class)
class SecurityConfigTest {

    @InjectMocks
    private SecurityConfig securityConfig;

    @Test
    void testFilterChain_shouldCreateSecurityFilterChain() throws Exception {
        HttpSecurity http = mock(HttpSecurity.class, RETURNS_DEEP_STUBS);

        SecurityFilterChain filterChain = securityConfig.filterChain(http);

        assertNotNull(filterChain);
    }

    @Test
    void testJwtAuthenticationConverter_shouldNotBeNull() {
        JwtAuthenticationConverter converter = securityConfig.jwtAuthenticationConverterForKeycloak();

        assertNotNull(converter);
    }

    @Test
    void testJwtAuthenticationConverter_shouldConvertRolesToAuthorities() {
        JwtAuthenticationConverter converter = securityConfig.jwtAuthenticationConverterForKeycloak();
        Jwt jwt = mock(Jwt.class, RETURNS_DEEP_STUBS);

        Map<String, Collection<String>> realmAccess = new HashMap<>();
        realmAccess.put("roles", Arrays.asList("ADMIN", "CUSTOMER"));
        lenient().when(jwt.getClaim("realm_access")).thenReturn(realmAccess);

        var authentication = converter.convert(jwt);

        assertNotNull(authentication);
        assertEquals(2, authentication.getAuthorities().size());
        assertTrue(authentication.getAuthorities().stream()
            .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN")));
        assertTrue(authentication.getAuthorities().stream()
            .anyMatch(authority -> authority.getAuthority().equals("ROLE_CUSTOMER")));
    }

    @Test
    void testJwtAuthenticationConverter_whenRolesEmpty_shouldReturnNoAuthorities() {
        JwtAuthenticationConverter converter = securityConfig.jwtAuthenticationConverterForKeycloak();
        Jwt jwt = mock(Jwt.class, RETURNS_DEEP_STUBS);

        Map<String, Collection<String>> realmAccess = new HashMap<>();
        realmAccess.put("roles", Collections.emptyList());
        lenient().when(jwt.getClaim("realm_access")).thenReturn(realmAccess);

        var authentication = converter.convert(jwt);

        assertNotNull(authentication);
        assertTrue(authentication.getAuthorities().isEmpty());
    }

    @Test
    void testJwtAuthenticationConverter_shouldPrefixAllRoles() {
        JwtAuthenticationConverter converter = securityConfig.jwtAuthenticationConverterForKeycloak();
        Jwt jwt = mock(Jwt.class, RETURNS_DEEP_STUBS);

        Map<String, Collection<String>> realmAccess = new HashMap<>();
        List<String> roles = Arrays.asList("USER", "MODERATOR", "ADMIN");
        realmAccess.put("roles", roles);
        lenient().when(jwt.getClaim("realm_access")).thenReturn(realmAccess);

        var authentication = converter.convert(jwt);

        assertNotNull(authentication);
        assertEquals(3, authentication.getAuthorities().size());
        authentication.getAuthorities().forEach(authority ->
            assertTrue(authority.getAuthority().startsWith("ROLE_")));
    }
}