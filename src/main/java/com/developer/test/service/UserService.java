package com.developer.test.service;

import com.developer.test.model.User;
import com.developer.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @CachePut(value = "users", key = "#user.id")
    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
