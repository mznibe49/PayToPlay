package com.app.backend.payload.request;

import javax.validation.constraints.NotBlank;

public class UpdatePasswordRequest {

    @NotBlank
    private String userToken;

    @NotBlank
    private String password;

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
