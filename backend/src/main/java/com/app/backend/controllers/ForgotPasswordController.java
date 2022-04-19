package com.app.backend.controllers;

import com.app.backend.payload.request.ForgotPasswordRequest;
import com.app.backend.payload.request.LoginRequest;
import com.app.backend.payload.response.MessageResponse;
import net.bytebuddy.utility.RandomString;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/api")
public class ForgotPasswordController {

    @PostMapping("/forget_password")
    public ResponseEntity<?> processForgotPasswordForm(@Valid @RequestBody ForgotPasswordRequest fpwRequest){

        String email = fpwRequest.getEmail();
        String token = RandomString.make(45);

        System.out.println("Email : "+email);
        System.out.println("Token : "+token);

        return ResponseEntity.ok(new MessageResponse("Generated token : "+token));
    }
}
