package com.appsdeveloperblog.app.ws.service.impl;

import com.appsdeveloperblog.app.ws.io.entity.UserEntity;
import com.appsdeveloperblog.app.ws.repository.PasswordResetTokenRepository;
import com.appsdeveloperblog.app.ws.repository.UserRepository;
import com.appsdeveloperblog.app.ws.shared.dto.AddressDTO;
import com.appsdeveloperblog.app.ws.shared.dto.UserDto;
import com.appsdeveloperblog.app.ws.shared.dto.Utils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {
    @InjectMocks
    UserServiceImpl userService;
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordResetTokenRepository passwordResetTokenRepository;
    @Mock
    Utils utils;
    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;
    String userId = "asajnfsjknsfs";
    String  encryptedPassword = "qwertyuiop";
    UserEntity userEntity;
    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        userEntity = new UserEntity();
        userEntity.setId(1);
        userEntity.setFirstName("Salim");
        userEntity.setUserId(userId);
        userEntity.setEncryptedPassword(encryptedPassword);
        userEntity.setEmail("salimzakari@gmail.com");
        userEntity.setEmailVerificationToken("wertyuijhgfdsaASDFG");
    }

    @Test
    void getUser() {
        when(userRepository.findByEmail(anyString())).thenReturn(userEntity);
        UserDto userDto = userService.getUser("salimzakari6@mgail.com");
        assertNotNull(userDto);
        assertEquals("Salim", userDto.getFirstName());
    }

    @Test
    void getUser_UsernameNotFoundException(){
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        assertThrows(UsernameNotFoundException.class, ()->{
             userService.getUser("salimzakari6@mgail.com");
        });
    }

    @Test
    void createUser() {
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        when (utils.generateAddressId(anyInt())).thenReturn("asajnfsjknsfs");
        when (utils.generateUserId(anyInt())).thenReturn(userId);
        when (utils.generateEmailVerificationToken(anyString())).thenReturn("aujkfskjd");
        when (bCryptPasswordEncoder.encode(anyString())).thenReturn(encryptedPassword);
        when (userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setType("shipping");
        addressDTO.setCountry("Nigeria");
        addressDTO.setCity("Abuja");
        addressDTO.setPostalCode("3-00-93");
        addressDTO.setStreetName("FCDA Street");

        AddressDTO billingAddressDTO = new AddressDTO();
        billingAddressDTO.setType("billing");
        billingAddressDTO.setCountry("Nigeria");
        billingAddressDTO.setCity("Abuja");
        billingAddressDTO.setPostalCode("3-00-93");
        billingAddressDTO.setStreetName("FCDA Street");

        List<AddressDTO> addresses = new ArrayList<>();
        addresses.add(addressDTO);
        addresses.add(billingAddressDTO);

        UserDto userDto = new UserDto();
        userDto.setAddresses(addresses);
        userDto.setFirstName("Salim");
        userDto.setLastName("Zakari");
        userDto.setPassword("This is my password");
        userDto.setEmail("salimzakari6@gmail.com");

        UserDto storedUserDetails = userService.createUser(userDto);
        assertNotNull(storedUserDetails);
        assertEquals(userEntity.getFirstName(), storedUserDetails.getFirstName());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void getUsers() {
    }

    @Test
    void verifyEmailToken() {
    }

    @Test
    void getUserByUserId() {
    }

    @Test
    void loadUserByUsername() {
    }

    @Test
    void requestPasswordReset() {
    }

    @Test
    void resetPassword() {
    }
}