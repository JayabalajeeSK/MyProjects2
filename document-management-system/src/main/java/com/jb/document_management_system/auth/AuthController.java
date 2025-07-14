package com.jb.document_management_system.auth;

import com.jb.document_management_system.dto.JwtAuthResponse;
import com.jb.document_management_system.dto.LoginDto;
import com.jb.document_management_system.dto.RegisterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController 
{

    @Autowired
    private AuthService authService;

    // ✅ Register Endpoint using RegisterDto
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) 
    {
        String response = authService.registerUser(registerDto);
        return ResponseEntity.ok(response);
    }

    // ✅ Login Endpoint using LoginDto
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto) 
    {
        String token = authService.loginUser(loginDto);

        if (token.equalsIgnoreCase("Invalid username/email or password")) 
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new JwtAuthResponse(null, "Invalid credentials"));
        }

        return ResponseEntity.ok(new JwtAuthResponse(token, "Login successful"));
    }
}
