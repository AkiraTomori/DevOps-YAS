package com.yas.rating.utils;

import com.yas.commonlibrary.exception.AccessDeniedException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationUtilsTest {

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void testExtractUserId_whenJwtAuthentication_shouldReturnSubject() {
        // Arrange
        Jwt jwt = mock(Jwt.class);
        when(jwt.getSubject()).thenReturn("user-123");
        
        JwtAuthenticationToken authentication = new JwtAuthenticationToken(jwt);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Act
        String userId = AuthenticationUtils.extractUserId();

        // Assert
        assertEquals("user-123", userId);
    }

    @Test
    void testExtractUserId_whenAnonymousAuthentication_shouldThrowAccessDeniedException() {
        // Arrange
        AnonymousAuthenticationToken authentication = mock(AnonymousAuthenticationToken.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Act & Assert
        AccessDeniedException exception = assertThrows(AccessDeniedException.class, 
                AuthenticationUtils::extractUserId);
        assertEquals(Constants.ErrorCode.ACCESS_DENIED, exception.getMessage());
    }

    @Test
    void testExtractUserId_whenNoAuthentication_shouldThrowNullPointerException() {
        // Arrange
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(null);
        SecurityContextHolder.setContext(securityContext);

        // Act & Assert
        assertThrows(NullPointerException.class, AuthenticationUtils::extractUserId);
    }

    @Test
    void testExtractUserId_withDifferentUserIds() {
        // Arrange
        Jwt jwt = mock(Jwt.class);
        when(jwt.getSubject()).thenReturn("different-user-456");
        
        JwtAuthenticationToken authentication = new JwtAuthenticationToken(jwt);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Act
        String userId = AuthenticationUtils.extractUserId();

        // Assert
        assertEquals("different-user-456", userId);
    }
}
