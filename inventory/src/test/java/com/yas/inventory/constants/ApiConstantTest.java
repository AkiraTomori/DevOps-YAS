package com.yas.inventory.constants;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ApiConstantTest {

    @Test
    void testWarehouseUrl_shouldReturnCorrectValue() {
        assertEquals("/backoffice/warehouses", ApiConstant.WAREHOUSE_URL);
    }

    @Test
    void testStockHistoryUrl_shouldReturnCorrectValue() {
        assertEquals("/backoffice/stocks/histories", ApiConstant.STOCK_HISTORY_URL);
    }

    @Test
    void testStockUrl_shouldReturnCorrectValue() {
        assertEquals("/backoffice/stocks", ApiConstant.STOCK_URL);
    }

    @Test
    void testCode200_shouldReturnCorrectValue() {
        assertEquals("200", ApiConstant.CODE_200);
    }

    @Test
    void testOk_shouldReturnCorrectValue() {
        assertEquals("Ok", ApiConstant.OK);
    }

    @Test
    void testCode404_shouldReturnCorrectValue() {
        assertEquals("404", ApiConstant.CODE_404);
    }

    @Test
    void testNotFound_shouldReturnCorrectValue() {
        assertEquals("Not found", ApiConstant.NOT_FOUND);
    }

    @Test
    void testCode201_shouldReturnCorrectValue() {
        assertEquals("201", ApiConstant.CODE_201);
    }

    @Test
    void testCreated_shouldReturnCorrectValue() {
        assertEquals("Created", ApiConstant.CREATED);
    }

    @Test
    void testCode400_shouldReturnCorrectValue() {
        assertEquals("400", ApiConstant.CODE_400);
    }

    @Test
    void testBadRequest_shouldReturnCorrectValue() {
        assertEquals("Bad request", ApiConstant.BAD_REQUEST);
    }

    @Test
    void testCode204_shouldReturnCorrectValue() {
        assertEquals("204", ApiConstant.CODE_204);
    }

    @Test
    void testNoContent_shouldReturnCorrectValue() {
        assertEquals("No content", ApiConstant.NO_CONTENT);
    }

    @Test
    void testAccessDenied_shouldReturnCorrectValue() {
        assertEquals("ACCESS_DENIED", ApiConstant.ACCESS_DENIED);
    }

    @Test
    void testInvalidAdjustedQuantity_shouldReturnCorrectValue() {
        assertEquals("INVALID_ADJUSTED_QUANTITY", ApiConstant.INVALID_ADJUSTED_QUANTITY);
    }
}
