package com.yas.tax.constants;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class PageableConstantTest {

    @Test
    void testDefaultPageSize_shouldHaveCorrectValue() {
        assertEquals("10", PageableConstant.DEFAULT_PAGE_SIZE);
    }

    @Test
    void testDefaultPageNumber_shouldHaveCorrectValue() {
        assertEquals("0", PageableConstant.DEFAULT_PAGE_NUMBER);
    }

    @Test
    void testAllPageableConstantsNotNull() {
        assertNotNull(PageableConstant.DEFAULT_PAGE_SIZE);
        assertNotNull(PageableConstant.DEFAULT_PAGE_NUMBER);
    }

    @Test
    void testDefaultPageSize_shouldBeNumericString() {
        // Verify it can be parsed as an integer
        int pageSize = Integer.parseInt(PageableConstant.DEFAULT_PAGE_SIZE);
        assertEquals(10, pageSize);
    }

    @Test
    void testDefaultPageNumber_shouldBeNumericString() {
        // Verify it can be parsed as an integer
        int pageNumber = Integer.parseInt(PageableConstant.DEFAULT_PAGE_NUMBER);
        assertEquals(0, pageNumber);
    }
}
