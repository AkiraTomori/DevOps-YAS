package com.yas.inventory.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.yas.commonlibrary.exception.AccessDeniedException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collections;

class AuthenticationUtilsTest {

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void testExtractUserId_whenAuthenticatedUser_shouldReturnUserId() {
        // Arrange
        SecurityContext securityContext = mock(SecurityContext.class);
        Jwt jwt = mock(Jwt.class);
        JwtAuthenticationToken authentication = new JwtAuthenticationToken(jwt, 
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

        when(jwt.getSubject()).thenReturn("user123");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Act
        String userId = AuthenticationUtils.extractUserId();

        // Assert
        assertEquals("user123", userId);
    }

    @Test
    void testExtractUserId_whenAnonymousUser_shouldThrowAccessDeniedException() {
        // Arrange
        SecurityContext securityContext = mock(SecurityContext.class);
        AnonymousAuthenticationToken authentication = new AnonymousAuthenticationToken(
            "anonymous", "anonymousUser", 
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_ANONYMOUS")));

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Act & Assert
        assertThrows(AccessDeniedException.class, () -> AuthenticationUtils.extractUserId());
    }

    @Test
    void testExtractJwt_whenAuthenticatedUser_shouldReturnTokenValue() {
        // Arrange
        SecurityContext securityContext = mock(SecurityContext.class);
        Jwt jwt = mock(Jwt.class);
        JwtAuthenticationToken authentication = new JwtAuthenticationToken(jwt, 
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

        when(jwt.getTokenValue()).thenReturn("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Act
        String token = AuthenticationUtils.extractJwt();

        // Assert
        assertEquals("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9", token);
    }

    @Test
    void testExtractUserId_whenDifferentUserId_shouldReturnCorrectUserId() {
        // Arrange
        SecurityContext securityContext = mock(SecurityContext.class);
        Jwt jwt = mock(Jwt.class);
        JwtAuthenticationToken authentication = new JwtAuthenticationToken(jwt, 
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));

        when(jwt.getSubject()).thenReturn("admin456");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Act
        String userId = AuthenticationUtils.extractUserId();

        // Assert
        assertEquals("admin456", userId);
    }

    @Test
    void testExtractJwt_whenDifferentToken_shouldReturnCorrectToken() {
        // Arrange
        SecurityContext securityContext = mock(SecurityContext.class);
        Jwt jwt = mock(Jwt.class);
        JwtAuthenticationToken authentication = new JwtAuthenticationToken(jwt, 
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));

        when(jwt.getTokenValue()).thenReturn("differentTokenValue123");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Act
        String token = AuthenticationUtils.extractJwt();

        // Assert
        assertEquals("differentTokenValue123", token);
    }
}
