package com.yas.inventory.constants;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PageableConstantTest {

    @Test
    void testDefaultPageSize_shouldReturnCorrectValue() {
        assertEquals("10", PageableConstant.DEFAULT_PAGE_SIZE);
    }

    @Test
    void testDefaultPageNumber_shouldReturnCorrectValue() {
        assertEquals("0", PageableConstant.DEFAULT_PAGE_NUMBER);
    }
}
