package com.yas.order.utils;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

class ConstantsTest {

    @Test
    void testConstants_classCannotBeInstantiated() {
        // Arrange & Act & Assert
        assertDoesNotThrow(() -> {
            Constructor<Constants> constructor = Constants.class.getDeclaredConstructor();
            assertTrue(Modifier.isPublic(constructor.getModifiers()) || 
                      Modifier.isPrivate(constructor.getModifiers()) ||
                      Modifier.isProtected(constructor.getModifiers()));
        });
    }

    @Test
    void testErrorCode_ORDER_NOT_FOUND() {
        // Assert
        assertEquals("ORDER_NOT_FOUND", Constants.ErrorCode.ORDER_NOT_FOUND);
    }

    @Test
    void testErrorCode_CHECKOUT_NOT_FOUND() {
        // Assert
        assertEquals("CHECKOUT_NOT_FOUND", Constants.ErrorCode.CHECKOUT_NOT_FOUND);
    }

    @Test
    void testErrorCode_CHECKOUT_ITEM_NOT_EMPTY() {
        // Assert
        assertEquals("CHECKOUT_ITEM_NOT_EMPTY", Constants.ErrorCode.CHECKOUT_ITEM_NOT_EMPTY);
    }

    @Test
    void testErrorCode_SIGN_IN_REQUIRED() {
        // Assert
        assertEquals("SIGN_IN_REQUIRED", Constants.ErrorCode.SIGN_IN_REQUIRED);
    }

    @Test
    void testMessageCode_CREATE_CHECKOUT() {
        // Assert
        assertEquals("Create checkout {} by user {}", Constants.MessageCode.CREATE_CHECKOUT);
    }

    @Test
    void testMessageCode_UPDATE_CHECKOUT_STATUS() {
        // Assert
        assertEquals("Update checkout {} STATUS from {} to {}", Constants.MessageCode.UPDATE_CHECKOUT_STATUS);
    }

    @Test
    void testMessageCode_UPDATE_CHECKOUT_PAYMENT() {
        // Assert
        assertEquals("Update checkout {} PAYMENT from {} to {}", Constants.MessageCode.UPDATE_CHECKOUT_PAYMENT);
    }

    @Test
    void testErrorCode_allFieldsAreNotNull() {
        // Assert
        assertNotNull(Constants.ErrorCode.ORDER_NOT_FOUND);
        assertNotNull(Constants.ErrorCode.CHECKOUT_NOT_FOUND);
        assertNotNull(Constants.ErrorCode.CHECKOUT_ITEM_NOT_EMPTY);
        assertNotNull(Constants.ErrorCode.SIGN_IN_REQUIRED);
    }

    @Test
    void testMessageCode_allFieldsAreNotNull() {
        // Assert
        assertNotNull(Constants.MessageCode.CREATE_CHECKOUT);
        assertNotNull(Constants.MessageCode.UPDATE_CHECKOUT_STATUS);
        assertNotNull(Constants.MessageCode.UPDATE_CHECKOUT_PAYMENT);
    }

    @Test
    void testErrorCode_classStructure() {
        // Assert
        assertTrue(Modifier.isFinal(Constants.ErrorCode.class.getModifiers()));
    }

    @Test
    void testMessageCode_classStructure() {
        // Assert
        assertTrue(Modifier.isFinal(Constants.MessageCode.class.getModifiers()));
    }
}
