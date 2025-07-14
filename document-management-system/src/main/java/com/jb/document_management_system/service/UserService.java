package com.jb.document_management_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jb.document_management_system.entity.User;
import com.jb.document_management_system.repository.UserRepository;

@Service
public class UserService 
{
    @Autowired
    private UserRepository userRepository;

    public User getByEmail(String email) 
    {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    public User getByUsernameOrEmail(String value) 
    {
        return userRepository.findByUsername(value)
                .or(() -> userRepository.findByEmail(value))
                .orElseThrow(() -> new RuntimeException("User not found: " + value));
    }

}
