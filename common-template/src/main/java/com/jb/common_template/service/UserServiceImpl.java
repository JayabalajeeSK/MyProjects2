package com.jb.common_template.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jb.common_template.dto.UserResponseDTO;
import com.jb.common_template.entity.User;
import com.jb.common_template.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public List<User> getAll() 
    {
        return userRepository.findAll();
    }

    @Override
    public User getById(Long id) 
    {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with ID " +id ));
    }

    @Override
    public UserResponseDTO getByIdForUser(Long id) 
    {
        return userRepository.findById(id)
            .map(user -> new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole()
            )).orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }

    @Override
    public List<UserResponseDTO> getAllForUser() 
    {
        return userRepository.findAll().stream()
            .map(user -> new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole()
            ))
            .toList(); // or .collect(Collectors.toList())
    }

    @Override
    public User getByEmail(String email) 
    {
        return userRepository.findByEmail(email).orElseThrow( () -> new RuntimeException("User with email "+ email+" not found"));
    }

    @Override
    public User userCreatedByAdmin(User user) 
    {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User user) 
    {
        return userRepository.findById(id)
        .map(existing -> 
        { 
            existing.setUsername(user.getUsername());
            existing.setEmail(user.getEmail());
            existing.setPassword(user.getPassword());
            return userRepository.save(existing);
        })
        .orElseThrow(() -> new RuntimeException("User not found with ID: "+id));
    }

    @Override
    public User updateAdmin(Long id, User admin) 
    {
        return userRepository.findById(id)
        .map(existing -> 
        { 
            existing.setUsername(admin.getUsername());
            existing.setEmail(admin.getEmail());
            existing.setPassword(admin.getPassword());
            existing.setRole(admin.getRole());
            return userRepository.save(existing);
        })
        .orElseThrow(() -> new RuntimeException("User not found with ID: "+id));
    }

    @Override
    public User updateRoleByAdmin(Long id, User user) 
    {
        return userRepository.findById(id)
        .map(existing -> 
        { 
            existing.setRole(user.getRole());
            return userRepository.save(existing);
        })
        .orElseThrow(() -> new RuntimeException("User not found with ID: "+id));
    }

    @Override
    public void delete(Long id) 
    {
        userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with ID: "+id));
        userRepository.deleteById(id);
    }
    
}
