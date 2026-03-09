package com.yas.tax.constants;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class ApiConstantTest {

    @Test
    void testTaxClassUrl_shouldHaveCorrectValue() {
        assertEquals("/backoffice/tax-classes", ApiConstant.TAX_CLASS_URL);
    }

    @Test
    void testTaxRateUrl_shouldHaveCorrectValue() {
        assertEquals("/backoffice/tax-rates", ApiConstant.TAX_RATE_URL);
    }

    @Test
    void testOk_shouldHaveCorrectValue() {
        assertEquals("Ok", ApiConstant.OK);
    }

    @Test
    void testCreated_shouldHaveCorrectValue() {
        assertEquals("Created", ApiConstant.CREATED);
    }

    @Test
    void testNoContent_shouldHaveCorrectValue() {
        assertEquals("No content", ApiConstant.NO_CONTENT);
    }

    @Test
    void testAllConstantsNotNull() {
        assertNotNull(ApiConstant.TAX_CLASS_URL);
        assertNotNull(ApiConstant.TAX_RATE_URL);
        assertNotNull(ApiConstant.OK);
        assertNotNull(ApiConstant.CREATED);
        assertNotNull(ApiConstant.NO_CONTENT);
    }

    @Test
    void testTaxClassUrlFormat_shouldStartWithSlash() {
        assertEquals('/', ApiConstant.TAX_CLASS_URL.charAt(0));
    }

    @Test
    void testTaxRateUrlFormat_shouldStartWithSlash() {
        assertEquals('/', ApiConstant.TAX_RATE_URL.charAt(0));
    }
}
