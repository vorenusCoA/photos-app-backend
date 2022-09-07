package com.example.photos.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.photos.model.Role;
import com.example.photos.model.RoleType;
import com.example.photos.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleService {

	private final RoleRepository roleRepository;
	
	public Optional<Role> findByType(RoleType type) {
		return roleRepository.findByType(type);
	}
}
