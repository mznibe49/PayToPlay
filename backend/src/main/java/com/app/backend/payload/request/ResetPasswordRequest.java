package com.app.backend.payload.request;

import javax.validation.constraints.NotBlank;

public class ResetPasswordRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String confirmationEmail;

    public String getConfirmationEmail() {
        return confirmationEmail;
    }

    public void setConfirmationEmail(String confirmationEmail) {
        this.confirmationEmail = confirmationEmail;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
