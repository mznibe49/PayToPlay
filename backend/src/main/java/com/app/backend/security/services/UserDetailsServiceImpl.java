package com.app.backend.security.services;

import com.app.backend.models.User;
import com.app.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(user);
    }

    public void updateResetPassword(String token, String email) throws UserNotFoundException {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if(userOpt.isPresent()){ // not null
            User user = userOpt.get();
            userRepository.save(user);
        } else {
            throw new  UserNotFoundException("Could not find any user with email : "+email);
        }
    }

    public User get(String resetPasswordToken){
        return userRepository.findByResetPasswordToken(resetPasswordToken);
    }

    public void updatePassword(User user, String newPassWord){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPw = passwordEncoder.encode(newPassWord);

        user.setPassword(encodedPw);
        user.setResetPasswordToken(null);

        userRepository.save(user);
    }
}