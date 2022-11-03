package com.example.photos.model;

import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserDTO {

	private final UUID id;
	private final String email;
	private final List<PhotoDTO> photos;
	
}
