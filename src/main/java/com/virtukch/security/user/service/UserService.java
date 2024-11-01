package com.virtukch.security.user.service;

import com.virtukch.security.user.entity.User;
import com.virtukch.security.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findUser(String id){
        Optional<User> user = userRepository.findByUserId(id);


        return user;
    }
}
