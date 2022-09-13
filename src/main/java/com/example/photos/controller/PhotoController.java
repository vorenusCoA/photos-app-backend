package com.example.photos.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.photos.mapper.PhotoMapper;
import com.example.photos.model.Photo;
import com.example.photos.model.PhotoDTO;
import com.example.photos.model.ResponseMessage;
import com.example.photos.service.PhotoService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = { "http://localhost:4200" })
@RequiredArgsConstructor
@RequestMapping("/api")
public class PhotoController {

	private final PhotoService photoService;
	private final PhotoMapper photoMapper;

	@GetMapping(value = "/photos/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> getPhotoById(@PathVariable UUID id, JwtAuthenticationToken principal) {

		Optional<Photo> optionalPhoto = photoService.findByIdAndFetchUser(id);
		if (optionalPhoto.isEmpty()) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		Photo photo = optionalPhoto.get();

		// You can only ask for your photos
		if (!photo.getUser().getEmail().equals(principal.getToken().getClaim("email"))) {
			return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
		}

		return new ResponseEntity<>(photo.getData(), HttpStatus.OK);
	}

	@GetMapping("/photos")
	public ResponseEntity<List<PhotoDTO>> getAllPhotos(JwtAuthenticationToken principal) {

		List<Photo> photos = photoService.findAllByUserEmail(principal.getToken().getClaim("email"));
		List<PhotoDTO> photosDTO = photoMapper.getPhotosDTO(photos);

		return new ResponseEntity<>(photosDTO, HttpStatus.OK);
	}

	@PostMapping("/photos")
	public ResponseEntity<ResponseMessage> uploadPhoto(@RequestParam("file") MultipartFile file,
			JwtAuthenticationToken principal) {

		try {

			photoService.savePhoto(file, principal.getToken().getClaim("email"));

			return new ResponseEntity<>(
					new ResponseMessage("Uploaded '" + file.getOriginalFilename() + "' successfully"), HttpStatus.OK);

		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>(new ResponseMessage("Could not upload '" + file.getOriginalFilename() + "'"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
