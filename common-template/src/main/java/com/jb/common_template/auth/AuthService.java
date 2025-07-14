package com.jb.common_template.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jb.common_template.dto.LoginDto;
import com.jb.common_template.dto.RegisterDto;
import com.jb.common_template.entity.User;
import com.jb.common_template.repository.UserRepository;
import com.jb.common_template.security.JwtTokenProvider;

@Service
public class AuthService 
{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    // ✅ Register User
    public String registerUser(RegisterDto registerDto) {
        if (userRepository.findByUsername(registerDto.getUsername()).isPresent()) 
        {
            return "Username already exists";
        }

        if (userRepository.findByEmail(registerDto.getEmail()).isPresent()) 
        {
            return "Email already exists";
        }

        String encodedPassword = passwordEncoder.encode(registerDto.getPassword());

        User user = User.builder()
                .username(registerDto.getUsername())
                .email(registerDto.getEmail())
                .password(encodedPassword)
                .role("ROLE_"+registerDto.getRole().toUpperCase())
                .build();

        userRepository.save(user);
        return  user.getRole().toUpperCase().replaceFirst("(?i)^role_", "") +" registered successfully";
    }


    // ✅ Login User
    public String loginUser(LoginDto loginDto) 
    {
        try 
        {
            Authentication authentication = authenticationManager.authenticate
            (
                    new UsernamePasswordAuthenticationToken
                    (
                            loginDto.getUsernameOrEmail(),
                            loginDto.getPassword()
                    )
            );

            String token = jwtTokenProvider.generateToken(authentication);
            return "Bearer " + token;

        } 
        catch (AuthenticationException ex) 
        {
            return "Invalid username/email or password";
        }
    }
}