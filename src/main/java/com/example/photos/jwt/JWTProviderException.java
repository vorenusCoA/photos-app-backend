package com.example.photos.jwt;

public class JWTProviderException extends Exception {

	private static final long serialVersionUID = 1L;

	public JWTProviderException(String message, Throwable err) {
		super(message, err);
	}
}
