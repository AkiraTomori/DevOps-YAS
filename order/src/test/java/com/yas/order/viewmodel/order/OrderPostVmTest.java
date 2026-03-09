package com.yas.order.viewmodel.order;

import com.yas.order.model.enumeration.DeliveryMethod;
import com.yas.order.model.enumeration.PaymentMethod;
import com.yas.order.model.enumeration.PaymentStatus;
import com.yas.order.viewmodel.orderaddress.OrderAddressPostVm;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OrderPostVmTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void testOrderPostVm_withValidData_shouldPass() {
        // Arrange
        OrderPostVm vm = createValidOrderPostVm();

        // Act
        Set<ConstraintViolation<OrderPostVm>> violations = validator.validate(vm);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    void testOrderPostVm_whenCheckoutIdIsBlank_shouldFail() {
        // Arrange
        OrderPostVm vm = OrderPostVm.builder()
                .checkoutId("")
                .email("test@example.com")
                .shippingAddressPostVm(createOrderAddressPostVm())
                .billingAddressPostVm(createOrderAddressPostVm())
                .totalPrice(BigDecimal.valueOf(100))
                .deliveryMethod(DeliveryMethod.YAS_EXPRESS)
                .paymentMethod(PaymentMethod.COD)
                .paymentStatus(PaymentStatus.PENDING)
                .orderItemPostVms(List.of())
                .build();

        // Act
        Set<ConstraintViolation<OrderPostVm>> violations = validator.validate(vm);

        // Assert
        assertFalse(violations.isEmpty());
    }

    @Test
    void testOrderPostVm_whenEmailIsBlank_shouldFail() {
        // Arrange
        OrderPostVm vm = OrderPostVm.builder()
                .checkoutId("checkout-123")
                .email("")
                .shippingAddressPostVm(createOrderAddressPostVm())
                .billingAddressPostVm(createOrderAddressPostVm())
                .totalPrice(BigDecimal.valueOf(100))
                .deliveryMethod(DeliveryMethod.YAS_EXPRESS)
                .paymentMethod(PaymentMethod.COD)
                .paymentStatus(PaymentStatus.PENDING)
                .orderItemPostVms(List.of())
                .build();

        // Act
        Set<ConstraintViolation<OrderPostVm>> violations = validator.validate(vm);

        // Assert
        assertFalse(violations.isEmpty());
    }

    @Test
    void testOrderPostVm_whenShippingAddressIsNull_shouldFail() {
        // Arrange
        OrderPostVm vm = OrderPostVm.builder()
                .checkoutId("checkout-123")
                .email("test@example.com")
                .shippingAddressPostVm(null)
                .billingAddressPostVm(createOrderAddressPostVm())
                .totalPrice(BigDecimal.valueOf(100))
                .deliveryMethod(DeliveryMethod.YAS_EXPRESS)
                .paymentMethod(PaymentMethod.COD)
                .paymentStatus(PaymentStatus.PENDING)
                .orderItemPostVms(List.of())
                .build();

        // Act
        Set<ConstraintViolation<OrderPostVm>> violations = validator.validate(vm);

        // Assert
        assertFalse(violations.isEmpty());
    }

    @Test
    void testOrderPostVm_whenTotalPriceIsNull_shouldFail() {
        // Arrange
        OrderPostVm vm = OrderPostVm.builder()
                .checkoutId("checkout-123")
                .email("test@example.com")
                .shippingAddressPostVm(createOrderAddressPostVm())
                .billingAddressPostVm(createOrderAddressPostVm())
                .totalPrice(null)
                .deliveryMethod(DeliveryMethod.YAS_EXPRESS)
                .paymentMethod(PaymentMethod.COD)
                .paymentStatus(PaymentStatus.PENDING)
                .orderItemPostVms(List.of())
                .build();

        // Act
        Set<ConstraintViolation<OrderPostVm>> violations = validator.validate(vm);

        // Assert
        assertFalse(violations.isEmpty());
    }

    @Test
    void testOrderPostVm_getters() {
        // Arrange
        OrderPostVm vm = createValidOrderPostVm();

        // Assert
        assertEquals("checkout-123", vm.checkoutId());
        assertEquals("test@example.com", vm.email());
        assertNotNull(vm.shippingAddressPostVm());
        assertNotNull(vm.billingAddressPostVm());
        assertEquals("Test note", vm.note());
        assertEquals(10.0f, vm.tax());
        assertEquals(5.0f, vm.discount());
        assertEquals(2, vm.numberItem());
        assertEquals(BigDecimal.valueOf(100), vm.totalPrice());
        assertEquals(BigDecimal.valueOf(10), vm.deliveryFee());
        assertEquals("COUPON", vm.couponCode());
        assertEquals(DeliveryMethod.YAS_EXPRESS, vm.deliveryMethod());
        assertEquals(PaymentMethod.COD, vm.paymentMethod());
        assertEquals(PaymentStatus.PENDING, vm.paymentStatus());
        assertEquals(1, vm.orderItemPostVms().size());
    }

    @Test
    void testOrderPostVm_equalsAndHashCode() {
        // Arrange
        OrderPostVm vm1 = createValidOrderPostVm();
        OrderPostVm vm2 = createValidOrderPostVm();

        // Assert
        assertEquals(vm1, vm2);
        assertEquals(vm1.hashCode(), vm2.hashCode());
    }

    @Test
    void testOrderPostVm_toString() {
        // Arrange
        OrderPostVm vm = createValidOrderPostVm();

        // Act
        String result = vm.toString();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("checkout-123"));
        assertTrue(result.contains("test@example.com"));
    }

    private OrderPostVm createValidOrderPostVm() {
        return OrderPostVm.builder()
                .checkoutId("checkout-123")
                .email("test@example.com")
                .shippingAddressPostVm(createOrderAddressPostVm())
                .billingAddressPostVm(createOrderAddressPostVm())
                .note("Test note")
                .tax(10.0f)
                .discount(5.0f)
                .numberItem(2)
                .totalPrice(BigDecimal.valueOf(100))
                .deliveryFee(BigDecimal.valueOf(10))
                .couponCode("COUPON")
                .deliveryMethod(DeliveryMethod.YAS_EXPRESS)
                .paymentMethod(PaymentMethod.COD)
                .paymentStatus(PaymentStatus.PENDING)
                .orderItemPostVms(List.of(createOrderItemPostVm()))
                .build();
    }

    private OrderAddressPostVm createOrderAddressPostVm() {
        return new OrderAddressPostVm(
                "John Doe",
                "123-456-7890",
                "123 Main St",
                "Apt 4B",
                "New York",
                "10001",
                1L,
                "Manhattan",
                1L,
                "NY",
                1L,
                "USA"
        );
    }

    private OrderItemPostVm createOrderItemPostVm() {
        return OrderItemPostVm.builder()
                .productId(1L)
                .productName("Product")
                .quantity(2)
                .productPrice(BigDecimal.valueOf(50))
                .build();
    }
}

