package com.yas.product.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.yas.product.config.DatabaseAutoConfig;
import com.yas.product.model.Brand;
import com.yas.product.model.Category;
import com.yas.product.model.Product;
import com.yas.product.model.ProductCategory;
import com.yas.product.model.enumeration.DimensionUnit;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(DatabaseAutoConfig.class)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Brand testBrand;
    private Brand testBrand2;
    private Category testCategory;
    private Product testProduct1;
    private Product testProduct2;
    private Product testProduct3;

    @BeforeEach
    void setUp() {
        // Setup Brand
        testBrand = new Brand();
        testBrand.setName("Test Brand");
        testBrand.setSlug("test-brand");
        testBrand.setPublished(true);
        entityManager.persist(testBrand);

        testBrand2 = new Brand();
        testBrand2.setName("Another Brand");
        testBrand2.setSlug("another-brand");
        testBrand2.setPublished(true);
        entityManager.persist(testBrand2);

        // Setup Category
        testCategory = new Category();
        testCategory.setName("Test Category");
        testCategory.setSlug("test-category");
        testCategory.setIsPublished(true);
        entityManager.persist(testCategory);

        // Setup Product 1
        testProduct1 = Product.builder()
            .name("Test Product 1")
            .slug("test-product-1")
            .sku("SKU-001")
            .gtin("GTIN-001")
            .price(99.99)
            .isPublished(true)
            .isFeatured(true)
            .isVisibleIndividually(true)
            .isAllowedToOrder(true)
            .brand(testBrand)
            .build();
        entityManager.persist(testProduct1);

        // Setup Product 2
        testProduct2 = Product.builder()
            .name("Test Product 2")
            .slug("test-product-2")
            .sku("SKU-002")
            .gtin("GTIN-002")
            .price(149.99)
            .isPublished(true)
            .isFeatured(false)
            .isVisibleIndividually(true)
            .isAllowedToOrder(true)
            .brand(testBrand)
            .build();
        entityManager.persist(testProduct2);

        // Setup Product 3 - unpublished
        testProduct3 = Product.builder()
            .name("Unpublished Product")
            .slug("unpublished-product")
            .sku("SKU-003")
            .gtin("GTIN-003")
            .price(199.99)
            .isPublished(false)
            .isFeatured(false)
            .isVisibleIndividually(true)
            .isAllowedToOrder(true)
            .brand(testBrand2)
            .build();
        entityManager.persist(testProduct3);

        entityManager.flush();
    }

    @Test
    void testFindAllByBrandAndIsPublishedTrueOrderByIdAsc_Success() {
        // When
        List<Product> products = productRepository.findAllByBrandAndIsPublishedTrueOrderByIdAsc(testBrand);

        // Then
        assertThat(products).isNotEmpty();
        assertThat(products).hasSize(2);
        assertThat(products.get(0).getName()).isEqualTo("Test Product 1");
        assertThat(products.get(1).getName()).isEqualTo("Test Product 2");
    }

    @Test
    void testFindAllByBrandAndIsPublishedTrueOrderByIdAsc_OnlyPublished() {
        // When
        List<Product> products = productRepository.findAllByBrandAndIsPublishedTrueOrderByIdAsc(testBrand2);

        // Then - testProduct3 is unpublished, should not be returned
        assertThat(products).isEmpty();
    }

    @Test
    void testFindBySlugAndIsPublishedTrue_Success() {
        // When
        Optional<Product> product = productRepository.findBySlugAndIsPublishedTrue("test-product-1");

        // Then
        assertThat(product).isPresent();
        assertThat(product.get().getName()).isEqualTo("Test Product 1");
    }

    @Test
    void testFindBySlugAndIsPublishedTrue_NotFound() {
        // When - unpublished product
        Optional<Product> product = productRepository.findBySlugAndIsPublishedTrue("unpublished-product");

        // Then
        assertThat(product).isEmpty();
    }

    @Test
    void testFindByGtinAndIsPublishedTrue_Success() {
        // When
        Optional<Product> product = productRepository.findByGtinAndIsPublishedTrue("GTIN-001");

        // Then
        assertThat(product).isPresent();
        assertThat(product.get().getName()).isEqualTo("Test Product 1");
    }

    @Test
    void testFindByGtinAndIsPublishedTrue_NotFound() {
        // When
        Optional<Product> product = productRepository.findByGtinAndIsPublishedTrue("GTIN-003");

        // Then - unpublished
        assertThat(product).isEmpty();
    }

    @Test
    void testFindBySkuAndIsPublishedTrue_Success() {
        // When
        Optional<Product> product = productRepository.findBySkuAndIsPublishedTrue("SKU-001");

        // Then
        assertThat(product).isPresent();
        assertThat(product.get().getName()).isEqualTo("Test Product 1");
    }

    @Test
    void testFindBySkuAndIsPublishedTrue_NotFound() {
        // When
        Optional<Product> product = productRepository.findBySkuAndIsPublishedTrue("SKU-003");

        // Then
        assertThat(product).isEmpty();
    }

    @Test
    void testGetProductsWithFilter_WithProductName() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);

        // When
        Page<Product> products = productRepository.getProductsWithFilter("product", "", pageable);

        // Then
        assertThat(products).isNotEmpty();
        assertThat(products.getTotalElements()).isEqualTo(2);
    }

    @Test
    void testGetProductsWithFilter_WithBrandName() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);

        // When
        Page<Product> products = productRepository.getProductsWithFilter("", "Test Brand", pageable);

        // Then
        assertThat(products).isNotEmpty();
        assertThat(products.getTotalElements()).isEqualTo(2);
    }

    @Test
    void testGetProductsWithFilter_WithBothFilters() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);

        // When
        Page<Product> products = productRepository.getProductsWithFilter("product 1", "Test Brand", pageable);

        // Then
        assertThat(products).isNotEmpty();
        assertThat(products.getTotalElements()).isEqualTo(1);
        assertThat(products.getContent().get(0).getName()).isEqualTo("Test Product 1");
    }

    @Test
    void testGetExportingProducts_Success() {
        // When
        List<Product> products = productRepository.getExportingProducts("product", "");

        // Then
        assertThat(products).isNotEmpty();
        assertThat(products).hasSize(2);
    }

    @Test
    void testFindAllByIdIn_Success() {
        // When
        List<Product> products = productRepository.findAllByIdIn(
            List.of(testProduct1.getId(), testProduct2.getId())
        );

        // Then
        assertThat(products).isNotEmpty();
        assertThat(products).hasSize(2);
    }

    @Test
    void testFindAllByIdIn_EmptyList() {
        // When
        List<Product> products = productRepository.findAllByIdIn(List.of(999L, 998L));

        // Then
        assertThat(products).isEmpty();
    }

    @Test
    void testGetFeaturedProduct_Success() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);

        // When
        Page<Product> products = productRepository.getFeaturedProduct(pageable);

        // Then
        assertThat(products).isNotEmpty();
        assertThat(products.getTotalElements()).isEqualTo(1);
        assertThat(products.getContent().get(0).getName()).isEqualTo("Test Product 1");
    }

    @Test
    void testFindByProductNameAndCategorySlugAndPriceBetween_WithProductName() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);

        // When
        Page<Product> products = productRepository.findByProductNameAndCategorySlugAndPriceBetween(
            "product", null, null, null, pageable
        );

        // Then
        assertThat(products).isNotEmpty();
        assertThat(products.getTotalElements()).isEqualTo(2);
    }

    @Test
    void testFindByProductNameAndCategorySlugAndPriceBetween_WithPriceRange() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);

        // When
        Page<Product> products = productRepository.findByProductNameAndCategorySlugAndPriceBetween(
            "", null, 90.0, 120.0, pageable
        );

        // Then
        assertThat(products).isNotEmpty();
        assertThat(products.getTotalElements()).isEqualTo(1);
        assertThat(products.getContent().get(0).getPrice()).isEqualTo(99.99);
    }

    @Test
    void testFindProductForWarehouse_WithName() {
        // When
        List<Product> products = productRepository.findProductForWarehouse(
            "Product", "", List.of(), "ALL"
        );

        // Then
        assertThat(products).isNotEmpty();
        assertThat(products.size()).isGreaterThanOrEqualTo(2);
    }

    @Test
    void testFindProductForWarehouse_WithSku() {
        // When
        List<Product> products = productRepository.findProductForWarehouse(
            "", "SKU-001", List.of(), "ALL"
        );

        // Then
        assertThat(products).isNotEmpty();
        assertThat(products.stream().anyMatch(p -> "SKU-001".equals(p.getSku()))).isTrue();
    }

    @Test
    void testFindByCategoryIdsIn_Success() {
        // Given - Create product category relationship
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProduct(testProduct1);
        productCategory.setCategory(testCategory);
        entityManager.persist(productCategory);
        entityManager.flush();

        // When
        List<Product> products = productRepository.findByCategoryIdsIn(
            List.of(productCategory.getId())
        );

        // Then
        assertThat(products).isNotEmpty();
    }

    @Test
    void testFindByBrandIdsIn_Success() {
        // When
        List<Product> products = productRepository.findByBrandIdsIn(
            List.of(testBrand.getId())
        );

        // Then
        assertThat(products).isNotEmpty();
        assertThat(products).hasSize(2);
        assertThat(products.stream().allMatch(p -> p.getBrand().getId().equals(testBrand.getId()))).isTrue();
    }

    @Test
    void testGetLatestProducts_Success() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);

        // When
        List<Product> products = productRepository.getLatestProducts(pageable);

        // Then
        assertThat(products).isNotEmpty();
        assertThat(products.size()).isGreaterThanOrEqualTo(2);
    }

    @Test
    void testFindAllPublishedProductsByIds_Success() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);

        // When
        Page<Product> products = productRepository.findAllPublishedProductsByIds(
            List.of(testProduct1.getId(), testProduct2.getId(), testProduct3.getId()),
            pageable
        );

        // Then - Only published products should be returned
        assertThat(products).isNotEmpty();
        assertThat(products.getTotalElements()).isEqualTo(2);
        assertThat(products.getContent().stream().allMatch(Product::isPublished)).isTrue();
    }

    @Test
    void testFindAllPublishedProductsByIds_OnlyPublished() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);

        // When - Only unpublished product ID
        Page<Product> products = productRepository.findAllPublishedProductsByIds(
            List.of(testProduct3.getId()),
            pageable
        );

        // Then
        assertThat(products).isEmpty();
    }
}
