package com.example.gateway.service;

import com.example.gateway.exception.AppException;
import com.example.gateway.kafka.ProducerBroker;
import com.example.gateway.model.Role;
import com.example.gateway.model.RoleName;
import com.example.gateway.model.User;
import com.example.gateway.model.auth.SignUpRequest;
import com.example.gateway.model.kafka.Merchant;
import com.example.gateway.repository.RoleRepo;
import com.example.gateway.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthService {

    private final UserRepo userRepository;
    private final RoleRepo roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProducerBroker producerBroker;

    @Autowired
    public AuthService(UserRepo userRepository, RoleRepo roleRepository, PasswordEncoder passwordEncoder, ProducerBroker producerBroker) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.producerBroker = producerBroker;
    }

    public User registerUserByType(SignUpRequest signUpRequest, String type) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new AppException("Username is already taken!");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new AppException("Email Address already in use!");

        }

        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(),
                signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole;
        if (type.equals("merchant")) {
            userRole = roleRepository.findByName(RoleName.ROLE_MERCHANT)
                    .orElseThrow(() -> new AppException("Merchant Role not set."));
            user.setRoles(Collections.singleton(userRole));
            sendMerchantToKafka(user);
        } else if (type.equals("admin")) {
            userRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                    .orElseThrow(() -> new AppException("Admin Role not set."));
        } else {
            userRole = roleRepository.findByName(RoleName.ROLE_USER)
                    .orElseThrow(() -> new AppException("User Role not set."));
        }

        user.setRoles(Collections.singleton(userRole));

        userRepository.save(user);

        return user;
    }

    private void sendMerchantToKafka(User user) {
        Merchant merchant = new Merchant(user.getName(), user.getUsername(), user.getEmail());
        producerBroker.sendMerchant(merchant);
    }
}
