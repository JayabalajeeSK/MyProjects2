package com.jb.common_template.service;

import java.util.List;

import com.jb.common_template.dto.UserResponseDTO;
import com.jb.common_template.entity.User;

public interface UserService 
{
	 List<User> getAll(); // no one

	 List<UserResponseDTO> getAllForUser(); //user + admin

	 User getById(Long id); // no one

	 UserResponseDTO getByIdForUser(Long id); // user + admin

	 User getByEmail(String email); // user + admin

	 //====================================================

	 User userCreatedByAdmin(User user); //admin

	 //====================================================

	 User updateUser(Long id, User user); //user

	 User updateAdmin(Long id, User admin); //admin

	 User updateRoleByAdmin(Long id, User user); //admin

	 //====================================================

	 void delete(Long id); //admin
}
