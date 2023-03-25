package com.app.backend.controllers.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DeleteAvailabilityRequest {

    @Email
    private String email;
}
