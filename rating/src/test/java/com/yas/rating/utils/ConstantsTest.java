package com.yas.rating.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConstantsTest {

    @Test
    void testErrorCode_ratingNotFound() {
        assertEquals("RATING_NOT_FOUND", Constants.ErrorCode.RATING_NOT_FOUND);
    }

    @Test
    void testErrorCode_productNotFound() {
        assertEquals("PRODUCT_NOT_FOUND", Constants.ErrorCode.PRODUCT_NOT_FOUND);
    }

    @Test
    void testErrorCode_customerNotFound() {
        assertEquals("CUSTOMER_NOT_FOUND", Constants.ErrorCode.CUSTOMER_NOT_FOUND);
    }

    @Test
    void testErrorCode_resourceAlreadyExisted() {
        assertEquals("RESOURCE_ALREADY_EXISTED", Constants.ErrorCode.RESOURCE_ALREADY_EXISTED);
    }

    @Test
    void testErrorCode_accessDenied() {
        assertEquals("ACCESS_DENIED", Constants.ErrorCode.ACCESS_DENIED);
    }

    @Test
    void testMessage_successMessage() {
        assertEquals("SUCCESS", Constants.Message.SUCCESS_MESSAGE);
    }

    @Test
    void testConstants_shouldNotBeInstantiable() {
        // Assert
        assertDoesNotThrow(() -> {
            var constructor = Constants.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        });
    }

    @Test
    void testErrorCode_shouldNotBeInstantiable() {
        // Assert
        assertThrows(Exception.class, () -> {
            var constructor = Constants.ErrorCode.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        });
    }

    @Test
    void testMessage_shouldNotBeInstantiable() {
        // Assert
        assertThrows(Exception.class, () -> {
            var constructor = Constants.Message.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        });
    }
}
