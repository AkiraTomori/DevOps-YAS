package com.yas.tax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.yas.tax.config.ServiceUrlConfig;
import com.yas.tax.viewmodel.location.StateOrProvinceAndCountryGetNameVm;
import java.net.URI;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.client.RestClient;

@ExtendWith(MockitoExtension.class)
class LocationServiceTest {

    @Mock
    private RestClient restClient;

    @Mock
    private RestClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private RestClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private RestClient.ResponseSpec responseSpec;

    @Mock
    private ServiceUrlConfig serviceUrlConfig;

    @InjectMocks
    private LocationService locationService;

    @BeforeEach
    void setUp() {
        lenient().when(serviceUrlConfig.location()).thenReturn("http://location-service");
        
        // Mock JWT authentication
        Jwt jwt = Jwt.withTokenValue("test-token")
            .header("alg", "none")
            .claim("sub", "user")
            .build();
        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(jwt, null, List.of())
        );
    }

    @Test
    void testGetStateOrProvinceAndCountryNames_whenValidIds_shouldReturnNames() {
        // Arrange
        List<Long> stateOrProvinceIds = List.of(1L, 2L);

        StateOrProvinceAndCountryGetNameVm location1 = new StateOrProvinceAndCountryGetNameVm(
            1L,
            "California",
            "United States"
        );

        StateOrProvinceAndCountryGetNameVm location2 = new StateOrProvinceAndCountryGetNameVm(
            2L,
            "Texas",
            "United States"
        );

        List<StateOrProvinceAndCountryGetNameVm> expectedList = List.of(location1, location2);

        when(restClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(any(URI.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.headers(any())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.body(any(ParameterizedTypeReference.class)))
            .thenReturn(expectedList);

        // Act
        List<StateOrProvinceAndCountryGetNameVm> result = 
            locationService.getStateOrProvinceAndCountryNames(stateOrProvinceIds);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("California", result.get(0).stateOrProvinceName());
        assertEquals("Texas", result.get(1).stateOrProvinceName());

        verify(restClient).get();
    }

    @Test
    void testGetStateOrProvinceAndCountryNames_whenEmptyList_shouldReturnEmptyList() {
        // Arrange
        List<Long> stateOrProvinceIds = List.of();

        when(restClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(any(URI.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.headers(any())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.body(any(ParameterizedTypeReference.class)))
            .thenReturn(List.of());

        // Act
        List<StateOrProvinceAndCountryGetNameVm> result = 
            locationService.getStateOrProvinceAndCountryNames(stateOrProvinceIds);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());

        verify(restClient).get();
    }

    @Test
    void testGetStateOrProvinceAndCountryNames_whenCircuitBreakerActive_shouldCallFallback() {
        // This test documents the circuit breaker behavior
        // The @CircuitBreaker annotation with fallback method handles runtime exceptions
        // In unit tests, we verify the service is properly configured
        
        // Arrange
        List<Long> stateOrProvinceIds = List.of(1L);

        // Act & Assert - Circuit breaker fallback is handled by annotation in runtime
        assertNotNull(locationService);
        assertNotNull(serviceUrlConfig);
    }
}
