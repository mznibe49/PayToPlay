package com.app.backend.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UpdatePasswordRequest {

    @NotBlank
    private String userToken;

    @NotBlank
    private String password;

}
