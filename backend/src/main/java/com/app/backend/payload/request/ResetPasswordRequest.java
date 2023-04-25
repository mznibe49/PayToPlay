package com.app.backend.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ResetPasswordRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String confirmationEmail;
}
