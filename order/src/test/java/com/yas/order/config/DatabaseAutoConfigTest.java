package com.yas.order.config;

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
    void testAuditorAware_whenAuthenticationIsNull_shouldReturnSystem() {
        // Arrange
        SecurityContextHolder.clearContext();

        // Act
        AuditorAware<String> auditorAware = databaseAutoConfig.auditorAware();
        Optional<String> auditor = auditorAware.getCurrentAuditor();

        // Assert
        assertTrue(auditor.isPresent());
        assertEquals("system", auditor.get());
    }

    @Test
    void testAuditorAware_whenAuthenticationExists_shouldReturnAuthName() {
        // Arrange
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        String expectedUsername = "testUser";

        when(authentication.getName()).thenReturn(expectedUsername);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Act
        AuditorAware<String> auditorAware = databaseAutoConfig.auditorAware();
        Optional<String> auditor = auditorAware.getCurrentAuditor();

        // Assert
        assertTrue(auditor.isPresent());
        assertEquals(expectedUsername, auditor.get());

        // Cleanup
        SecurityContextHolder.clearContext();
    }

    @Test
    void testAuditorAware_beanCreation_shouldNotBeNull() {
        // Act
        AuditorAware<String> auditorAware = databaseAutoConfig.auditorAware();

        // Assert
        assertNotNull(auditorAware);
    }

    @Test
    void testAuditorAware_whenAuthenticationNameIsNull_shouldReturnSystem() {
        // Arrange
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);

        when(authentication.getName()).thenReturn(null);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Act
        AuditorAware<String> auditorAware = databaseAutoConfig.auditorAware();
        Optional<String> auditor = auditorAware.getCurrentAuditor();

        // Assert
        assertTrue(auditor.isPresent());
        assertEquals("system", auditor.get());

        // Cleanup
        SecurityContextHolder.clearContext();
    }
}
