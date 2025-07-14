package com.jb.document_management_system.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderImpl 
{
    public static void main(String[] args) 
    {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("user"));// $2a$10$vvH8hBLSxTPb79lNAIpDLuoohzXdCE/0FbfyWF7fJrevBrf6C94dm
        System.out.println(passwordEncoder.encode("admin"));// $2a$10$.e9n7MN.fyt2d3OQBmsShuk1pp5kx7P/udwy.Ng5IASI7AW4HoBGm
    }
}//