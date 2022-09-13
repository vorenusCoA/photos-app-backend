package com.example.photos.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.photos.model.Photo;

public interface PhotoRepository extends JpaRepository<Photo, UUID> {

	List<Photo> findAllByUserEmail(String email);

	@Query(value = "SELECT p FROM Photo p JOIN FETCH p.user WHERE p.id = :id")
	Optional<Photo> findByIdAndFetchUser(UUID id);

}
