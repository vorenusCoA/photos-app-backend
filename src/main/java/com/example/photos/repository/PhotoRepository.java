package com.example.photos.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.photos.model.Photo;

public interface PhotoRepository extends JpaRepository<Photo, UUID> {

	List<Photo> findAllByUserEmail(String email);

}
