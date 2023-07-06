package com.appsdeveloperblog.app.ws.service.impl;

import com.appsdeveloperblog.app.ws.io.entity.UserEntity;
import com.appsdeveloperblog.app.ws.repository.PasswordResetTokenRepository;
import com.appsdeveloperblog.app.ws.repository.UserRepository;
import com.appsdeveloperblog.app.ws.shared.dto.UserDto;
import com.appsdeveloperblog.app.ws.shared.dto.Utils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

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
    String userId = "qwertyuiop";
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);
        userEntity.setFirstName("Salim");
        userEntity.setUserId(userId);
        userEntity.setEncryptedPassword("qwertyuiop");
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