package com.appsdeveloperblog.app.ws.controller;

import com.appsdeveloperblog.app.ws.io.entity.UserEntity;
import com.appsdeveloperblog.app.ws.service.impl.UserServiceImpl;
import com.appsdeveloperblog.app.ws.shared.dto.AddressDTO;
import com.appsdeveloperblog.app.ws.shared.dto.UserDto;
import com.appsdeveloperblog.app.ws.ui.model.response.UserRest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @InjectMocks
    UserController userController;
    @Mock
    UserServiceImpl userService;
    UserDto userDto;
    String userId = "asajnfsjknsfs";
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userDto = new UserDto();
        userDto.setUserId(userId);
        userDto.setFirstName("Salim");
        userDto.setLastName("Zakari");
        userDto.setEmail("salimzakari@gmail.com");
        userDto.setEmailVerificationToken(null);
        userDto.setEmailVerificationStatus(Boolean.FALSE);
        userDto.setAddresses(getAddressesDto());
    }

    private List<AddressDTO> getAddressesDto() {
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

        return addresses;
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getUser() {
        when(userService.getUserByUserId(anyString())).thenReturn(userDto);

        UserRest userRest = userController.getUser(userId);
        assertNotNull(userRest);
        assertEquals(userId,userRest.getUserId());
        assertEquals(userDto.getFirstName(), userRest.getFirstName());
        assertEquals(userDto.getLastName(), userRest.getLastName());
        assertTrue(userDto.getAddresses().size() == userRest.getAddresses().size());

    }

    @Test
    void createUser() {
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
    void getAddresses() {
    }

    @Test
    void getAddress() {
    }

    @Test
    void verifyEmail() {
    }

    @Test
    void requestReset() {
    }

    @Test
    void resetPassword() {
    }


}