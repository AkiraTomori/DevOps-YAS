package com.yas.cart.utils;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

class ConstantsTest {

    @Test
    void testConstants_shouldBeFinalClass() {
        // Act & Assert
        assertTrue(Modifier.isFinal(Constants.class.getModifiers()), 
            "Constants class should be final");
    }

    @Test
    void testConstants_canBeInstantiated() {
        // Act & Assert
        assertDoesNotThrow(() -> new Constants());
    }

    @Test
    void testErrorCode_notFoundProduct() {
        // Act & Assert
        assertEquals("NOT_FOUND_PRODUCT", Constants.ErrorCode.NOT_FOUND_PRODUCT);
    }

    @Test
    void testErrorCode_notExistingItemInCart() {
        // Act & Assert
        assertEquals("NOT_EXISTING_ITEM_IN_CART", Constants.ErrorCode.NOT_EXISTING_ITEM_IN_CART);
    }

    @Test
    void testErrorCode_notExistingProductInCart() {
        // Act & Assert
        assertEquals("NOT_EXISTING_PRODUCT_IN_CART", Constants.ErrorCode.NOT_EXISTING_PRODUCT_IN_CART);
    }

    @Test
    void testErrorCode_nonExistingCartItem() {
        // Act & Assert
        assertEquals("NON_EXISTING_CART_ITEM", Constants.ErrorCode.NON_EXISTING_CART_ITEM);
    }

    @Test
    void testErrorCode_addCartItemFailed() {
        // Act & Assert
        assertEquals("ADD_CART_ITEM_FAILED", Constants.ErrorCode.ADD_CART_ITEM_FAILED);
    }

    @Test
    void testErrorCode_duplicatedCartItemsToDelete() {
        // Act & Assert
        assertEquals("DUPLICATED_CART_ITEMS_TO_DELETE", Constants.ErrorCode.DUPLICATED_CART_ITEMS_TO_DELETE);
    }

    @Test
    void testErrorCode_allConstantsAreNotNull() {
        // Act & Assert
        assertNotNull(Constants.ErrorCode.NOT_FOUND_PRODUCT);
        assertNotNull(Constants.ErrorCode.NOT_EXISTING_ITEM_IN_CART);
        assertNotNull(Constants.ErrorCode.NOT_EXISTING_PRODUCT_IN_CART);
        assertNotNull(Constants.ErrorCode.NON_EXISTING_CART_ITEM);
        assertNotNull(Constants.ErrorCode.ADD_CART_ITEM_FAILED);
        assertNotNull(Constants.ErrorCode.DUPLICATED_CART_ITEMS_TO_DELETE);
    }

    @Test
    void testErrorCode_allConstantsAreNotEmpty() {
        // Act & Assert
        assertFalse(Constants.ErrorCode.NOT_FOUND_PRODUCT.isEmpty());
        assertFalse(Constants.ErrorCode.NOT_EXISTING_ITEM_IN_CART.isEmpty());
        assertFalse(Constants.ErrorCode.NOT_EXISTING_PRODUCT_IN_CART.isEmpty());
        assertFalse(Constants.ErrorCode.NON_EXISTING_CART_ITEM.isEmpty());
        assertFalse(Constants.ErrorCode.ADD_CART_ITEM_FAILED.isEmpty());
        assertFalse(Constants.ErrorCode.DUPLICATED_CART_ITEMS_TO_DELETE.isEmpty());
    }

    @Test
    void testErrorCode_constantsAreUnique() {
        // Arrange
        String[] errorCodes = {
            Constants.ErrorCode.NOT_FOUND_PRODUCT,
            Constants.ErrorCode.NOT_EXISTING_ITEM_IN_CART,
            Constants.ErrorCode.NOT_EXISTING_PRODUCT_IN_CART,
            Constants.ErrorCode.NON_EXISTING_CART_ITEM,
            Constants.ErrorCode.ADD_CART_ITEM_FAILED,
            Constants.ErrorCode.DUPLICATED_CART_ITEMS_TO_DELETE
        };

        // Act & Assert
        assertEquals(errorCodes.length, 
            java.util.Arrays.stream(errorCodes).distinct().count(),
            "All error codes should be unique");
    }
}
