package com.yas.rating.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessagesUtilsTest {

    @Test
    void testGetMessage_withExistingKey() {
        // Act
        String message = MessagesUtils.getMessage(Constants.ErrorCode.RATING_NOT_FOUND, "123");

        // Assert
        assertNotNull(message);
    }

    @Test
    void testGetMessage_withNonExistingKey_shouldReturnKey() {
        // Act
        String message = MessagesUtils.getMessage("NON_EXISTING_KEY");

        // Assert
        assertEquals("NON_EXISTING_KEY", message);
    }

    @Test
    void testGetMessage_withMultipleParameters() {
        // Act
        String message = MessagesUtils.getMessage("TEST_MESSAGE", "param1", "param2", "param3");

        // Assert
        assertNotNull(message);
    }

    @Test
    void testGetMessage_withoutParameters() {
        // Act
        String message = MessagesUtils.getMessage(Constants.Message.SUCCESS_MESSAGE);

        // Assert
        assertNotNull(message);
    }

    @Test
    void testGetMessage_withNullParameter() {
        // Act
        String message = MessagesUtils.getMessage(Constants.ErrorCode.ACCESS_DENIED, (Object) null);

        // Assert
        assertNotNull(message);
    }
}
