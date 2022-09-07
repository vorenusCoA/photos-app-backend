package com.example.photos.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.photos.model.Role;
import com.example.photos.model.RoleType;
import com.example.photos.model.User;
import com.example.photos.repository.UserRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;

@Service
public class UserService {

	private final RoleService roleService;
	private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Value("${generatedPassword}")
    private String generatedPassword;
    
	public UserService(RoleService roleService, UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
		this.roleService = roleService;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	public User save(User user) {
		return userRepository.save(user);
	}

	public void registerUserIfNotPresent(GoogleIdToken idToken) {
		
		String email = idToken.getPayload().getEmail();
		
		Optional<User> user = findByEmail(email);
		
		if (user.isEmpty()) {
			User newUser = new User(email, passwordEncoder.encode(generatedPassword));
			Role role = roleService.findByType(RoleType.ROLE_USER).get();
			newUser.addRole(role);
			save(newUser);
		}

	}

	public Optional<User> findByEmailAndFetchRoles(String email) {
		return userRepository.findByEmailAndFetchRoles(email);
	}
}
