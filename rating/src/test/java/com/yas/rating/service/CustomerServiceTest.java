package com.yas.rating.service;

import com.yas.rating.config.ServiceUrlConfig;
import com.yas.rating.viewmodel.CustomerVm;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.client.RestClient;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private RestClient restClient;

    @Mock
    private ServiceUrlConfig serviceUrlConfig;

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private RestClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private RestClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private RestClient.ResponseSpec responseSpec;

    @Test
    void testGetCustomer_shouldReturnCustomerVm() {
        // Arrange
        Jwt jwt = mock(Jwt.class);
        when(jwt.getTokenValue()).thenReturn("test-token");
        
        JwtAuthenticationToken authentication = new JwtAuthenticationToken(jwt);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        
        CustomerVm expectedCustomer = new CustomerVm("user123", "user@test.com", "John", "Doe");
        
        when(serviceUrlConfig.customer()).thenReturn("http://customer-service");
        when(restClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(any(java.net.URI.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.headers(any())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.body(CustomerVm.class)).thenReturn(expectedCustomer);

        // Act
        CustomerVm result = customerService.getCustomer();

        // Assert
        assertNotNull(result);
        assertEquals("user123", result.username());
        assertEquals("user@test.com", result.email());
        assertEquals("John", result.firstName());
        assertEquals("Doe", result.lastName());
        
        SecurityContextHolder.clearContext();
    }

    @Test
    void testGetCustomer_shouldUseCorrectServiceUrl() {
        // Arrange
        Jwt jwt = mock(Jwt.class);
        when(jwt.getTokenValue()).thenReturn("test-token");
        
        JwtAuthenticationToken authentication = new JwtAuthenticationToken(jwt);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        
        CustomerVm expectedCustomer = new CustomerVm("user", "user@test.com", "Test", "User");
        
        when(serviceUrlConfig.customer()).thenReturn("http://customer-service");
        when(restClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(any(java.net.URI.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.headers(any())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.body(CustomerVm.class)).thenReturn(expectedCustomer);

        // Act
        customerService.getCustomer();

        // Assert
        verify(serviceUrlConfig).customer();
        
        SecurityContextHolder.clearContext();
    }

    @Test
    void testHandleFallback_shouldReturnNull() throws Throwable {
        // Arrange
        Throwable throwable = new RuntimeException("Service unavailable");

        // Act
        CustomerVm result = customerService.handleFallback(throwable);

        // Assert
        assertNull(result);
    }
}
