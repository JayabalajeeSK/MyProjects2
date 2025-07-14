package com.jb.document_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class RegisterDto 
{
    private Long id;
    private String username;
    private String email;
    private String password;
    private String role;

}//