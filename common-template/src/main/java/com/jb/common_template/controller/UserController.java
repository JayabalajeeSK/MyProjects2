package com.jb.common_template.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jb.common_template.dto.UserResponseDTO;
import com.jb.common_template.entity.User;
import com.jb.common_template.service.UserService;

@Controller
@RequestMapping("/api/user")
public class UserController 
{
    @Autowired
    private UserService userService;

    // @GetMapping
    // public ResponseEntity<List<User>> getAll() 
    // {
    //     return ResponseEntity.ok(userService.getAll());
    // }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')") //user + admin
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllForUSer() 
    {
        return ResponseEntity.ok(userService.getAllForUser());
    }

    // @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')") //user + admin
    // @GetMapping("/{id}")
    // public ResponseEntity<User> getById(@PathVariable Long id) 
    // {
    //     return ResponseEntity.ok(userService.getById(id));
    // }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')") //user + admin
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getByIdForUser(@PathVariable Long id) 
    {
        return ResponseEntity.ok(userService.getByIdForUser(id));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN, ROLE_USER')") //user + admin
    @GetMapping("/by-email")
    public ResponseEntity<User> getByEmail(@RequestParam String email) 
    {
        return ResponseEntity.ok(userService.getByEmail(email));
    }

    //===================================================================

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')") //admin
    @PostMapping
    public ResponseEntity<String> userCreatedByAdmin(@RequestBody User user) 
    {
        userService.userCreatedByAdmin(user);
        return ResponseEntity.status(201).body("User created successfully!");
    }

    //===================================================================

    @PutMapping("updateUser/{id}") //user
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User user ) 
    {
        userService.updateUser(id,user);
        return ResponseEntity.ok("User updated successfully!");
    }

    @PutMapping("updateAdmin/{id}") //admin
    public ResponseEntity<String> updateAdmin(@PathVariable Long id, @RequestBody User admin ) 
    {
        userService.updateUser(id,admin);
        return ResponseEntity.ok("User updated successfully!");
    }


    @PutMapping("updateRoles/{id}") //admin
    public ResponseEntity<String> updateRoleByAdmin(@PathVariable Long id, @RequestBody User user ) 
    {
        userService.updateUser(id,user);
        return ResponseEntity.ok("User updated successfully!");
    }

    //====================================================================

    @DeleteMapping("DeleteUser/{id}") //admin
    public ResponseEntity<String> delete(@PathVariable Long id) 
    {
        userService.delete(id);
        return ResponseEntity.ok("User deleted successfully!");
    }

}
