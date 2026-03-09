package com.yas.customer.service;

import com.yas.commonlibrary.exception.AccessDeniedException;
import com.yas.commonlibrary.exception.NotFoundException;
import com.yas.customer.model.UserAddress;
import com.yas.customer.repository.UserAddressRepository;
import com.yas.customer.utils.Constants;
import com.yas.customer.viewmodel.address.ActiveAddressVm;
import com.yas.customer.viewmodel.address.AddressDetailVm;
import com.yas.customer.viewmodel.address.AddressPostVm;
import com.yas.customer.viewmodel.address.AddressVm;
import com.yas.customer.viewmodel.useraddress.UserAddressVm;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserAddressServiceTest {

    @Mock
    private UserAddressRepository userAddressRepository;

    @Mock
    private LocationService locationService;

    @InjectMocks
    private UserAddressService userAddressService;

    private static final String USER_ID = "test-user-123";

    @Test
    void testGetUserAddressList_whenValidUser_shouldReturnSortedAddressList() {
        // Arrange
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(USER_ID);
        SecurityContextHolder.setContext(securityContext);
        UserAddress address1 = UserAddress.builder()
            .id(1L)
            .userId(USER_ID)
            .addressId(100L)
            .isActive(false)
            .build();

        UserAddress address2 = UserAddress.builder()
            .id(2L)
            .userId(USER_ID)
            .addressId(200L)
            .isActive(true)
            .build();

        List<UserAddress> userAddresses = Arrays.asList(address1, address2);

        AddressDetailVm addressDetail1 = AddressDetailVm.builder()
            .id(100L)
            .contactName("John Doe")
            .phone("123456789")
            .addressLine1("123 Main St")
            .city("City1")
            .zipCode("12345")
            .districtId(1L)
            .districtName("District1")
            .stateOrProvinceId(1L)
            .stateOrProvinceName("State1")
            .countryId(1L)
            .countryName("Country1")
            .build();

        AddressDetailVm addressDetail2 = AddressDetailVm.builder()
            .id(200L)
            .contactName("Jane Smith")
            .phone("987654321")
            .addressLine1("456 Oak Ave")
            .city("City2")
            .zipCode("54321")
            .districtId(2L)
            .districtName("District2")
            .stateOrProvinceId(2L)
            .stateOrProvinceName("State2")
            .countryId(2L)
            .countryName("Country2")
            .build();

        List<AddressDetailVm> addressDetails = Arrays.asList(addressDetail1, addressDetail2);

        when(userAddressRepository.findAllByUserId(USER_ID)).thenReturn(userAddresses);
        when(locationService.getAddressesByIdList(anyList())).thenReturn(addressDetails);

        // Act
        List<ActiveAddressVm> result = userAddressService.getUserAddressList();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        
        // Verify sorted by isActive (active first)
        assertTrue(result.get(0).isActive());
        assertFalse(result.get(1).isActive());
        
        assertEquals(200L, result.get(0).id());
        assertEquals(100L, result.get(1).id());
        
        verify(userAddressRepository).findAllByUserId(USER_ID);
        verify(locationService).getAddressesByIdList(Arrays.asList(100L, 200L));
        SecurityContextHolder.clearContext();
    }

    @Test
    void testGetUserAddressList_whenAnonymousUser_shouldThrowAccessDeniedException() {
        // Arrange
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("anonymousUser");
        SecurityContextHolder.setContext(securityContext);

        // Act & Assert
        AccessDeniedException exception = assertThrows(AccessDeniedException.class,
            () -> userAddressService.getUserAddressList());

        assertEquals(Constants.ErrorCode.UNAUTHENTICATED, exception.getMessage());
    }

    @Test
    void testGetUserAddressList_whenNoAddresses_shouldReturnEmptyList() {
        // Arrange
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(USER_ID);
        SecurityContextHolder.setContext(securityContext);
        
        when(userAddressRepository.findAllByUserId(USER_ID)).thenReturn(Collections.emptyList());
        when(locationService.getAddressesByIdList(Collections.emptyList())).thenReturn(Collections.emptyList());

        // Act
        List<ActiveAddressVm> result = userAddressService.getUserAddressList();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        SecurityContextHolder.clearContext();
    }

    @Test
    void testGetAddressDefault_whenValidUser_shouldReturnDefaultAddress() {
        // Arrange
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(USER_ID);
        SecurityContextHolder.setContext(securityContext);
        // Arrange
        UserAddress userAddress = UserAddress.builder()
            .id(1L)
            .userId(USER_ID)
            .addressId(100L)
            .isActive(true)
            .build();

        AddressDetailVm addressDetail = AddressDetailVm.builder()
            .id(100L)
            .contactName("John Doe")
            .phone("123456789")
            .addressLine1("123 Main St")
            .city("City")
            .zipCode("12345")
            .districtId(1L)
            .districtName("District")
            .stateOrProvinceId(1L)
            .stateOrProvinceName("State")
            .countryId(1L)
            .countryName("Country")
            .build();

        when(userAddressRepository.findByUserIdAndIsActiveTrue(USER_ID)).thenReturn(Optional.of(userAddress));
        when(locationService.getAddressById(100L)).thenReturn(addressDetail);

        // Act
        AddressDetailVm result = userAddressService.getAddressDefault();

        // Assert
        assertNotNull(result);
        assertEquals(100L, result.id());
        assertEquals("John Doe", result.contactName());
        
        verify(userAddressRepository).findByUserIdAndIsActiveTrue(USER_ID);
        verify(locationService).getAddressById(100L);
        SecurityContextHolder.clearContext();
    }

    @Test
    void testGetAddressDefault_whenAnonymousUser_shouldThrowAccessDeniedException() {
        // Arrange
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("anonymousUser");
        SecurityContextHolder.setContext(securityContext);

        // Act & Assert
        AccessDeniedException exception = assertThrows(AccessDeniedException.class,
            () -> userAddressService.getAddressDefault());

        assertEquals(Constants.ErrorCode.UNAUTHENTICATED, exception.getMessage());
    }

    @Test
    void testGetAddressDefault_whenNoActiveAddress_shouldThrowNotFoundException() {
        // Arrange
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(USER_ID);
        SecurityContextHolder.setContext(securityContext);
        
        when(userAddressRepository.findByUserIdAndIsActiveTrue(USER_ID)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class,
            () -> userAddressService.getAddressDefault());

        assertEquals("User address not found", exception.getMessage());
    }

    @Test
    void testCreateAddress_whenFirstAddress_shouldSetAsActive() {
        // Arrange
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(USER_ID);
        SecurityContextHolder.setContext(securityContext);

        AddressPostVm addressPostVm = new AddressPostVm(
            "John Doe", "123456789", "123 Main St", "City", "12345", 1L, 1L, 1L
        );

        AddressVm addressVm = AddressVm.builder()
            .id(100L)
            .contactName("John Doe")
            .phone("123456789")
            .addressLine1("123 Main St")
            .city("City")
            .zipCode("12345")
            .districtId(1L)
            .stateOrProvinceId(1L)
            .countryId(1L)
            .build();

        UserAddress savedUserAddress = UserAddress.builder()
            .id(1L)
            .userId(USER_ID)
            .addressId(100L)
            .isActive(true)
            .build();

        when(userAddressRepository.findAllByUserId(USER_ID)).thenReturn(Collections.emptyList());
        when(locationService.createAddress(addressPostVm)).thenReturn(addressVm);
        when(userAddressRepository.save(any(UserAddress.class))).thenReturn(savedUserAddress);

        // Act
        UserAddressVm result = userAddressService.createAddress(addressPostVm);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals(USER_ID, result.userId());
        assertTrue(result.isActive());
        
        verify(userAddressRepository).findAllByUserId(USER_ID);
        verify(locationService).createAddress(addressPostVm);
        verify(userAddressRepository).save(argThat(ua -> 
            ua.getUserId().equals(USER_ID) && 
            ua.getAddressId().equals(100L) && 
            ua.getIsActive().equals(true)
        ));
        SecurityContextHolder.clearContext();
    }

    @Test
    void testCreateAddress_whenNotFirstAddress_shouldSetAsInactive() {
        // Arrange
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(USER_ID);
        SecurityContextHolder.setContext(securityContext);
        // Arrange
        AddressPostVm addressPostVm = new AddressPostVm(
            "Jane Smith", "987654321", "456 Oak Ave", "City2", "54321", 2L, 2L, 2L
        );

        UserAddress existingAddress = UserAddress.builder()
            .id(1L)
            .userId(USER_ID)
            .addressId(100L)
            .isActive(true)
            .build();

        AddressVm addressVm = AddressVm.builder()
            .id(200L)
            .contactName("Jane Smith")
            .phone("987654321")
            .addressLine1("456 Oak Ave")
            .city("City2")
            .zipCode("54321")
            .districtId(2L)
            .stateOrProvinceId(2L)
            .countryId(2L)
            .build();

        UserAddress savedUserAddress = UserAddress.builder()
            .id(2L)
            .userId(USER_ID)
            .addressId(200L)
            .isActive(false)
            .build();

        when(userAddressRepository.findAllByUserId(USER_ID)).thenReturn(Collections.singletonList(existingAddress));
        when(locationService.createAddress(addressPostVm)).thenReturn(addressVm);
        when(userAddressRepository.save(any(UserAddress.class))).thenReturn(savedUserAddress);

        // Act
        UserAddressVm result = userAddressService.createAddress(addressPostVm);

        // Assert
        assertNotNull(result);
        assertEquals(2L, result.id());
        assertFalse(result.isActive());
        
        verify(userAddressRepository).save(argThat(ua -> ua.getIsActive().equals(false)));
        SecurityContextHolder.clearContext();
    }

    @Test
    void testDeleteAddress_whenValidAddress_shouldDeleteSuccessfully() {
        // Arrange
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(USER_ID);
        SecurityContextHolder.setContext(securityContext);
        // Arrange
        Long addressId = 100L;
        UserAddress userAddress = UserAddress.builder()
            .id(1L)
            .userId(USER_ID)
            .addressId(addressId)
            .isActive(false)
            .build();

        when(userAddressRepository.findOneByUserIdAndAddressId(USER_ID, addressId)).thenReturn(userAddress);

        // Act
        userAddressService.deleteAddress(addressId);

        // Assert
        verify(userAddressRepository).findOneByUserIdAndAddressId(USER_ID, addressId);
        verify(userAddressRepository).delete(userAddress);
        SecurityContextHolder.clearContext();
    }

    @Test
    void testDeleteAddress_whenAddressNotFound_shouldThrowNotFoundException() {
        // Arrange
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(USER_ID);
        SecurityContextHolder.setContext(securityContext);
        // Arrange
        Long addressId = 999L;
        when(userAddressRepository.findOneByUserIdAndAddressId(USER_ID, addressId)).thenReturn(null);

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class,
            () -> userAddressService.deleteAddress(addressId));

        assertEquals("User address not found", exception.getMessage());
        
        verify(userAddressRepository).findOneByUserIdAndAddressId(USER_ID, addressId);
        verify(userAddressRepository, never()).delete(any());
        SecurityContextHolder.clearContext();
    }

    @Test
    void testChooseDefaultAddress_whenValidAddress_shouldUpdateActiveStatus() {
        // Arrange
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(USER_ID);
        SecurityContextHolder.setContext(securityContext);
        // Arrange
        Long newActiveAddressId = 200L;

        UserAddress address1 = UserAddress.builder()
            .id(1L)
            .userId(USER_ID)
            .addressId(100L)
            .isActive(true)
            .build();

        UserAddress address2 = UserAddress.builder()
            .id(2L)
            .userId(USER_ID)
            .addressId(200L)
            .isActive(false)
            .build();

        UserAddress address3 = UserAddress.builder()
            .id(3L)
            .userId(USER_ID)
            .addressId(300L)
            .isActive(false)
            .build();

        List<UserAddress> userAddresses = Arrays.asList(address1, address2, address3);

        when(userAddressRepository.findAllByUserId(USER_ID)).thenReturn(userAddresses);

        // Act
        userAddressService.chooseDefaultAddress(newActiveAddressId);

        // Assert
        assertFalse(address1.getIsActive());
        assertTrue(address2.getIsActive());
        assertFalse(address3.getIsActive());
        
        verify(userAddressRepository).findAllByUserId(USER_ID);
        verify(userAddressRepository).saveAll(userAddresses);
        SecurityContextHolder.clearContext();
    }

    @Test
    void testChooseDefaultAddress_whenNoAddresses_shouldNotFail() {
        // Arrange
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(USER_ID);
        SecurityContextHolder.setContext(securityContext);
        // Arrange
        Long addressId = 100L;
        when(userAddressRepository.findAllByUserId(USER_ID)).thenReturn(Collections.emptyList());

        // Act
        userAddressService.chooseDefaultAddress(addressId);

        // Assert
        verify(userAddressRepository).findAllByUserId(USER_ID);
        verify(userAddressRepository).saveAll(Collections.emptyList());
        SecurityContextHolder.clearContext();
    }

    @Test
    void testGetUserAddressList_whenMultipleAddresses_shouldMapCorrectly() {
        // Arrange
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(USER_ID);
        SecurityContextHolder.setContext(securityContext);
        // Arrange
        UserAddress address1 = UserAddress.builder()
            .id(1L)
            .userId(USER_ID)
            .addressId(100L)
            .isActive(true)
            .build();

        AddressDetailVm addressDetail1 = AddressDetailVm.builder()
            .id(100L)
            .contactName("Contact Name")
            .phone("111222333")
            .addressLine1("Address Line 1")
            .city("TestCity")
            .zipCode("00000")
            .districtId(10L)
            .districtName("TestDistrict")
            .stateOrProvinceId(20L)
            .stateOrProvinceName("TestProvince")
            .countryId(30L)
            .countryName("TestCountry")
            .build();

        when(userAddressRepository.findAllByUserId(USER_ID)).thenReturn(Collections.singletonList(address1));
        when(locationService.getAddressesByIdList(Collections.singletonList(100L))).thenReturn(Collections.singletonList(addressDetail1));

        // Act
        List<ActiveAddressVm> result = userAddressService.getUserAddressList();

        // Assert
        assertEquals(1, result.size());
        ActiveAddressVm activeAddress = result.get(0);
        assertEquals(100L, activeAddress.id());
        assertEquals("Contact Name", activeAddress.contactName());
        assertEquals("111222333", activeAddress.phone());
        assertEquals(true, activeAddress.isActive());
        SecurityContextHolder.clearContext();
    }
}
