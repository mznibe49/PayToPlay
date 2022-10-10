package com.app.backend.controllers;

import com.app.backend.models.PasswordResetToken;
import com.app.backend.models.User;
import com.app.backend.payload.request.ForgotPasswordRequest;
import com.app.backend.payload.request.UpdatePasswordRequest;
import com.app.backend.payload.response.MessageResponse;
import com.app.backend.repository.PasswordTokenRepository;
import com.app.backend.services.EmailServiceImp;
import com.app.backend.services.UserDetailsServiceImpl;
import com.app.backend.utils.Utils;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/api")
public class ForgotPasswordController {

    @Autowired
    UserDetailsServiceImpl userService;

    @Autowired
    EmailServiceImp emailService;

    @Autowired
    PasswordTokenRepository passwordTokenRepository;

    @Value("${spring.application.front.url}")
    private String frontUrl;

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
            String resetLink = url + "/api/reset_password?token=" + token;

            try {
                emailService.sendForgottenPassWordEmail(email, resetLink);
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

    @GetMapping("/reset_password")
    public RedirectView redirect(@Param(value="token") String token,
                                               RedirectAttributes model){
        // checking if token is valid or not
        Optional<PasswordResetToken> passwordResetToken = passwordTokenRepository.findByToken(token);
        RedirectView redirectView = new RedirectView();
        String finalUrl = frontUrl + "/reset_password/";
        if(passwordResetToken.isPresent()){

            finalUrl += token;
        }
        redirectView.setUrl(finalUrl);
        return redirectView;
    }

    @PostMapping("/update_password")
    public ResponseEntity<?> updatePassword(HttpServletRequest request,
                                            @Valid @RequestBody UpdatePasswordRequest updatePasswordRequest){


        String token = updatePasswordRequest.getUserToken();
        String password = updatePasswordRequest.getPassword();

        Optional<PasswordResetToken> passwordResetToken = passwordTokenRepository.findByToken(token);
        if(passwordResetToken.isPresent()){

            PasswordResetToken prToken = passwordResetToken.get();
            LocalDateTime expiredDate = prToken.getExpiryDate();
            LocalDateTime now = LocalDateTime.now();
            if(now.isAfter(expiredDate)){
                return ResponseEntity
                        .badRequest() // token not found
                        .body(new MessageResponse("Your link is expired"));
            }

            User user = passwordResetToken.get().getUser();
            userService.updateUserPassword(user, password);
            return ResponseEntity.ok(new MessageResponse("Password updated successfully!"));
        }
        return ResponseEntity
                .badRequest() // token not found
                .body(new MessageResponse("Error while updating password"));
    }
}
