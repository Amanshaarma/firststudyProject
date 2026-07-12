package com.study.Main.Service;

import java.util.List;

import com.study.Main.Dto.UserRequestDTO;
import com.study.Main.Dto.UserResponseDTO;

//service/UserService.java
public interface UserService {
	UserResponseDTO registerUser(UserRequestDTO request);

	UserResponseDTO getUserById(Long userId);

	UserResponseDTO getUserByUserName(String userName);

	List<UserResponseDTO> getAllUsers();

	List<UserResponseDTO> getUsersByPlanType(String planType);
}