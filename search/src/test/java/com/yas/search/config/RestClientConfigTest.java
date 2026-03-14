package com.yas.search.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@ExtendWith(MockitoExtension.class)
class RestClientConfigTest {

    @InjectMocks
    private RestClientConfig restClientConfig;

    @Mock
    private RestClient.Builder restClientBuilder;

    @Test
    void testGetRestClient_shouldConfigureDefaultHeaders() {
        RestClient mockRestClient = mock(RestClient.class);
        when(restClientBuilder.defaultHeader(anyString(), anyString())).thenReturn(restClientBuilder);
        when(restClientBuilder.build()).thenReturn(mockRestClient);

        RestClient result = restClientConfig.getRestClient(restClientBuilder);

        assertNotNull(result);
        verify(restClientBuilder).defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        verify(restClientBuilder).build();
    }

    @Test
    void testGetRestClient_shouldReturnRestClient() {
        RestClient mockRestClient = mock(RestClient.class);
        when(restClientBuilder.defaultHeader(anyString(), anyString())).thenReturn(restClientBuilder);
        when(restClientBuilder.build()).thenReturn(mockRestClient);

        RestClient result = restClientConfig.getRestClient(restClientBuilder);

        assertSame(mockRestClient, result);
    }
}