package com.appsdeveloperblog.app.ws.shared.dto;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest
class UtilsTest {
    @Autowired
    Utils utils;
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Disabled
    void generateEmailVerificationToken() {
    }

    @Disabled
    void generatePasswordResetRequest() {
    }

    @Test
    void generateUserId() {
        String userId = utils.generateUserId(30);
        assertNotNull(userId);
        assertTrue(userId.length()==30);
    }

    @Disabled
    void generateAddressId() {
    }

    @Disabled
    void hasTokenExpired() {
        String userId = utils.generateUserId(30);
        String token = utils.generateEmailVerificationToken(userId);
        Boolean hasTokenExpired = Utils.hasTokenExpired(token);
        assertFalse(hasTokenExpired);
    }
}