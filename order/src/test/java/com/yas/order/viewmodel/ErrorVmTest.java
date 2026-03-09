package com.yas.order.viewmodel;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ErrorVmTest {

    @Test
    void testErrorVm_withAllFields() {
        // Arrange
        List<String> fieldErrors = List.of("Field error 1", "Field error 2");

        // Act
        ErrorVm errorVm = new ErrorVm("400", "Bad Request", "Invalid input", fieldErrors);

        // Assert
        assertEquals("400", errorVm.statusCode());
        assertEquals("Bad Request", errorVm.title());
        assertEquals("Invalid input", errorVm.detail());
        assertEquals(2, errorVm.fieldErrors().size());
        assertTrue(errorVm.fieldErrors().contains("Field error 1"));
    }

    @Test
    void testErrorVm_withoutFieldErrors() {
        // Act
        ErrorVm errorVm = new ErrorVm("404", "Not Found", "Resource not found");

        // Assert
        assertEquals("404", errorVm.statusCode());
        assertEquals("Not Found", errorVm.title());
        assertEquals("Resource not found", errorVm.detail());
        assertNotNull(errorVm.fieldErrors());
        assertTrue(errorVm.fieldErrors().isEmpty());
    }

    @Test
    void testErrorVm_getters() {
        // Arrange
        ErrorVm errorVm = new ErrorVm("500", "Internal Error", "Server error");

        // Assert
        assertEquals("500", errorVm.statusCode());
        assertEquals("Internal Error", errorVm.title());
        assertEquals("Server error", errorVm.detail());
    }

    @Test
    void testErrorVm_equalsAndHashCode() {
        // Arrange
        ErrorVm vm1 = new ErrorVm("400", "Bad Request", "Error");
        ErrorVm vm2 = new ErrorVm("400", "Bad Request", "Error");

        // Assert
        assertEquals(vm1, vm2);
        assertEquals(vm1.hashCode(), vm2.hashCode());
    }

    @Test
    void testErrorVm_toString() {
        // Arrange
        ErrorVm errorVm = new ErrorVm("400", "Bad Request", "Error detail");

        // Act
        String result = errorVm.toString();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("400"));
        assertTrue(result.contains("Bad Request"));
    }

    @Test
    void testErrorVm_withEmptyFieldErrors() {
        // Arrange
        List<String> fieldErrors = new ArrayList<>();

        // Act
        ErrorVm errorVm = new ErrorVm("400", "Error", "Details", fieldErrors);

        // Assert
        assertNotNull(errorVm.fieldErrors());
        assertTrue(errorVm.fieldErrors().isEmpty());
    }

    @Test
    void testErrorVm_withMultipleFieldErrors() {
        // Arrange
        List<String> fieldErrors = List.of("Error 1", "Error 2", "Error 3");

        // Act
        ErrorVm errorVm = new ErrorVm("400", "Validation Error", "Multiple errors", fieldErrors);

        // Assert
        assertEquals(3, errorVm.fieldErrors().size());
        assertEquals("Error 1", errorVm.fieldErrors().get(0));
        assertEquals("Error 2", errorVm.fieldErrors().get(1));
        assertEquals("Error 3", errorVm.fieldErrors().get(2));
    }
}
