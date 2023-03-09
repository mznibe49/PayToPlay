package com.app.backend.services;

import com.app.backend.models.PasswordResetToken;
import com.app.backend.models.User;
import com.app.backend.repository.PasswordTokenRepository;
import com.app.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordTokenRepository passwordTokenRepository;

    @Autowired
    PasswordEncoder encoder;

    @Override
    @Transactional
    // this is an overrided method with name "loadUserByUserName" but it will actually search for email
    // this method is used by spring security to verify the login by email instead of username
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));

        return UserDetailsImpl.build(user);
    }


    public UserDetails customLoadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + username));

        return UserDetailsImpl.build(user);
    }

    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordTokenRepository.save(myToken); // first save so JPA will set expiration date = now ()
        myToken.setExpiryDate(myToken.getExpiryDate().plusDays(1)); // adding 1 day to expiration date
        passwordTokenRepository.save(myToken);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public  void updateUserPassword(User user, String password){
        String encodedPw = encoder.encode(password);
        user.setPassword(encodedPw);
        userRepository.save(user);
    }
}