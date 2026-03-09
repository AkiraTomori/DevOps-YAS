package com.yas.product.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.yas.commonlibrary.exception.BadRequestException;
import com.yas.commonlibrary.exception.DuplicatedException;
import com.yas.commonlibrary.exception.NotFoundException;
import com.yas.product.model.Brand;
import com.yas.product.model.Category;
import com.yas.product.model.Product;
import com.yas.product.model.ProductCategory;
import com.yas.product.model.ProductImage;
import com.yas.product.model.ProductOption;
import com.yas.product.model.ProductRelated;
import com.yas.product.model.enumeration.DimensionUnit;
import com.yas.product.repository.BrandRepository;
import com.yas.product.repository.CategoryRepository;
import com.yas.product.repository.ProductCategoryRepository;
import com.yas.product.repository.ProductImageRepository;
import com.yas.product.repository.ProductOptionCombinationRepository;
import com.yas.product.repository.ProductOptionRepository;
import com.yas.product.repository.ProductOptionValueRepository;
import com.yas.product.repository.ProductRelatedRepository;
import com.yas.product.repository.ProductRepository;
import com.yas.product.viewmodel.ImageVm;
import com.yas.product.viewmodel.NoFileMediaVm;
import com.yas.product.viewmodel.product.ProductDetailGetVm;
import com.yas.product.viewmodel.product.ProductDetailVm;
import com.yas.product.viewmodel.product.ProductEsDetailVm;
import com.yas.product.viewmodel.product.ProductExportingDetailVm;
import com.yas.product.viewmodel.product.ProductFeatureGetVm;
import com.yas.product.viewmodel.product.ProductGetDetailVm;
import com.yas.product.viewmodel.product.ProductListGetFromCategoryVm;
import com.yas.product.viewmodel.product.ProductListGetVm;
import com.yas.product.viewmodel.product.ProductListVm;
import com.yas.product.viewmodel.product.ProductPostVm;
import com.yas.product.viewmodel.product.ProductPutVm;
import com.yas.product.viewmodel.product.ProductSlugGetVm;
import com.yas.product.viewmodel.product.ProductThumbnailGetVm;
import com.yas.product.viewmodel.product.ProductThumbnailVm;
import com.yas.product.viewmodel.product.ProductVariationGetVm;
import com.yas.product.viewmodel.product.ProductsGetVm;
import com.yas.product.viewmodel.product.ProductInfoVm;
import com.yas.product.viewmodel.product.ProductQuantityPostVm;
import com.yas.product.viewmodel.product.ProductQuantityPutVm;
import com.yas.product.viewmodel.product.ProductGetCheckoutListVm;
import com.yas.product.viewmodel.product.ProductCheckoutListVm;
import com.yas.product.model.enumeration.FilterExistInWhSelection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private MediaService mediaService;

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductCategoryRepository productCategoryRepository;

    @Mock
    private ProductImageRepository productImageRepository;

    @Mock
    private ProductOptionRepository productOptionRepository;

    @Mock
    private ProductOptionValueRepository productOptionValueRepository;

    @Mock
    private ProductOptionCombinationRepository productOptionCombinationRepository;

    @Mock
    private ProductRelatedRepository productRelatedRepository;

    @InjectMocks
    private ProductService productService;

    private Product testProduct;
    private Brand testBrand;
    private Category testCategory;
    private NoFileMediaVm testMediaVm;

    @BeforeEach
    void setUp() {
        // Setup test brand
        testBrand = new Brand();
        testBrand.setId(1L);
        testBrand.setName("Test Brand");
        testBrand.setSlug("test-brand");

        // Setup test category
        testCategory = new Category();
        testCategory.setId(1L);
        testCategory.setName("Test Category");
        testCategory.setSlug("test-category");

        // Setup test media
        testMediaVm = new NoFileMediaVm(1L, "Test Image", "test.jpg", "image/jpeg", "http://example.com/test.jpg");

        // Setup test product
        testProduct = Product.builder()
            .id(1L)
            .name("Test Product")
            .slug("test-product")
            .sku("TEST-SKU-001")
            .gtin("1234567890123")
            .price(99.99)
            .shortDescription("Short description")
            .description("Long description")
            .specification("Specifications")
            .weight(1.5)
            .dimensionUnit(DimensionUnit.CM)
            .length(10.0)
            .width(5.0)
            .height(3.0)
            .isAllowedToOrder(true)
            .isPublished(true)
            .isFeatured(false)
            .isVisibleIndividually(true)
            .stockTrackingEnabled(true)
            .hasOptions(false)
            .metaTitle("Meta Title")
            .metaKeyword("meta, keywords")
            .metaDescription("Meta Description")
            .thumbnailMediaId(1L)
            .brand(testBrand)
            .productCategories(new ArrayList<>())
            .productImages(new ArrayList<>())
            .taxClassId(1L)
            .build();
    }

    // ========== CREATE PRODUCT TESTS ==========

    @Test
    void testCreateProduct_Success_WithoutVariations() {
        // Given
        ProductPostVm productPostVm = new ProductPostVm(
            "Test Product",
            "test-product",
            1L,
            List.of(1L),
            "Short description",
            "Long description",
            "Specifications",
            "TEST-SKU-001",
            "1234567890123",
            1.5,
            DimensionUnit.CM,
            10.0,
            5.0,
            3.0,
            99.99,
            true,
            true,
            false,
            true,
            true,
            "Meta Title",
            "meta, keywords",
            "Meta Description",
            1L,
            List.of(1L),
            Collections.emptyList(), // no variations
            Collections.emptyList(), // no product option values
            Collections.emptyList(),
            Collections.emptyList(),
            1L
        );

        when(productRepository.findBySlugAndIsPublishedTrue(anyString())).thenReturn(Optional.empty());
        when(productRepository.findBySkuAndIsPublishedTrue(anyString())).thenReturn(Optional.empty());
        when(productRepository.findByGtinAndIsPublishedTrue(anyString())).thenReturn(Optional.empty());
        when(brandRepository.findById(1L)).thenReturn(Optional.of(testBrand));
        when(categoryRepository.findAllById(anyList())).thenReturn(List.of(testCategory));
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);

        // When
        ProductGetDetailVm result = productService.createProduct(productPostVm);

        // Then
        assertNotNull(result);
        assertEquals("Test Product", result.name());
        verify(productRepository).save(any(Product.class)); // Only called once for non-variation products
        verify(productCategoryRepository).saveAll(anyList());
        verify(productImageRepository).saveAll(anyList());
    }

    @Test
    void testCreateProduct_ThrowException_WhenSlugDuplicated() {
        // Given
        ProductPostVm productPostVm = new ProductPostVm(
            "Test Product",
            "test-product",
            null,
            Collections.emptyList(),
            "Short description",
            "Long description",
            "Specifications",
            "TEST-SKU-001",
            "",
            1.5,
            DimensionUnit.CM,
            10.0,
            5.0,
            3.0,
            99.99,
            true,
            true,
            false,
            true,
            true,
            "Meta Title",
            "meta, keywords",
            "Meta Description",
            1L,
            Collections.emptyList(),
            Collections.emptyList(),
            Collections.emptyList(),
            Collections.emptyList(),
            Collections.emptyList(),
            1L
        );

        when(productRepository.findBySlugAndIsPublishedTrue("test-product"))
            .thenReturn(Optional.of(testProduct));

        // When & Then
        assertThrows(DuplicatedException.class, () -> productService.createProduct(productPostVm));
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void testCreateProduct_ThrowException_WhenSkuDuplicated() {
        // Given
        ProductPostVm productPostVm = new ProductPostVm(
            "Test Product",
            "test-product-new",
            null,
            Collections.emptyList(),
            "Short description",
            "Long description",
            "Specifications",
            "TEST-SKU-001",
            "",
            1.5,
            DimensionUnit.CM,
            10.0,
            5.0,
            3.0,
            99.99,
            true,
            true,
            false,
            true,
            true,
            "Meta Title",
            "meta, keywords",
            "Meta Description",
            1L,
            Collections.emptyList(),
            Collections.emptyList(),
            Collections.emptyList(),
            Collections.emptyList(),
            Collections.emptyList(),
            1L
        );

        when(productRepository.findBySlugAndIsPublishedTrue(anyString())).thenReturn(Optional.empty());
        when(productRepository.findBySkuAndIsPublishedTrue("TEST-SKU-001"))
            .thenReturn(Optional.of(testProduct));

        // When & Then
        assertThrows(DuplicatedException.class, () -> productService.createProduct(productPostVm));
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void testCreateProduct_ThrowException_WhenGtinDuplicated() {
        // Given
        ProductPostVm productPostVm = new ProductPostVm(
            "Test Product",
            "test-product-new",
            null,
            Collections.emptyList(),
            "Short description",
            "Long description",
            "Specifications",
            "TEST-SKU-NEW",
            "1234567890123",
            1.5,
            DimensionUnit.CM,
            10.0,
            5.0,
            3.0,
            99.99,
            true,
            true,
            false,
            true,
            true,
            "Meta Title",
            "meta, keywords",
            "Meta Description",
            1L,
            Collections.emptyList(),
            Collections.emptyList(),
            Collections.emptyList(),
            Collections.emptyList(),
            Collections.emptyList(),
            1L
        );

        when(productRepository.findBySlugAndIsPublishedTrue("test-product-new")).thenReturn(Optional.empty());
        // No need to stub findBySkuAndIsPublishedTrue because validation will fail at GTIN check
        when(productRepository.findByGtinAndIsPublishedTrue("1234567890123"))
            .thenReturn(Optional.of(testProduct));

        // When & Then
        assertThrows(DuplicatedException.class, () -> productService.createProduct(productPostVm));
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void testCreateProduct_ThrowException_WhenLengthLessThanWidth() {
        // Given - Length (5.0) is less than Width (10.0)
        ProductPostVm productPostVm = new ProductPostVm(
            "Test Product",
            "test-product",
            null,
            Collections.emptyList(),
            "Short description",
            "Long description",
            "Specifications",
            "TEST-SKU-001",
            "",
            1.5,
            DimensionUnit.CM,
            5.0, // length < width
            10.0,
            3.0,
            99.99,
            true,
            true,
            false,
            true,
            true,
            "Meta Title",
            "meta, keywords",
            "Meta Description",
            1L,
            Collections.emptyList(),
            Collections.emptyList(),
            Collections.emptyList(),
            Collections.emptyList(),
            Collections.emptyList(),
            1L
        );

        // When & Then
        assertThrows(BadRequestException.class, () -> productService.createProduct(productPostVm));
        verify(productRepository, never()).save(any(Product.class));
    }

    // ========== GET PRODUCT BY ID TESTS ==========

    @Test
    void testGetProductById_Success() {
        // Given
        ProductImage productImage = new ProductImage();
        productImage.setImageId(2L);
        testProduct.getProductImages().add(productImage);

        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategory(testCategory);
        testProduct.getProductCategories().add(productCategory);

        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
        when(mediaService.getMedia(1L)).thenReturn(testMediaVm);
        when(mediaService.getMedia(2L)).thenReturn(testMediaVm);

        // When
        ProductDetailVm result = productService.getProductById(1L);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Test Product", result.name());
        assertEquals("test-product", result.slug());
        assertEquals("TEST-SKU-001", result.sku());
        assertEquals(99.99, result.price());
        assertEquals(1L, result.brandId());
        assertNotNull(result.thumbnailMedia());
        assertEquals(1, result.productImageMedias().size());
        assertEquals(1, result.categories().size());
        verify(productRepository).findById(1L);
        verify(mediaService, times(2)).getMedia(anyLong());
    }

    @Test
    void testGetProductById_ThrowException_WhenProductNotFound() {
        // Given
        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NotFoundException.class, () -> productService.getProductById(999L));
        verify(productRepository).findById(999L);
    }

    @Test
    void testGetProductById_Success_WithoutBrand() {
        // Given
        testProduct.setBrand(null);
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
        when(mediaService.getMedia(1L)).thenReturn(testMediaVm);

        // When
        ProductDetailVm result = productService.getProductById(1L);

        // Then
        assertNotNull(result);
        assertEquals(null, result.brandId());
    }

    // ========== UPDATE PRODUCT TESTS ==========

    @Test
    void testUpdateMainProductFromVm() {
        // Given
        ProductPutVm productPutVm = new ProductPutVm(
            "Updated Product",
            "updated-product",
            199.99,
            true,
            true,
            true,
            true,
            true,
            null,
            Collections.emptyList(),
            "Updated short description",
            "Updated long description",
            "Updated specifications",
            "UPDATED-SKU",
            "9876543210987",
            2.0,
            DimensionUnit.CM,
            15.0,
            10.0,
            5.0,
            "Updated Meta Title",
            "updated, keywords",
            "Updated Meta Description",
            2L,
            Collections.emptyList(),
            Collections.emptyList(),
            Collections.emptyList(),
            Collections.emptyList(),
            Collections.emptyList(),
            1L
        );

        // When
        productService.updateMainProductFromVm(productPutVm, testProduct);

        // Then
        assertEquals("Updated Product", testProduct.getName());
        assertEquals("updated-product", testProduct.getSlug());
        assertEquals("UPDATED-SKU", testProduct.getSku());
        assertEquals(199.99, testProduct.getPrice());
        assertEquals(2.0, testProduct.getWeight());
        assertEquals(15.0, testProduct.getLength());
    }

    @Test
    void testUpdateProduct_ThrowException_WhenProductNotFound() {
        // Given
        ProductPutVm productPutVm = new ProductPutVm(
            "Updated Product",
            "updated-product",
            199.99,
            true,
            true,
            true,
            true,
            true,
            null,
            Collections.emptyList(),
            "Updated short description",
            "Updated long description",
            "Updated specifications",
            "UPDATED-SKU",
            "",
            2.0,
            DimensionUnit.CM,
            15.0,
            10.0,
            5.0,
            "Updated Meta Title",
            "updated, keywords",
            "Updated Meta Description",
            2L,
            Collections.emptyList(),
            Collections.emptyList(),
            Collections.emptyList(),
            Collections.emptyList(),
            Collections.emptyList(),
            1L
        );

        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NotFoundException.class, () -> productService.updateProduct(999L, productPutVm));
        verify(productRepository).findById(999L);
    }

    // ========== DELETE PRODUCT TESTS ==========

    @Test
    void testDeleteProduct_Success() {
        // Given
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);

        // When
        productService.deleteProduct(1L);

        // Then
        assertFalse(testProduct.isPublished());
        verify(productRepository).findById(1L);
        verify(productRepository).save(testProduct);
    }

    @Test
    void testDeleteProduct_ThrowException_WhenProductNotFound() {
        // Given
        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NotFoundException.class, () -> productService.deleteProduct(999L));
        verify(productRepository).findById(999L);
        verify(productRepository, never()).save(any(Product.class));
    }

    // ========== GET PRODUCTS WITH FILTER TESTS ==========

    @Test
    void testGetProductsWithFilter_Success() {
        // Given
        List<Product> products = List.of(testProduct);
        Page<Product> productPage = new PageImpl<>(products, PageRequest.of(0, 10), 1);

        when(productRepository.getProductsWithFilter(anyString(), anyString(), any(Pageable.class)))
            .thenReturn(productPage);

        // When
        ProductListGetVm result = productService.getProductsWithFilter(0, 10, "test", "brand");

        // Then
        assertNotNull(result);
        assertEquals(1, result.productContent().size());
        assertEquals(0, result.pageNo());
        assertEquals(10, result.pageSize());
        assertEquals(1, result.totalElements());
        assertEquals(1, result.totalPages());
        assertTrue(result.isLast());
        verify(productRepository).getProductsWithFilter(anyString(), anyString(), any(Pageable.class));
    }

    @Test
    void testGetProductsWithFilter_ReturnEmptyList() {
        // Given
        Page<Product> emptyPage = new PageImpl<>(Collections.emptyList(), PageRequest.of(0, 10), 0);

        when(productRepository.getProductsWithFilter(anyString(), anyString(), any(Pageable.class)))
            .thenReturn(emptyPage);

        // When
        ProductListGetVm result = productService.getProductsWithFilter(0, 10, "nonexistent", "");

        // Then
        assertNotNull(result);
        assertTrue(result.productContent().isEmpty());
        assertEquals(0, result.totalElements());
        verify(productRepository).getProductsWithFilter(anyString(), anyString(), any(Pageable.class));
    }

    // ========== GET LATEST PRODUCTS TESTS ==========

    @Test
    void testGetLatestProducts_Success() {
        // Given
        testProduct.setCreatedBy("system");
        testProduct.setLastModifiedBy("system");

        List<Product> products = List.of(testProduct);

        when(productRepository.getLatestProducts(any(Pageable.class))).thenReturn(products);

        // When
        List<ProductListVm> result = productService.getLatestProducts(5);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Product", result.get(0).name());
        verify(productRepository).getLatestProducts(any(Pageable.class));
    }

    @Test
    void testGetLatestProducts_ReturnEmptyList_WhenCountIsZero() {
        // When
        List<ProductListVm> result = productService.getLatestProducts(0);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(productRepository, never()).getLatestProducts(any(Pageable.class));
    }

    @Test
    void testGetLatestProducts_ReturnEmptyList_WhenCountIsNegative() {
        // When
        List<ProductListVm> result = productService.getLatestProducts(-5);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(productRepository, never()).getLatestProducts(any(Pageable.class));
    }

    // ========== GET PRODUCT DETAIL BY SLUG TESTS ==========

    @Test
    void testGetProductDetail_Success() {
        // Given
        when(productRepository.findBySlugAndIsPublishedTrue("test-product"))
            .thenReturn(Optional.of(testProduct));
        when(mediaService.getMedia(1L)).thenReturn(testMediaVm);

        // When
        ProductDetailGetVm result = productService.getProductDetail("test-product");

        // Then
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Test Product", result.name());
        assertNotNull(result.thumbnailMediaUrl());
        verify(productRepository).findBySlugAndIsPublishedTrue("test-product");
        verify(mediaService).getMedia(1L);
    }

    @Test
    void testGetProductDetail_ThrowException_WhenSlugNotFound() {
        // Given
        when(productRepository.findBySlugAndIsPublishedTrue("nonexistent-slug"))
            .thenReturn(Optional.empty());

        // When & Then
        assertThrows(NotFoundException.class, () -> productService.getProductDetail("nonexistent-slug"));
        verify(productRepository).findBySlugAndIsPublishedTrue("nonexistent-slug");
    }

    // ========== GET PRODUCTS BY BRAND TESTS ==========

    @Test
    void testGetProductsByBrand_Success() {
        // Given
        List<Product> products = List.of(testProduct);
        when(brandRepository.findBySlug("test-brand")).thenReturn(Optional.of(testBrand));
        when(productRepository.findAllByBrandAndIsPublishedTrueOrderByIdAsc(testBrand)).thenReturn(products);
        when(mediaService.getMedia(1L)).thenReturn(testMediaVm);

        // When
        List<ProductThumbnailVm> result = productService.getProductsByBrand("test-brand");

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(brandRepository).findBySlug("test-brand");
        verify(productRepository).findAllByBrandAndIsPublishedTrueOrderByIdAsc(testBrand);
    }

    @Test
    void testGetProductsByBrand_ThrowException_WhenBrandNotFound() {
        // Given
        when(brandRepository.findBySlug("nonexistent-brand")).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NotFoundException.class, () -> productService.getProductsByBrand("nonexistent-brand"));
        verify(brandRepository).findBySlug("nonexistent-brand");
    }

    // ========== GET PRODUCT SLUG TESTS ==========

    @Test
    void testGetProductSlug_Success() {
        // Given
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));

        // When
        ProductSlugGetVm result = productService.getProductSlug(1L);

        // Then
        assertNotNull(result);
        assertEquals("test-product", result.slug());
        verify(productRepository).findById(1L);
    }

    @Test
    void testGetProductSlug_ThrowException_WhenProductNotFound() {
        // Given
        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NotFoundException.class, () -> productService.getProductSlug(999L));
        verify(productRepository).findById(999L);
    }
    // ========== GET PRODUCTS FROM CATEGORY TESTS ==========

    @Test
    void testGetProductsFromCategory_Success() {
        // Given
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProduct(testProduct);
        productCategory.setCategory(testCategory);

        Page<ProductCategory> productCategoryPage = new PageImpl<>(
            List.of(productCategory), PageRequest.of(0, 10), 1);

        when(categoryRepository.findBySlug("test-category")).thenReturn(Optional.of(testCategory));
        when(productCategoryRepository.findAllByCategory(any(Pageable.class), eq(testCategory)))
            .thenReturn(productCategoryPage);
        when(mediaService.getMedia(1L)).thenReturn(testMediaVm);

        // When
        ProductListGetFromCategoryVm result = productService.getProductsFromCategory(0, 10, "test-category");

        // Then
        assertNotNull(result);
        assertEquals(1, result.productContent().size());
        verify(categoryRepository).findBySlug("test-category");
        verify(productCategoryRepository).findAllByCategory(any(Pageable.class), eq(testCategory));
    }

    @Test
    void testGetProductsFromCategory_ThrowException_WhenCategoryNotFound() {
        // Given
        when(categoryRepository.findBySlug("nonexistent")).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NotFoundException.class, () -> productService.getProductsFromCategory(0, 10, "nonexistent"));
        verify(categoryRepository).findBySlug("nonexistent");
    }

    // ========== GET FEATURED PRODUCTS TESTS ==========

    @Test
    void testGetFeaturedProductsById_Success() {
        // Given
        testProduct.setParent(null);  // Parent must be null to pass condition
        List<Product> products = List.of(testProduct);
        when(productRepository.findAllByIdIn(anyList())).thenReturn(products);
        when(mediaService.getMedia(1L)).thenReturn(testMediaVm);

        // When
        List<ProductThumbnailGetVm> result = productService.getFeaturedProductsById(List.of(1L));

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(productRepository).findAllByIdIn(anyList());
    }

    @Test
    void testGetListFeaturedProducts_Success() {
        // Given
        List<Product> products = List.of(testProduct);
        Page<Product> productPage = new PageImpl<>(products, PageRequest.of(0, 10), 1);

        when(productRepository.getFeaturedProduct(any(Pageable.class))).thenReturn(productPage);
        when(mediaService.getMedia(1L)).thenReturn(testMediaVm);

        // When
        ProductFeatureGetVm result = productService.getListFeaturedProducts(0, 10);

        // Then
        assertNotNull(result);
        assertEquals(1, result.productList().size());
        verify(productRepository).getFeaturedProduct(any(Pageable.class));
    }

    // ========== GET PRODUCTS BY MULTI QUERY TESTS ==========

    @Test
    void testGetProductsByMultiQuery_Success() {
        // Given
        List<Product> products = List.of(testProduct);
        Page<Product> productPage = new PageImpl<>(products, PageRequest.of(0, 10), 1);

        when(productRepository.findByProductNameAndCategorySlugAndPriceBetween(
            anyString(), anyString(), any(), any(), any(Pageable.class))).thenReturn(productPage);
        when(mediaService.getMedia(1L)).thenReturn(testMediaVm);

        // When
        ProductsGetVm result = productService.getProductsByMultiQuery(0, 10, "test", "", 0.0, 1000.0);

        // Then
        assertNotNull(result);
        assertEquals(1, result.productContent().size());
        verify(productRepository).findByProductNameAndCategorySlugAndPriceBetween(
            anyString(), anyString(), any(), any(), any(Pageable.class));
    }

    // ========== GET PRODUCT VARIATIONS TESTS ==========

    @Test
    void testGetProductVariationsByParentId_Success() {
        // Given
        Product parentProduct = Product.builder()
            .id(2L)
            .name("Parent Product")
            .hasOptions(true)
            .products(new ArrayList<>())
            .build();

        Product variation = Product.builder()
            .id(3L)
            .name("Variation 1")
            .slug("variation-1")
            .sku("VAR-SKU-001")
            .price(79.99)
            .thumbnailMediaId(1L)
            .isPublished(true)
            .build();

        parentProduct.getProducts().add(variation);

        when(productRepository.findById(2L)).thenReturn(Optional.of(parentProduct));
        when(mediaService.getMedia(1L)).thenReturn(testMediaVm);
        when(productOptionCombinationRepository.findAllByProduct(variation)).thenReturn(new ArrayList<>());

        // When
        List<ProductVariationGetVm> result = productService.getProductVariationsByParentId(2L);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(productRepository).findById(2L);
    }

    @Test
    void testGetProductVariationsByParentId_ThrowException_WhenNotFound() {
        // Given
        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NotFoundException.class, () -> productService.getProductVariationsByParentId(999L));
        verify(productRepository).findById(999L);
    }

    // ========== EXPORT PRODUCTS TESTS ==========

    @Test
    void testExportProducts_Success() {
        // Given
        List<Product> products = List.of(testProduct);
        when(productRepository.getExportingProducts(anyString(), anyString())).thenReturn(products);

        // When
        List<ProductExportingDetailVm> result = productService.exportProducts("test", "brand");

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(productRepository).getExportingProducts(anyString(), anyString());
    }

    // ========== GET PRODUCT ES DETAIL TESTS ==========

    @Test
    void testGetProductEsDetailById_Success() {
        // Given
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));

        // When
        ProductEsDetailVm result = productService.getProductEsDetailById(1L);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Test Product", result.name());
        verify(productRepository).findById(1L);
    }

    @Test
    void testGetProductEsDetailById_ThrowException_WhenNotFound() {
        // Given
        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NotFoundException.class, () -> productService.getProductEsDetailById(999L));
        verify(productRepository).findById(999L);
    }

    // ========== GET RELATED PRODUCTS TESTS ==========

    @Test
    void testGetRelatedProductsBackoffice_Success() {
        // Given
        Product relatedProduct = Product.builder()
            .id(2L)
            .name("Related Product")
            .slug("related-product")
            .price(59.99)
            .isAllowedToOrder(true)
            .isPublished(true)
            .isFeatured(false)
            .isVisibleIndividually(true)
            .taxClassId(1L)
            .build();
        relatedProduct.setCreatedOn(java.time.ZonedDateTime.now());

        ProductRelated productRelated = ProductRelated.builder()
            .product(testProduct)
            .relatedProduct(relatedProduct)
            .build();

        List<ProductRelated> relatedList = new ArrayList<>();
        relatedList.add(productRelated);
        testProduct.setRelatedProducts(relatedList);

        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));

        // When
        List<ProductListVm> result = productService.getRelatedProductsBackoffice(1L);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Related Product", result.get(0).name());
        verify(productRepository).findById(1L);
    }

    // ========== GET PRODUCT BY IDS TESTS ==========

    @Test
    void testGetProductByIds_Success() {
        // Given
        testProduct.setCreatedBy("system");
        testProduct.setLastModifiedBy("system");
        List<Product> products = List.of(testProduct);
        when(productRepository.findAllByIdIn(anyList())).thenReturn(products);

        // When
        List<ProductListVm> result = productService.getProductByIds(List.of(1L));

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(productRepository).findAllByIdIn(anyList());
    }

    // ========== GET PRODUCT BY CATEGORY/BRAND IDS TESTS ==========

    @Test
    void testGetProductByCategoryIds_Success() {
        // Given
        testProduct.setCreatedBy("system");
        testProduct.setLastModifiedBy("system");
        List<Product> products = List.of(testProduct);
        when(productRepository.findByCategoryIdsIn(anyList())).thenReturn(products);

        // When
        List<ProductListVm> result = productService.getProductByCategoryIds(List.of(1L));

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(productRepository).findByCategoryIdsIn(anyList());
    }

    @Test
    void testGetProductByBrandIds_Success() {
        // Given
        testProduct.setCreatedBy("system");
        testProduct.setLastModifiedBy("system");
        List<Product> products = List.of(testProduct);
        when(productRepository.findByBrandIdsIn(anyList())).thenReturn(products);

        // When
        List<ProductListVm> result = productService.getProductByBrandIds(List.of(1L));

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(productRepository).findByBrandIdsIn(anyList());
    }
    // ========== EDGE CASE TESTS ==========

    @Test
    void testCreateProduct_Success_WithNullGtin() {
        // Given - GTIN is null/empty, should not check for duplicates
        ProductPostVm productPostVm = new ProductPostVm(
            "Test Product",
            "test-product",
            null,
            Collections.emptyList(),
            "Short description",
            "Long description",
            "Specifications",
            "TEST-SKU-001",
            "", // empty GTIN
            1.5,
            DimensionUnit.CM,
            10.0,
            5.0,
            3.0,
            99.99,
            true,
            true,
            false,
            true,
            true,
            "Meta Title",
            "meta, keywords",
            "Meta Description",
            1L,
            Collections.emptyList(),
            Collections.emptyList(),
            Collections.emptyList(),
            Collections.emptyList(),
            Collections.emptyList(),
            1L
        );

        when(productRepository.findBySlugAndIsPublishedTrue(anyString())).thenReturn(Optional.empty());
        when(productRepository.findBySkuAndIsPublishedTrue(anyString())).thenReturn(Optional.empty());
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);

        // When
        ProductGetDetailVm result = productService.createProduct(productPostVm);

        // Then
        assertNotNull(result);
        verify(productRepository, never()).findByGtinAndIsPublishedTrue(anyString());
    }

    @Test
    void testGetProductById_Success_WithNullThumbnail() {
        // Given
        testProduct.setThumbnailMediaId(null);
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));

        // When
        ProductDetailVm result = productService.getProductById(1L);

        // Then
        assertNotNull(result);
        assertEquals(null, result.thumbnailMedia());
        verify(mediaService, never()).getMedia(anyLong());
    }

    @Test
    void testGetProductById_Success_WithParent() {
        // Given
        Product parentProduct = Product.builder()
            .id(2L)
            .name("Parent Product")
            .build();
        testProduct.setParent(parentProduct);

        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
        when(mediaService.getMedia(1L)).thenReturn(testMediaVm);

        // When
        ProductDetailVm result = productService.getProductById(1L);

        // Then
        assertNotNull(result);
        assertEquals(2L, result.parentId());
    }

    @Test
    void testCreateProduct_SlugConvertedToLowercase() {
        // Given
        ProductPostVm productPostVm = new ProductPostVm(
            "Test Product",
            "TEST-PRODUCT-UPPERCASE", // uppercase slug
            null,
            Collections.emptyList(),
            "Short description",
            "Long description",
            "Specifications",
            "TEST-SKU-001",
            "",
            1.5,
            DimensionUnit.CM,
            10.0,
            5.0,
            3.0,
            99.99,
            true,
            true,
            false,
            true,
            true,
            "Meta Title",
            "meta, keywords",
            "Meta Description",
            1L,
            Collections.emptyList(),
            Collections.emptyList(),
            Collections.emptyList(),
            Collections.emptyList(),
            Collections.emptyList(),
            1L
        );

        when(productRepository.findBySlugAndIsPublishedTrue("test-product-uppercase"))
            .thenReturn(Optional.empty());
        when(productRepository.findBySkuAndIsPublishedTrue(anyString())).thenReturn(Optional.empty());
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> {
            Product savedProduct = invocation.getArgument(0);
            assertEquals("test-product-uppercase", savedProduct.getSlug());
            return testProduct;
        });

        // When
        ProductGetDetailVm result = productService.createProduct(productPostVm);

        // Then
        assertNotNull(result);
        verify(productRepository).findBySlugAndIsPublishedTrue("test-product-uppercase");
    }

    // ========== WAREHOUSE & STOCK MANAGEMENT TESTS ==========

    @Test
    void testGetProductsForWarehouse_Success() {
        // Given
        when(productRepository.findProductForWarehouse(
            anyString(), anyString(), anyList(), anyString()
        )).thenReturn(List.of(testProduct));

        // When
        List<ProductInfoVm> result = productService.getProductsForWarehouse(
            "Test", "SKU-001", List.of(1L), FilterExistInWhSelection.YES
        );

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(productRepository).findProductForWarehouse(
            anyString(), anyString(), anyList(), anyString()
        );
    }

    @Test
    void testUpdateProductQuantity_Success() {
        // Given
        ProductQuantityPostVm quantityVm = new ProductQuantityPostVm(1L, 100L);
        List<ProductQuantityPostVm> quantityVms = List.of(quantityVm);
        when(productRepository.findAllByIdIn(anyList())).thenReturn(List.of(testProduct));
        when(productRepository.saveAll(anyList())).thenReturn(List.of(testProduct));

        // When
        productService.updateProductQuantity(quantityVms);

        // Then
        verify(productRepository).findAllByIdIn(anyList());
        verify(productRepository).saveAll(anyList());
    }

    @Test
    void testSubtractStockQuantity_Success() {
        // Given
        testProduct.setStockQuantity(100L);
        ProductQuantityPutVm quantityVm = new ProductQuantityPutVm(1L, 20L);
        List<ProductQuantityPutVm> quantityVms = List.of(quantityVm);
        when(productRepository.findAllByIdIn(anyList())).thenReturn(List.of(testProduct));
        when(productRepository.saveAll(anyList())).thenReturn(List.of(testProduct));

        // When
        productService.subtractStockQuantity(quantityVms);

        // Then
        verify(productRepository, atLeast(1)).findAllByIdIn(anyList());
        verify(productRepository, atLeast(1)).saveAll(anyList());
    }

    @Test
    void testRestoreStockQuantity_Success() {
        // Given
        testProduct.setStockQuantity(80L);
        ProductQuantityPutVm quantityVm = new ProductQuantityPutVm(1L, 20L);
        List<ProductQuantityPutVm> quantityVms = List.of(quantityVm);
        when(productRepository.findAllByIdIn(anyList())).thenReturn(List.of(testProduct));
        when(productRepository.saveAll(anyList())).thenReturn(List.of(testProduct));

        // When
        productService.restoreStockQuantity(quantityVms);

        // Then
        verify(productRepository, atLeast(1)).findAllByIdIn(anyList());
        verify(productRepository, atLeast(1)).saveAll(anyList());
    }

    @Test
    void testGetProductCheckoutList_Success() {
        // Given
        List<Product> products = List.of(testProduct);
        Page<Product> productPage = new PageImpl<>(products, PageRequest.of(0, 10), 1);
        when(productRepository.findAllPublishedProductsByIds(anyList(), any(Pageable.class)))
            .thenReturn(productPage);
        when(mediaService.getMedia(1L)).thenReturn(testMediaVm);

        // When
        ProductGetCheckoutListVm result = productService.getProductCheckoutList(0, 10, List.of(1L));

        // Then
        assertNotNull(result);
        assertEquals(1, result.productCheckoutListVms().size());
        verify(productRepository).findAllPublishedProductsByIds(anyList(), any(Pageable.class));
    }
}
