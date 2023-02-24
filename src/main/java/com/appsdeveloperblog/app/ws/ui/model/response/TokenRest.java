package com.appsdeveloperblog.app.ws.ui.model.response;

public class TokenRest {
    private String token;

    public TokenRest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
