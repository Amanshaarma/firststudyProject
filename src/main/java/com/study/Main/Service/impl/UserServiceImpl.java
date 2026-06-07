package com.study.Main.Service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.study.Main.Dto.UserRequestDTO;
import com.study.Main.Dto.UserResponseDTO;
import com.study.Main.Expection.DuplicateResourceException;
import com.study.Main.Expection.ResourceNotFoundException;
import com.study.Main.Mapper.UserMapper;
import com.study.Main.Model.User;
import com.study.Main.Repository.UserRepository;
import com.study.Main.Service.UserService;

import jakarta.transaction.Transactional;

//service/impl/UserServiceImpl.java
@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder; // BCrypt

	// D - Constructor injection
	public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	@Transactional
	public UserResponseDTO registerUser(UserRequestDTO request) {

		// 1. Duplicate username check
		if (userRepository.existsByUserName(request.getUserName())) {
			throw new DuplicateResourceException("Username already exists: " + request.getUserName());
		}

		// 2. RequestDTO → Entity
		User user = userMapper.toEntity(request);

		// 3. Hash password — never store plain text!
		user.setPasswordHash(passwordEncoder.encode(request.getPassword()));

		// 4. Set joining date if not provided
		if (user.getJoiningDate() == null) {
			user.setJoiningDate(LocalDate.now());
		}

		// 5. Save & return
		User savedUser = userRepository.save(user);
		return userMapper.toDTO(savedUser);
	}

	@Override
	public UserResponseDTO getUserById(Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
		return userMapper.toDTO(user);
	}

	@Override
	public UserResponseDTO getUserByUserName(String userName) {
		User user = userRepository.findByUserName(userName)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + userName));
		return userMapper.toDTO(user);
	}

	@Override
	public List<UserResponseDTO> getAllUsers() {
		return userMapper.toDTOList(userRepository.findAll());
	}

	@Override
	public List<UserResponseDTO> getUsersByPlanType(String planType) {
		List<User> users = userRepository.findByPlanType(planType);
		return userMapper.toDTOList(users);
	}
}
