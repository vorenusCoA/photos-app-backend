package com.example.photos.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.photos.mapper.PhotoMapper;
import com.example.photos.model.Photo;
import com.example.photos.model.PhotoDTO;
import com.example.photos.model.User;
import com.example.photos.service.PhotoService;
import com.example.photos.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = { "http://localhost:4200" })
@RequiredArgsConstructor
public class PhotoController {

	private final PhotoService photoService;
	private final PhotoMapper photoMapper;
	private final UserService userService;

	@GetMapping(value = "/photos/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> getPhotoById(@PathVariable UUID id, @AuthenticationPrincipal String email) {

		Optional<Photo> photo = photoService.findById(id);
		if (photo.isEmpty()) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		
		// You can only ask for your photos
		User loggedUser = userService.findByEmail(email).get();
		if (!photo.get().getUser().equals(loggedUser)) {
			return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
		}

		return new ResponseEntity<>(photo.get().getData(), HttpStatus.OK);
	}
	
	@GetMapping("/photos")
	public ResponseEntity<List<PhotoDTO>> getAllPhotos(@AuthenticationPrincipal String userEmail) {

		List<Photo> photos = photoService.findAllByUserEmail(userEmail);
		List<PhotoDTO> photosDTO = photoMapper.getPhotosDTO(photos);
		
		return new ResponseEntity<>(photosDTO, HttpStatus.OK);
	}
	
	@PostMapping("/photos")
	public ResponseEntity<String> uploadPhotos(@RequestParam("files") MultipartFile[] files, @AuthenticationPrincipal String userEmail) {
		
		try {
			
			photoService.savePhotos(files, userEmail);
			
			return new ResponseEntity<>("Success!", HttpStatus.OK);
			
		} catch(IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>("Fail!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
}
