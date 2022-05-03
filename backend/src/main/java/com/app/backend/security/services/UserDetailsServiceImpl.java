package com.app.backend.security.services;

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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(user);
    }

    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordTokenRepository.save(myToken);
    }

    // getUserByEmail(email);// userRepository.findByEmail(email);
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public  void updateUserPassword(User user, String password){
        System.out.println("New Password from user service : "+password);
        String encodedPw = encoder.encode(password);
        user.setPassword(encodedPw);
        userRepository.save(user);
    }
}