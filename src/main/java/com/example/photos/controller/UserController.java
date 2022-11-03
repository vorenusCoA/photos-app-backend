package com.example.photos.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.photos.mapper.UserMapper;
import com.example.photos.model.User;
import com.example.photos.model.UserDTO;
import com.example.photos.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = { "http://localhost:4200" })
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;
	private final UserMapper userMapper;
	
	@PostMapping()
	public ResponseEntity<UserDTO> getUserOrRegister(JwtAuthenticationToken principal) {
		
		User user = userService.registerUserIfNotPresent(principal.getToken().getClaim("email"));
		UserDTO userDTO = userMapper.getUserDTO(user);
		return new ResponseEntity<>(userDTO, HttpStatus.OK);
	}
	
	

}
