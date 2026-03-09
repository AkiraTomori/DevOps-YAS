package com.yas.rating.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestClientConfigTest {

    @InjectMocks
    private RestClientConfig restClientConfig;

    @Mock
    private RestClient.Builder restClientBuilder;

    @Test
    void testGetRestClient_shouldReturnConfiguredRestClient() {
        // Arrange
        RestClient mockRestClient = mock(RestClient.class);
        when(restClientBuilder.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .thenReturn(restClientBuilder);
        when(restClientBuilder.build()).thenReturn(mockRestClient);

        // Act
        RestClient restClient = restClientConfig.getRestClient(restClientBuilder);

        // Assert
        assertNotNull(restClient);
        verify(restClientBuilder).defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        verify(restClientBuilder).build();
    }

    @Test
    void testGetRestClient_shouldSetContentTypeHeader() {
        // Arrange
        RestClient mockRestClient = mock(RestClient.class);
        when(restClientBuilder.defaultHeader(anyString(), anyString())).thenReturn(restClientBuilder);
        when(restClientBuilder.build()).thenReturn(mockRestClient);

        // Act
        restClientConfig.getRestClient(restClientBuilder);

        // Assert
        verify(restClientBuilder).defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    }

    @Test
    void testGetRestClient_shouldBuildRestClient() {
        // Arrange
        RestClient mockRestClient = mock(RestClient.class);
        when(restClientBuilder.defaultHeader(anyString(), anyString())).thenReturn(restClientBuilder);
        when(restClientBuilder.build()).thenReturn(mockRestClient);

        // Act
        restClientConfig.getRestClient(restClientBuilder);

        // Assert
        verify(restClientBuilder).build();
    }
}
