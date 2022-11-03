package com.example.photos.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.photos.model.PhotoDTO;
import com.example.photos.model.User;
import com.example.photos.model.UserDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserMapper {

	private final PhotoMapper photoMapper;

	public UserDTO getUserDTO(User user) {
		List<PhotoDTO> photos = photoMapper.getPhotosDTO(user.getPhotos());
		UserDTO newUserDTO = new UserDTO(user.getId(), user.getEmail(), photos);
		return newUserDTO;
	}
}
