package com.yas.inventory.constants;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MessageCodeTest {

    @Test
    void testWarehouseNotFound_shouldReturnCorrectValue() {
        assertEquals("WAREHOUSE_NOT_FOUND", MessageCode.WAREHOUSE_NOT_FOUND);
    }

    @Test
    void testProductNotFound_shouldReturnCorrectValue() {
        assertEquals("PRODUCT_NOT_FOUND", MessageCode.PRODUCT_NOT_FOUND);
    }

    @Test
    void testNameAlreadyExited_shouldReturnCorrectValue() {
        assertEquals("NAME_ALREADY_EXITED", MessageCode.NAME_ALREADY_EXITED);
    }

    @Test
    void testStockAlreadyExisted_shouldReturnCorrectValue() {
        assertEquals("STOCK_ALREADY_EXISTED", MessageCode.STOCK_ALREADY_EXISTED);
    }
}
