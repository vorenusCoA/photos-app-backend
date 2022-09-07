package com.example.photos.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.photos.model.Photo;
import com.example.photos.model.PhotoDTO;

@Component
public class PhotoMapper {

	@Value("${serverURL}")
	private String serverURL;

	public List<PhotoDTO> getPhotosDTO(List<Photo> photos) {
		List<PhotoDTO> photosDTO = new ArrayList<>(photos.size());
		for (Photo photo : photos) {
			photosDTO.add(getPhotoDTO(photo));
		}
		return photosDTO;
	}

	public PhotoDTO getPhotoDTO(Photo photo) {
		PhotoDTO newPhotoDTO = new PhotoDTO(photo.getName(), serverURL + "/photos/" + photo.getId());
		return newPhotoDTO;
	}

}
