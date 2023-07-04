package com.appsdeveloperblog.app.ws.io.entity;



import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
@Entity(name="password_reset_tokens")
public class PasswordResetTokenEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -5451930753180413588L;
    @Id
    @GeneratedValue
    private long id;

    private String token;
    @OneToOne()
    @JoinColumn(name="users_id")
    private UserEntity userDetails;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserEntity getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserEntity userDetails) {
        this.userDetails = userDetails;
    }
}
