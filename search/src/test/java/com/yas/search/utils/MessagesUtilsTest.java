package com.yas.search.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.yas.search.constant.MessageCode;
import org.junit.jupiter.api.Test;

class MessagesUtilsTest {

    @Test
    void testGetMessage_whenMessageCodeExists_shouldFormatMessage() {
        String result = MessagesUtils.getMessage(MessageCode.PRODUCT_NOT_FOUND, 10L);

        assertEquals("The product 10 is not found", result);
    }

    @Test
    void testGetMessage_whenMessageCodeMissing_shouldReturnCode() {
        String result = MessagesUtils.getMessage("UNKNOWN_CODE");

        assertEquals("UNKNOWN_CODE", result);
    }
}