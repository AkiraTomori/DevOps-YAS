package com.yas.search.kafka.config.consumer;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.config.KafkaListenerEndpointRegistrar;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@ExtendWith(MockitoExtension.class)
class AppKafkaListenerConfigurerTest {

    @Mock
    private LocalValidatorFactoryBean validator;

    @Mock
    private KafkaListenerEndpointRegistrar registrar;

    @InjectMocks
    private AppKafkaListenerConfigurer configurer;

    @Test
    void testConfigureKafkaListeners_shouldRegisterValidator() {
        configurer.configureKafkaListeners(registrar);

        verify(registrar).setValidator(validator);
    }
}