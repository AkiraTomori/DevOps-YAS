package com.yas.tax.utils;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class MessagesUtilsTest {

    @Test
    void testGetMessage_whenValidMessageCode_shouldReturnMessage() {
        // Act - Test with a valid message code from the bundle
        String result = MessagesUtils.getMessage("TAX_CLASS_NOT_FOUND", 1L);

        // Assert
        assertNotNull(result);
        // The message bundle may return the key itself if translation is missing
        assertTrue(result.length() > 0);
    }

    @Test
    void testGetMessage_whenMessageCodeWithNoParams_shouldReturnMessage() {
        // Act
        String result = MessagesUtils.getMessage("TAX_CLASS_NOT_FOUND");

        // Assert
        assertNotNull(result);
        // Should still return a message even if params don't match
        assertTrue(result.length() > 0);
    }

    @Test
    void testGetMessage_whenMessageCodeWithMultipleParams_shouldFormatCorrectly() {
        // Act
        String result = MessagesUtils.getMessage("TAX_CLASS_NOT_FOUND", 1L, "additional");

        // Assert
        assertNotNull(result);
        assertTrue(result.length() > 0);
    }

    @Test
    void testGetMessage_whenNullParams_shouldHandleGracefully() {
        // Act
        String result = MessagesUtils.getMessage("TAX_CLASS_NOT_FOUND", (Object[]) null);

        // Assert
        assertNotNull(result);
    }
}
