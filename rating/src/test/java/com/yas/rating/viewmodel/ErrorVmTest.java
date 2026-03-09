package com.yas.rating.viewmodel;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class ErrorVmTest {

    @Test
    void testErrorVm_shouldCreateWithAllFields() {
        // Act
        ErrorVm vm = new ErrorVm("400", "Bad Request", "Invalid input", Arrays.asList("field1", "field2"));

        // Assert
        assertNotNull(vm);
        assertEquals("400", vm.statusCode());
        assertEquals("Bad Request", vm.title());
        assertEquals("Invalid input", vm.detail());
        assertEquals(2, vm.fieldErrors().size());
        assertTrue(vm.fieldErrors().contains("field1"));
        assertTrue(vm.fieldErrors().contains("field2"));
    }

    @Test
    void testErrorVm_shouldCreateWithoutFieldErrors() {
        // Act
        ErrorVm vm = new ErrorVm("404", "Not Found", "Resource not found");

        // Assert
        assertNotNull(vm);
        assertEquals("404", vm.statusCode());
        assertEquals("Not Found", vm.title());
        assertEquals("Resource not found", vm.detail());
        assertTrue(vm.fieldErrors().isEmpty());
    }

    @Test
    void testErrorVm_withEmptyFieldErrors() {
        // Act
        ErrorVm vm = new ErrorVm("500", "Internal Error", "Something went wrong", Collections.emptyList());

        // Assert
        assertNotNull(vm);
        assertTrue(vm.fieldErrors().isEmpty());
    }

    @Test
    void testErrorVm_recordEquality() {
        // Arrange
        ErrorVm vm1 = new ErrorVm("400", "Bad Request", "Invalid");
        ErrorVm vm2 = new ErrorVm("400", "Bad Request", "Invalid");

        // Assert
        assertEquals(vm1, vm2);
    }

    @Test
    void testErrorVm_recordToString() {
        // Arrange
        ErrorVm vm = new ErrorVm("403", "Forbidden", "Access denied");

        // Act
        String result = vm.toString();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("403"));
        assertTrue(result.contains("Forbidden"));
        assertTrue(result.contains("Access denied"));
    }
}
