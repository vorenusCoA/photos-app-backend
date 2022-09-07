package com.example.photos.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.photos.model.Photo;
import com.example.photos.model.User;
import com.example.photos.repository.PhotoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PhotoService {

	private final PhotoRepository photoRepository;
	private final UserService userService;

	public void delete(Photo photo) {
		photoRepository.delete(photo);
	}

	public List<Photo> findAllByUserEmail(String email) {
		return photoRepository.findAllByUserEmail(email);
	}
	
	public Optional<Photo> findById(UUID id) {
		return photoRepository.findById(id);
	}

	public List<Photo> savePhotos(MultipartFile[] files, String userEmail) throws IOException {
		
		User user = userService.findByEmail(userEmail).get();
		
		List<Photo> photos = new ArrayList<>();
		for (MultipartFile file : files) {
			photos.add(new Photo(file.getOriginalFilename(), file.getBytes(), user));
		}
		
		return savePhotos(photos);
	}
	
	public List<Photo> savePhotos(List<Photo> photos) {
		return photoRepository.saveAll(photos);
	}
	
}
