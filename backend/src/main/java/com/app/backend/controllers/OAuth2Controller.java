package com.app.backend.controllers;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/login/oauth2/code")
public class OAuth2Controller {

    @GetMapping("/google")
    public Map<String, Object> currentGoogleUser(OAuth2AuthenticationToken token){
        return token.getPrincipal().getAttributes();
    }
}
