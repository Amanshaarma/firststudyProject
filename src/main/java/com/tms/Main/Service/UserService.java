package com.tms.Main.Service;

import java.util.List;

import com.tms.Main.Dto.UserRequestDTO;
import com.tms.Main.Dto.UserResponseDTO;

//service/UserService.java
public interface UserService {
	UserResponseDTO registerUser(UserRequestDTO request);

	UserResponseDTO getUserById(Long userId);

	UserResponseDTO getUserByUserName(String userName);

	List<UserResponseDTO> getAllUsers();

	List<UserResponseDTO> getUsersByPlanType(String planType);
}