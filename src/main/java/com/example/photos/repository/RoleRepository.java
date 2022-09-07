package com.example.photos.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.photos.model.Role;
import com.example.photos.model.RoleType;

public interface RoleRepository extends JpaRepository<Role, UUID> {

	Optional<Role> findByType(RoleType type);

}
