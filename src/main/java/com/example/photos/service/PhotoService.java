package com.example.photos.service;

import java.io.IOException;
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

	public List<Photo> findAllByUserEmail(String email) {
		return photoRepository.findAllByUserEmail(email);
	}

	public Optional<Photo> findByIdAndFetchUser(UUID id) {
		return photoRepository.findByIdAndFetchUser(id);
	}

	public Photo savePhoto(MultipartFile file, String userEmail) throws IOException {
		User user = userService.findByEmail(userEmail).get();
		Photo newPhoto = new Photo(file.getOriginalFilename(), file.getBytes(), user);
		return photoRepository.save(newPhoto);
	}

}
