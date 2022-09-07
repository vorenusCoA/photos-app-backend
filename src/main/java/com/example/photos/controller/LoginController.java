package com.example.photos.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.photos.jwt.JWTProvider;
import com.example.photos.jwt.JWTProviderException;
import com.example.photos.model.TokenDTO;
import com.example.photos.service.UserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@RequiredArgsConstructor
public class LoginController {

	private final GoogleIdTokenVerifier googleIdTokenVerifier;
	private final JWTProvider jwtProvider;
	private final UserService userService;
	
    @PostMapping("/login")
    ResponseEntity<TokenDTO> login(@RequestBody String idTokenString) {
    	
    	String jwt = "";
		try {
			
			GoogleIdToken idToken = googleIdTokenVerifier.verify(idTokenString);
			
			if (idToken == null) {
				throw new GeneralSecurityException("token not found");
			}
			
			userService.registerUserIfNotPresent(idToken);
			
	        jwt = jwtProvider.generateToken(idToken.getPayload().getEmail());
	        
		} catch (GeneralSecurityException | IOException | JWTProviderException e) {
			return new ResponseEntity<>(new TokenDTO(jwt), HttpStatus.FORBIDDEN);
		}

		return new ResponseEntity<>(new TokenDTO(jwt), HttpStatus.OK);
    }

}
