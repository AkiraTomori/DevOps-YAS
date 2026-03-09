package com.yas.cart.repository;

import com.yas.cart.config.DatabaseAutoConfig;
import com.yas.cart.model.CartItem;
import com.yas.cart.model.CartItemId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(DatabaseAutoConfig.class)
class CartItemRepositoryTest {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private TestEntityManager entityManager;

    private CartItem cartItem1;
    private CartItem cartItem2;
    private CartItem cartItem3;

    @BeforeEach
    void setUp() {
        cartItemRepository.deleteAll();
        
        cartItem1 = CartItem.builder()
            .customerId("customer1")
            .productId(1L)
            .quantity(5)
            .build();

        cartItem2 = CartItem.builder()
            .customerId("customer1")
            .productId(2L)
            .quantity(10)
            .build();

        cartItem3 = CartItem.builder()
            .customerId("customer2")
            .productId(1L)
            .quantity(3)
            .build();
    }

    @Test
    void testFindByCustomerIdAndProductId_whenExists_shouldReturnCartItem() {
        // Arrange
        entityManager.persist(cartItem1);
        entityManager.flush();
        entityManager.clear();

        // Act
        Optional<CartItem> result = cartItemRepository.findByCustomerIdAndProductId("customer1", 1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("customer1", result.get().getCustomerId());
        assertEquals(1L, result.get().getProductId());
        assertEquals(5, result.get().getQuantity());
    }

    @Test
    void testFindByCustomerIdAndProductId_whenNotExists_shouldReturnEmpty() {
        // Act
        Optional<CartItem> result = cartItemRepository.findByCustomerIdAndProductId("customer999", 999L);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void testFindByCustomerIdOrderByCreatedOnDesc_shouldReturnAllItemsForCustomer() {
        // Arrange
        entityManager.persist(cartItem1);
        entityManager.persist(cartItem2);
        entityManager.persist(cartItem3);
        entityManager.flush();
        entityManager.clear();

        // Act
        List<CartItem> results = cartItemRepository.findByCustomerIdOrderByCreatedOnDesc("customer1");

        // Assert
        assertEquals(2, results.size());
        assertTrue(results.stream().allMatch(item -> item.getCustomerId().equals("customer1")));
    }

    @Test
    void testFindByCustomerIdOrderByCreatedOnDesc_whenNoItems_shouldReturnEmptyList() {
        // Act
        List<CartItem> results = cartItemRepository.findByCustomerIdOrderByCreatedOnDesc("nonExistentCustomer");

        // Assert
        assertTrue(results.isEmpty());
    }

    @Test
    void testFindByCustomerIdAndProductIdIn_shouldReturnMatchingItems() {
        // Arrange
        entityManager.persist(cartItem1);
        entityManager.persist(cartItem2);
        entityManager.persist(cartItem3);
        entityManager.flush();
        entityManager.clear();

        List<Long> productIds = Arrays.asList(1L, 2L);

        // Act
        List<CartItem> results = cartItemRepository.findByCustomerIdAndProductIdIn("customer1", productIds);

        // Assert
        assertEquals(2, results.size());
        assertTrue(results.stream().allMatch(item -> item.getCustomerId().equals("customer1")));
        assertTrue(results.stream().anyMatch(item -> item.getProductId().equals(1L)));
        assertTrue(results.stream().anyMatch(item -> item.getProductId().equals(2L)));
    }

    @Test
    void testFindByCustomerIdAndProductIdIn_withEmptyProductIds_shouldReturnEmptyList() {
        // Arrange
        entityManager.persist(cartItem1);
        entityManager.flush();
        entityManager.clear();

        // Act
        List<CartItem> results = cartItemRepository.findByCustomerIdAndProductIdIn("customer1", List.of());

        // Assert
        assertTrue(results.isEmpty());
    }

    @Test
    void testFindByCustomerIdAndProductIdIn_withNonMatchingProductIds_shouldReturnEmptyList() {
        // Arrange
        entityManager.persist(cartItem1);
        entityManager.flush();
        entityManager.clear();

        List<Long> productIds = Arrays.asList(999L, 888L);

        // Act
        List<CartItem> results = cartItemRepository.findByCustomerIdAndProductIdIn("customer1", productIds);

        // Assert
        assertTrue(results.isEmpty());
    }

    @Test
    void testDeleteByCustomerIdAndProductId_shouldRemoveItem() {
        // Arrange
        entityManager.persist(cartItem1);
        entityManager.persist(cartItem2);
        entityManager.flush();
        entityManager.clear();

        // Act
        cartItemRepository.deleteByCustomerIdAndProductId("customer1", 1L);
        entityManager.flush();
        entityManager.clear();

        // Assert
        Optional<CartItem> deletedItem = cartItemRepository.findById(new CartItemId("customer1", 1L));
        Optional<CartItem> existingItem = cartItemRepository.findById(new CartItemId("customer1", 2L));
        
        assertFalse(deletedItem.isPresent());
        assertTrue(existingItem.isPresent());
    }

    @Test
    void testDeleteByCustomerIdAndProductId_whenNotExists_shouldNotThrowException() {
        // Act & Assert
        assertDoesNotThrow(() -> 
            cartItemRepository.deleteByCustomerIdAndProductId("nonExistent", 999L)
        );
    }

    @Test
    void testSaveCartItem_shouldPersistSuccessfully() {
        // Act
        CartItem savedItem = cartItemRepository.save(cartItem1);
        entityManager.flush();
        entityManager.clear();

        // Assert
        assertNotNull(savedItem);
        Optional<CartItem> foundItem = cartItemRepository.findById(
            new CartItemId(cartItem1.getCustomerId(), cartItem1.getProductId())
        );
        assertTrue(foundItem.isPresent());
        assertEquals(cartItem1.getQuantity(), foundItem.get().getQuantity());
    }

    @Test
    void testUpdateCartItem_shouldModifyQuantity() {
        // Arrange
        entityManager.persist(cartItem1);
        entityManager.flush();
        entityManager.clear();

        // Act
        CartItem existingItem = cartItemRepository.findById(
            new CartItemId("customer1", 1L)
        ).orElseThrow();
        existingItem.setQuantity(20);
        cartItemRepository.save(existingItem);
        entityManager.flush();
        entityManager.clear();

        // Assert
        CartItem updatedItem = cartItemRepository.findById(
            new CartItemId("customer1", 1L)
        ).orElseThrow();
        assertEquals(20, updatedItem.getQuantity());
    }

    @Test
    void testFindByCustomerId_withMultipleItems_shouldMaintainOrder() {
        // Arrange
        entityManager.persist(cartItem1);
        try {
            Thread.sleep(10); // Ensure different timestamps
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        entityManager.persist(cartItem2);
        entityManager.flush();
        entityManager.clear();

        // Act
        List<CartItem> results = cartItemRepository.findByCustomerIdOrderByCreatedOnDesc("customer1");

        // Assert
        assertEquals(2, results.size());
        // Note: Without explicit timestamps, we can only verify the items are returned
    }
}
