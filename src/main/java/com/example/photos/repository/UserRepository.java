package com.example.photos.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.photos.model.User;

public interface UserRepository extends JpaRepository<User, UUID> {

	Optional<User> findByEmail(String email);

	@Query(value = "SELECT u FROM User u JOIN FETCH u.roles WHERE u.email = :email")
	Optional<User> findByEmailAndFetchRoles(String email);

}
