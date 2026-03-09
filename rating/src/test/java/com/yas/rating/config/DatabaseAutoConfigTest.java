package com.yas.rating.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DatabaseAutoConfigTest {

    @InjectMocks
    private DatabaseAutoConfig databaseAutoConfig;

    @Test
    void testAuditorAware_whenAuthenticationExists_shouldReturnUsername() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testUser");
        SecurityContextHolder.setContext(securityContext);

        // Act
        AuditorAware<String> auditorAware = databaseAutoConfig.auditorAware();
        Optional<String> currentAuditor = auditorAware.getCurrentAuditor();

        // Assert
        assertTrue(currentAuditor.isPresent());
        assertEquals("testUser", currentAuditor.get());

        // Cleanup
        SecurityContextHolder.clearContext();
    }

    @Test
    void testAuditorAware_whenAuthenticationIsNull_shouldReturnSystem() {
        // Arrange
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(null);
        SecurityContextHolder.setContext(securityContext);

        // Act
        AuditorAware<String> auditorAware = databaseAutoConfig.auditorAware();
        Optional<String> currentAuditor = auditorAware.getCurrentAuditor();

        // Assert
        assertTrue(currentAuditor.isPresent());
        assertEquals("system", currentAuditor.get());

        // Cleanup
        SecurityContextHolder.clearContext();
    }

    @Test
    void testAuditorAware_whenNoSecurityContext_shouldReturnSystem() {
        // Arrange
        SecurityContextHolder.clearContext();

        // Act
        AuditorAware<String> auditorAware = databaseAutoConfig.auditorAware();
        Optional<String> currentAuditor = auditorAware.getCurrentAuditor();

        // Assert
        assertTrue(currentAuditor.isPresent());
        assertEquals("system", currentAuditor.get());
    }

    @Test
    void testAuditorAware_shouldReturnBean() {
        // Act
        AuditorAware<String> auditorAware = databaseAutoConfig.auditorAware();

        // Assert
        assertNotNull(auditorAware);
    }
}
