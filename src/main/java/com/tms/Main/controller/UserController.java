package com.tms.Main.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tms.Main.Dto.UserRequestDTO;
import com.tms.Main.Dto.UserResponseDTO;
import com.tms.Main.Service.UserService;
import com.tms.Main.response.ApiResponsePattern;

import jakarta.validation.Valid;

//controller/UserController.java
@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	// D - depends on interface
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/register")
	public ResponseEntity<ApiResponsePattern<UserResponseDTO>> registerUser(
			@Valid @RequestBody UserRequestDTO request) {
		UserResponseDTO response = userService.registerUser(request);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponsePattern.success(response, "User registered successfully"));
	}

	@GetMapping("/{userId}")
	public ResponseEntity<ApiResponsePattern<UserResponseDTO>> getUserById(@PathVariable Long userId) {
		return ResponseEntity.ok(ApiResponsePattern.success(userService.getUserById(userId)));
	}

	@GetMapping("/username/{userName}")
	public ResponseEntity<ApiResponsePattern<UserResponseDTO>> getUserByUserName(@PathVariable String userName) {
		return ResponseEntity.ok(ApiResponsePattern.success(userService.getUserByUserName(userName)));
	}

	@GetMapping
	public ResponseEntity<ApiResponsePattern<List<UserResponseDTO>>> getAllUsers() {
		return ResponseEntity.ok(ApiResponsePattern.success(userService.getAllUsers()));
	}

	@GetMapping("/plan/{planType}")
	public ResponseEntity<ApiResponsePattern<List<UserResponseDTO>>> getUsersByPlanType(@PathVariable String planType) {
		return ResponseEntity.ok(ApiResponsePattern.success(userService.getUsersByPlanType(planType)));
	}
}
