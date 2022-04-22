package com.app.backend.controllers;

import com.app.backend.models.User;
import com.app.backend.payload.request.ForgotPasswordRequest;
import com.app.backend.payload.request.LoginRequest;
import com.app.backend.payload.response.MessageResponse;
import com.app.backend.repository.UserRepository;
import com.app.backend.security.services.EmailServiceImp;
import com.app.backend.security.services.UserDetailsServiceImpl;
import com.app.backend.utils.Utils;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/api")
public class ForgotPasswordController {

    @Autowired
    UserDetailsServiceImpl userService;

    @Autowired
    EmailServiceImp emailService;

    @PostMapping("/forget_password")
    public ResponseEntity<?> processForgotPasswordForm(
            HttpServletRequest request,
            @Valid @RequestBody ForgotPasswordRequest fpwRequest) {

        String email = fpwRequest.getEmail();
        Optional<User> optionalUser = userService.getUserByEmail(email);

        if (optionalUser.isPresent()) {

            User user = optionalUser.get();
            String token = RandomString.make(45);
            userService.createPasswordResetTokenForUser(user, token);
            String url = Utils.getSiteUrl(request);
            String resetLink = url + "/reset_password?token=" + token;

            try {
                emailService.sendForgottenPassWordEmail(email, resetLink);
                // delete token instance in database if expiration date has passe (triggers ??)
            } catch (Throwable t) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Exception while sending Email : " + t));
            }

            return ResponseEntity.ok(new MessageResponse("Email sent to : " + email));
        }
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Could not find any user with email : " + email));
    }
}
