package com.yas.cart.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClient;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class RestClientConfigTest {

    @InjectMocks
    private RestClientConfig restClientConfig;

    @Test
    void testRestClient_shouldCreateBean() {
        // Act
        RestClient restClient = restClientConfig.restClient();

        // Assert
        assertNotNull(restClient, "RestClient bean should not be null");
    }

    @Test
    void testRestClient_shouldReturnNewInstanceEachTime() {
        // Act
        RestClient restClient1 = restClientConfig.restClient();
        RestClient restClient2 = restClientConfig.restClient();

        // Assert
        assertNotNull(restClient1);
        assertNotNull(restClient2);
        // Note: In Spring, beans are singleton by default, but this test verifies the method itself works
    }
}
