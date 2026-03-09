package com.yas.tax.constants;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class MessageCodeTest {

    @Test
    void testTaxClassNotFound_shouldHaveCorrectValue() {
        assertEquals("TAX_CLASS_NOT_FOUND", MessageCode.TAX_CLASS_NOT_FOUND);
    }

    @Test
    void testNameAlreadyExited_shouldHaveCorrectValue() {
        assertEquals("NAME_ALREADY_EXITED", MessageCode.NAME_ALREADY_EXITED);
    }

    @Test
    void testTaxRateNotFound_shouldHaveCorrectValue() {
        assertEquals("TAX_RATE_NOT_FOUND", MessageCode.TAX_RATE_NOT_FOUND);
    }

    @Test
    void testAllMessageCodesNotNull() {
        assertNotNull(MessageCode.TAX_CLASS_NOT_FOUND);
        assertNotNull(MessageCode.NAME_ALREADY_EXITED);
        assertNotNull(MessageCode.TAX_RATE_NOT_FOUND);
    }
}
